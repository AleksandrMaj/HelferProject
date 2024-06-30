package client;

import core.entities.Event;
import core.usecases.IEventsVerwalten;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import java.util.List;

@Named
@RequestScoped
public class EventMB {

    @EJB
    private IEventsVerwalten eventsVerwalten;

    private Event event;
    private List<Event> events;

    @PostConstruct
    public void init() {
        event = new Event();
        events = eventsVerwalten.getAllEvents();
    }

    public String createEvent() {
        try {
            eventsVerwalten.eventsAnlegen(event);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Event erfolgreich erstellt"));
            event = new Event(); // Formular zurücksetzen
            events = eventsVerwalten.getAllEvents(); // Liste aktualisieren
            return "home?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler beim Erstellen des Events", null));
            return null;
        }
    }

    public String updateEvent() {
        try {
            eventsVerwalten.eventBearbeiten(event);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Event erfolgreich aktualisiert"));
            events = eventsVerwalten.getAllEvents(); // Liste aktualisieren
            return "home?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler beim Aktualisieren des Events", null));
            return null;
        }
    }

    public String deleteEvent(int id) {
        try {
            eventsVerwalten.eventLoeschen(id);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Event erfolgreich gelöscht"));
            events = eventsVerwalten.getAllEvents(); // Liste aktualisieren
            return "home?faces-redirect=true";
        } catch (Exception e) {
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
}
