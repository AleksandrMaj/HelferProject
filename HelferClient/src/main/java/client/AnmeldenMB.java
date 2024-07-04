package client;

import entities.Benutzer;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Named
@RequestScoped
public class AnmeldenMB
{
    @Inject
    private UserSession userSession;

    @NotNull(message = "E-Mail ist erforderlich")
    @Email(message = "Ungültiges E-Mail Format")
    private String username;

    @NotNull(message = "Passwort ist erforderlich")
    @Size(min = 1, message = "Passwort ist erforderlich")
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
    public String login()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        Client client = ClientBuilder.newClient();
        WebTarget request = client.target(Environment.BASE + "/auth/login");

        Benutzer loginRequest = new Benutzer();
        loginRequest.setEmail(username);
        loginRequest.setPasswort(password);

        Response response = request
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(loginRequest), Response.class);

        if (response.getStatus() == 200)
        {
            Benutzer benutzer = response.readEntity(Benutzer.class);
            userSession.setLoggedInUser(benutzer);
            userSession.setToken(response.getHeaderString("Authentication"));
            return "dashboard?faces-redirect=true";
        }

        if (response.getStatus() != 200)
        {
            String errorMessage = "E-Mail oder Passwort falsch";
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null));
        }
        return "";
    }
}
