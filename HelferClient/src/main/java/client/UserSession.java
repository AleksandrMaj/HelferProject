package client;

import entities.Benutzer;
import enums.Benutzergruppe;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;

@Named
@SessionScoped
public class UserSession implements Serializable {
    private Benutzer loggedInUser;
    private String token;

    public Benutzer getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Benutzer loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public boolean isUserInRole(Benutzergruppe benutzergruppe) {
        return loggedInUser != null && loggedInUser.getBenutzergruppe() == benutzergruppe; // Assuming Benutzer has a getRoles() method
    }

    public boolean isAdmin() {
        return loggedInUser != null && loggedInUser.getBenutzergruppe() == Benutzergruppe.ADMIN;
    }
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }
}