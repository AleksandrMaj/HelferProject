package client;

import entities.Benutzer;
import enums.Benutzergruppe;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class UserSession implements Serializable {
    private Benutzer loggedInUser;

    public Benutzer getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Benutzer loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public boolean isUserInRole(Benutzergruppe benutzergruppe) {
        return loggedInUser != null && loggedInUser.getBenutzergruppe() == benutzergruppe; // Assuming Benutzer has a getRoles() method
    }
}