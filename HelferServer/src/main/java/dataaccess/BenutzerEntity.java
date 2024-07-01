package dataaccess;

import core.entities.Adresse;
import core.entities.Benutzer;
import core.entities.Event;
import core.enums.Benutzergruppe;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "Benutzer")
public class BenutzerEntity implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String vorname;

    @Embedded
    private Adresse adresse;

    @Enumerated(EnumType.STRING)
    private Benutzergruppe benutzergruppe;

    private String email;

    private String passwort;

    @ManyToMany(mappedBy = "helferListe", fetch = FetchType.LAZY)
    private List<EventEntity> events;

    public BenutzerEntity()
    {
    }

    public BenutzerEntity(int id, String name, String vorname, Adresse adresse, Benutzergruppe benutzergruppe, String email, String passwort, List<EventEntity> events)
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

    public BenutzerEntity(Benutzer benutzer)
    {
        this.id = benutzer.getId();
        this.name = benutzer.getName();
        this.vorname = benutzer.getVorname();
        this.adresse = benutzer.getAdresse();
        this.benutzergruppe = benutzer.getBenutzergruppe();
        this.email = benutzer.getEmail();
        this.passwort = benutzer.getPasswort();
        this.events = benutzer.getEvents().stream()
                .map(EventEntity::new)
                .toList();
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

    public List<EventEntity> getEvents()
    {
        return events;
    }

    public void setEvents(List<EventEntity> events)
    {
        this.events = events;
    }

    public Benutzer toBenutzer()
    {
        Benutzer benutzer = new Benutzer();
        benutzer.setId(this.id);
        benutzer.setName(this.name);
        benutzer.setVorname(this.vorname);
        benutzer.setAdresse(this.adresse);
        benutzer.setBenutzergruppe(this.benutzergruppe);
        benutzer.setEmail(this.email);
        benutzer.setPasswort(this.passwort);

        benutzer.setEvents(this.events.stream()
                .map(eventEntity ->
                {
                    Event event = new Event();
                    event.setId(eventEntity.getId());
                    event.setName(eventEntity.getName());
                    event.setDate(eventEntity.getDate());

                    Benutzer organisator = new Benutzer();
                    organisator.setVorname(eventEntity.getOrganisator().getVorname());
                    organisator.setName(eventEntity.getOrganisator().getName());
                    event.setOrganisator(organisator);

                    return event;
                }).toList());

        return benutzer;
    }
}