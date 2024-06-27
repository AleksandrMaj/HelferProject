package core.entities;

import core.enums.Benutzergruppe;

public class Benutzer {
    private int id;
    private String name;
    private String vorname;
    private Adresse adresse;
    private Benutzergruppe benutzergruppe;
    private String email;
    private String passwort;

    public Benutzer() {
    }

    public Benutzer(String name, String vorname, Adresse adresse, Benutzergruppe benutzergruppe, String email, String passwort) {
        this.name = name;
        this.vorname = vorname;
        this.adresse = adresse;
        this.benutzergruppe = benutzergruppe;
        this.email = email;
        this.passwort = passwort;
    }

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

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public Benutzergruppe getBenutzergruppe() {
        return benutzergruppe;
    }

    public void setBenutzergruppe(Benutzergruppe benutzergruppe) {
        this.benutzergruppe = benutzergruppe;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }
}
