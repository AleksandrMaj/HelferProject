package dataaccess;

import core.entities.Benutzer;
import core.entities.Event;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

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
    private List<Benutzer> helferListe;

    @ManyToOne
    @JoinColumn(name = "benutzerID")

    private Benutzer organisator;

    public EventEntity() {}
    public EventEntity(String name, Date date, Benutzer organisator) {
        this.name = name;
        this.date = date;
        this.organisator = organisator;
    }

    public EventEntity(Event event) {
        this.id = event.getId();
        this.name = event.getName();
        this.date = event.getDate();
        this.helferListe = event.getHelferListe();
        this.organisator = event.getOrganisator();
    }

    // Getter und Setter
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Date getTermin() { return date; }

    public void setTermin(Date termin) { this.date = termin; }

    public Benutzer getOrganisator() { return organisator; }

    public void setOrganisator(Benutzer organisator) { this.organisator = organisator; }

    public List<Benutzer> getHelfer() { return helferListe; }

    public void setHelfer(List<Benutzer> helferListe) { this.helferListe = helferListe;}

    public Event toEvent() {
        core.entities.Event event = new core.entities.Event();
        event.setId(this.id);
        event.setName(this.name);
        event.setDate(this.date);
        event.setOrganisator(this.organisator);
        event.setHelferListe(this.helferListe);
        return event;
    }
}
