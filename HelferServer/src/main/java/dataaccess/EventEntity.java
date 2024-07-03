package dataaccess;

import core.entities.Benutzer;
import core.entities.Event;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Event")
public class EventEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime date;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Helfer",
            joinColumns = @JoinColumn(name = "eventID"),
            inverseJoinColumns = @JoinColumn(name = "benutzerID"))
    private List<BenutzerEntity> helferListe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organisatorID")
    private BenutzerEntity organisator;

    public EventEntity()
    {
    }

    public EventEntity(String name, LocalDateTime date, BenutzerEntity organisator)
    {
        this.name = name;
        this.date = date;
        this.organisator = organisator;
    }

    public EventEntity(Event event)
    {
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

    public LocalDateTime getDate()
    {
        return date;
    }

    public void setDate(LocalDateTime date)
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

    public Event toEvent()
    {
        Event event = new Event();
        event.setId(this.id);
        event.setName(this.name);
        event.setDate(this.date);
        event.setOrganisator(this.organisator.toBenutzer());

        List<Benutzer> userList = this.helferListe.stream()
                .map(benutzerEntity ->
                {
                    Benutzer user = new Benutzer();
                    user.setId(benutzerEntity.getId());
                    user.setVorname(benutzerEntity.getVorname());
                    user.setName(benutzerEntity.getName());
                    return user;
                })
                .toList();

        event.setHelferListe(userList);

        return event;
    }
}
