package client;

import entities.Adresse;
import entities.Benutzer;
import enums.Benutzergruppe;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Registrieren {

    private String vorname;
    private String name;
    private String email;
    private String password;
    private final Adresse adresse = new Adresse();

    // Getters and setters
    public String getVorname()
    {
        return vorname;
    }

    public void setVorname(String vorname)
    {
        this.vorname = vorname;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Adresse getAdresse()
    {
        return adresse;
    }

    public String register() {
        return "";
    }
}