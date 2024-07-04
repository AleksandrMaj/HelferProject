package client;

import entities.Adresse;
import entities.Benutzer;
import enums.Benutzergruppe;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Arrays;
import java.util.List;

@Named
@RequestScoped
public class RegistrierenMB
{
    @NotNull(message = "Vorname ist erforderlich")
    @Size(min = 1, message = "Vorname ist erforderlich")
    private String vorname;

    @NotNull(message = "Name ist erforderlich")
    @Size(min = 1, message = "Name ist erforderlich")
    private String name;

    @NotNull(message = "E-Mail ist erforderlich")
    @Email(message = "Ungültige E-Mail-Adresse")
    private String email;

    @NotNull(message = "Passwort ist erforderlich")
    @Size(min = 1, message = "Passwort ist erforderlich")
    private String password;

    private final Adresse adresse = new Adresse();
    private Benutzergruppe benutzergruppe;
    private List<Benutzergruppe> benutzergruppen;

    @PostConstruct
    public void init()
    {
        // Initialize benutzergruppen with enum values
        benutzergruppen = Arrays.asList(Benutzergruppe.values());
    }

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

    public List<Benutzergruppe> getBenutzergruppen()
    {
        return benutzergruppen;
    }

    public Benutzergruppe getBenutzergruppe()
    {
        return benutzergruppe;
    }

    public void setBenutzergruppe(Benutzergruppe benutzergruppe)
    {
        this.benutzergruppe = benutzergruppe;
    }

    // Register method
    public String register()
    {
        FacesContext context = FacesContext.getCurrentInstance();

        if (!adresse.isFilled()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Adresse notwendig", null));
            return "";
        }

        Client client = ClientBuilder.newClient();
        WebTarget request = client.target(Environment.BASE + "/auth/register");

        // Create a Benutzer object for the JSON request body
        Benutzer newUser = new Benutzer();
        newUser.setVorname(vorname);
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPasswort(password);
        newUser.setAdresse(adresse);
        newUser.setBenutzergruppe(benutzergruppe);

        Response response = request
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(newUser), Response.class);

        if (response.getStatus() == 201)
        {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrierung Erfolgreich", null));
            return "login?faces-redirect=true";
        }

        if (response.getStatus() != 201)
        {
            String errorMessage = "Registrierung fehlgeschlagen";
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null));
        }
        return "";
    }
}