package dataaccess;

import core.entities.Adresse;
import core.entities.Benutzer;
import core.enums.Benutzergruppe;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
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

    public BenutzerEntity()
    {
    }

    public BenutzerEntity(String name, String vorname, Adresse adresse, Benutzergruppe benutzergruppe, String email, String passwort)
    {
        this.name = name;
        this.vorname = vorname;
        this.adresse = adresse;
        this.benutzergruppe = benutzergruppe;
        this.email = email;
        this.passwort = passwort;
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

    public Benutzer toBenutzer() {
        Benutzer benutzer = new Benutzer();
        benutzer.setId(this.id);
        benutzer.setName(this.name);
        benutzer.setVorname(this.vorname);
        benutzer.setAdresse(this.adresse);
        benutzer.setBenutzergruppe(this.benutzergruppe);
        benutzer.setEmail(this.email);
        benutzer.setPasswort(this.passwort);
        return benutzer;
    }
}