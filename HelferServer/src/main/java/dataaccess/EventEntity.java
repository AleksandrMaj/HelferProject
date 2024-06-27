package dataaccess;

import core.entities.Benutzer;
import core.entities.Event;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Event")
public class EventEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToMany
    @JoinTable(
            name = "Helfer",
            joinColumns = @JoinColumn(name = "eventID"),
            inverseJoinColumns = @JoinColumn(name = "benutzerID"))
    private List<BenutzerEntity> helferListe;

    @ManyToOne
    @JoinColumn(name = "benutzerID")

    private BenutzerEntity organisator;

    public EventEntity() {}
    public EventEntity(String name, Date date, BenutzerEntity organisator) {
        this.name = name;
        this.date = date;
        this.organisator = organisator;
    }

    public EventEntity(Event event) {
        this.id = event.getId();
        this.name = event.getName();
        this.date = event.getDate();
        this.helferListe = event.getHelferListe().stream()
                .map(BenutzerEntity::new)
                .toList();
        this.organisator = new BenutzerEntity(event.getOrganisator());
    }

    // Getter und Setter

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public List<BenutzerEntity> getHelferListe()
    {
        return helferListe;
    }

    public void setHelferListe(List<BenutzerEntity> helferListe)
    {
        this.helferListe = helferListe;
    }

    public BenutzerEntity getOrganisator()
    {
        return organisator;
    }

    public void setOrganisator(BenutzerEntity organisator)
    {
        this.organisator = organisator;
    }

    public Event toEvent() {
        Event event = new core.entities.Event();
        event.setId(this.id);
        event.setName(this.name);
        event.setDate(this.date);
        event.setOrganisator(this.organisator.toBenutzer());

        List<Benutzer> userList = this.helferListe.stream()
                .map(BenutzerEntity::toBenutzer)
                .toList();

        event.setHelferListe(userList);
        return event;
    }
}
