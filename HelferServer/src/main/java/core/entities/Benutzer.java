package core.entities;

import core.enums.Benutzergruppe;

import java.util.LinkedList;
import java.util.List;

public class Benutzer {
    private int id;
    private String name;
    private String vorname;
    private Adresse adresse;
    private Benutzergruppe benutzergruppe;
    private String email;
    private String passwort;
    private List<Event> events = new LinkedList<>();

    public Benutzer()
    {
    }

    public Benutzer(int id, String name, String vorname, Adresse adresse, Benutzergruppe benutzergruppe, String email, String passwort, List<Event> events)
    {
        this.id = id;
        this.name = name;
        this.vorname = vorname;
        this.adresse = adresse;
        this.benutzergruppe = benutzergruppe;
        this.email = email;
        this.passwort = passwort;
        this.events = events;
    }

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

    public String getVorname()
    {
        return vorname;
    }

    public void setVorname(String vorname)
    {
        this.vorname = vorname;
    }

    public Adresse getAdresse()
    {
        return adresse;
    }

    public void setAdresse(Adresse adresse)
    {
        this.adresse = adresse;
    }

    public Benutzergruppe getBenutzergruppe()
    {
        return benutzergruppe;
    }

    public void setBenutzergruppe(Benutzergruppe benutzergruppe)
    {
        this.benutzergruppe = benutzergruppe;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPasswort()
    {
        return passwort;
    }

    public void setPasswort(String passwort)
    {
        this.passwort = passwort;
    }

    public List<Event> getEvents()
    {
        return events;
    }

    public void setEvents(List<Event> events)
    {
        this.events = events;
    }

    public void anonymize() {
        this.name = "";
        this.vorname = "";
        this.adresse = new Adresse();
        this.email = "";
        this.passwort = "";
    }

    public void anonymizeWithoutName() {
        this.adresse = new Adresse();
        this.email = "";
        this.passwort = "";
    }
}
