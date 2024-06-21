package client;

import entities.Adresse;
import entities.Benutzer;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Named
@RequestScoped
public class RegistrierenMB
{
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

    // Register method
    public String register() {
        Client client = ClientBuilder.newClient();
        WebTarget request = client.target(Environment.BASE + "/register");

        // Create a Benutzer object for the JSON request body
        Benutzer newUser = new Benutzer();
        newUser.setVorname(vorname);
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPasswort(password);
        newUser.setAdresse(adresse);

        Response response = request
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(newUser), Response.class);

        if (response.getStatus() == 201) {
            // Assuming the response contains a JSON object with user details
            Benutzer registeredUser = response.readEntity(Benutzer.class);
            // Optionally, store the user information or perform other actions
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrierung Erfolgreich", null));
            return "login?faces-redirect=true";
        }

        if (response.getStatus() != 201){
            // Handle registration failure
            String errorMessage = "Registrierung fehlgeschlagen";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null));
        }
        return "";
    }
}