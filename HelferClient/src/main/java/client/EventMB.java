package client;

import entities.Event;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.util.List;

@Named
@RequestScoped
public class EventMB {

    private Client client;
    private WebTarget target;
    private Event event;
    private List<Event> events;

    public EventMB() {
        client = ClientBuilder.newClient();
        target = client.target("http://localhost:8080/HelferServer/webapi/event");
        event = new Event();
        loadEvents();
    }

    public void loadEvents() {
        Response response = target
                .request(MediaType.APPLICATION_JSON)
                .get();

        if (response.getStatus() == 200) {
            events = response.readEntity(new GenericType<List<Event>>() {});
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler beim Laden der Events", null));
        }
    }

    public String createEvent() {
        Response response = target
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(event));

        if (response.getStatus() == 200) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Event erfolgreich erstellt"));
            loadEvents(); // Liste aktualisieren
            event = new Event(); // Formular zurücksetzen
            return "home?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler beim Erstellen des Events", null));
            return null;
        }
    }

    public String updateEvent() {
        Response response = target
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(event));

        if (response.getStatus() == 200) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Event erfolgreich aktualisiert"));
            loadEvents(); // Liste aktualisieren
            return "home?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler beim Aktualisieren des Events", null));
            return null;
        }
    }

    public String deleteEvent(int id) {
        Response response = target
                .path(String.valueOf(id))
                .request(MediaType.APPLICATION_JSON).delete();
        if (response.getStatus() == 204) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Event erfolgreich gelöscht"));
            loadEvents(); // Liste aktualisieren
            return "home?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler beim Löschen des Events", null));
            return null;
        }
    }

    // Getter und Setter
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

}
