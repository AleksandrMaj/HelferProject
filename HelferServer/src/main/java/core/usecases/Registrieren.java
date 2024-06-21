package core.usecases;

import core.entities.Adresse;
import core.entities.Benutzer;
import core.enums.Benutzergruppe;
import dataaccess.BenutzerDAO;
import jakarta.ejb.EJB;
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

    @EJB
    private BenutzerDAO benutzerDAO;

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

    public BenutzerDAO getBenutzerDAO()
    {
        return benutzerDAO;
    }

    public void setBenutzerDAO(BenutzerDAO benutzerDAO)
    {
        this.benutzerDAO = benutzerDAO;
    }

    public String register() {
        Benutzer benutzer = new Benutzer();
        benutzer.setVorname(vorname);
        benutzer.setName(name);
        benutzer.setEmail(email);
        benutzer.setPasswort(password);
        benutzer.setAdresse(adresse);
        benutzer.setBenutzergruppe(Benutzergruppe.MITGLIED); // Set default group

        // Save the user using DAO
        benutzerDAO.save(benutzer);

        return "login.xhtml?faces-redirect=true"; // Redirect to login page
    }
}