package core.usecases;

import core.entities.Benutzer;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Anmelden
{
    @EJB
    private BenutzerManager benutzerManager;

    private String username;
    private String password;

    // Getter und Setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Login-Method
    public String login() {
        Benutzer benutzer = benutzerManager.benutzerSuchen(username, password);

        if (benutzer != null) {
            return "home.xhtml"; // Navigation to a success page
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login", "Username or password is incorrect."));
            return null; // Stay on the same page
        }
    }
}

