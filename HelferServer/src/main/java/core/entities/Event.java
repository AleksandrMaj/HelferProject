package core.entities;

import core.enums.Benutzergruppe;
import jakarta.persistence.*;

import core.entities.Helfer;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
public class Event implements Serializable {

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

    public Event() {}
    public Event(String name, Date date, Benutzer organisator) {
        this.name = name;
        this.date = date;
        this.organisator = organisator;
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



}
