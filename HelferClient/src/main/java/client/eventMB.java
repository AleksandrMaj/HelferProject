package client;

import entities.Event;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.Converter;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;


@Named
@RequestScoped
public class eventMB {

    private Client client;
    private WebTarget target;
    private Event event;

    private int eventID;

    @Inject
    UserSession userSession;

    public eventMB() {
        client = ClientBuilder.newClient();
        target = client.target(Environment.BASE + "/event");
        event = new Event();
    }

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
        eventID = Integer.parseInt(params.get("eventId"));
        loadEvent();
    }

    public void loadEvent() {
        Response response = target
                .path("/" + eventID)
                .request(MediaType.APPLICATION_JSON)
                .header("Authentication",userSession.getToken())
                .get();

        if (response.getStatus() == 200) {
            event = response.readEntity(new GenericType<Event>() {});
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler beim Laden der Events", null));
        }
    }

    public String updateEvent() {
        Response response = target
                .request(MediaType.APPLICATION_JSON)
                .header("Authentication", userSession.getToken())
                .put(Entity.json(event));

        if (response.getStatus() == 200) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Event erfolgreich aktualisiert"));
            loadEvent(); // Liste aktualisieren
            return "dashboard?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler beim Aktualisieren des Events", null));
            return null;
        }
    }

    public String deleteEvent(int id) {
        Response response = target
                .path(String.valueOf(id))
                .request(MediaType.APPLICATION_JSON)
                .header("Authentication", userSession.getToken())
                .delete();
        if (response.getStatus() == 204) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Event erfolgreich gelöscht"));
            loadEvent();
            return "dashboard?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler beim Löschen des Events", null));
            return null;
        }
    }
    public void toggleHelfer(int eventId) {
        Response response = target
                .path(String.valueOf(eventId) + "/helfer")
                .request(MediaType.APPLICATION_JSON)
                .header("Authentication", userSession.getToken())
                .post(null);

        if (response.getStatus() == 200) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Helferstatus erfolgreich geändert"));
            loadEvent(); // Liste aktualisieren
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler beim Ändern des Helferstatus", null));
        }
    }

    // Getter und Setter
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

}
