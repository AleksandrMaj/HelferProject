package core.entities;

import java.util.Date;
import java.util.List;

public class Event
{
    private int id;
    private String name;
    private Date date;
    private List<Benutzer> helferListe;
    private Benutzer organisator;

    public Event() {
    }

    public Event(String name, Date date, Benutzer organisator) {
        this.name = name;
        this.date = date;
        this.organisator = organisator;
    }

    // Getter and Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Benutzer getOrganisator() {
        return organisator;
    }

    public void setOrganisator(Benutzer organisator) {
        this.organisator = organisator;
    }

    public List<Benutzer> getHelferListe() {
        return helferListe;
    }

    public void setHelferListe(List<Benutzer> helferListe) {
        this.helferListe = helferListe;
    }
}
