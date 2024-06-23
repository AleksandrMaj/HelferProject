package core.entities;

import core.enums.Benutzergruppe;

import javax.persistence.*;
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

    private Benutzergruppe organisator;

    private List<Helfer> helfer;

    public Event() {}
    public Event(String name, Date date, Benutzergruppe organisator) {
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

    public Benutzergruppe getOrganisator() { return organisator; }

    public void setOrganisator(Benutzergruppe organisator) { this.organisator = organisator; }

    public List<Helfer> getHelfer() { return helfer; }

    public void setHelfer(List<Helfer> helfer) { this.helfer = helfer;}



}
