package client;

import entities.Event;
import enums.Benutzergruppe;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;


@Named
@ViewScoped
public class eventMB implements Serializable
{
    private Client client;
    private WebTarget target;
    private Event selectedEvent;
    private Event modifiedEvent = new Event();

    private int eventID;

    @Inject
    UserSession userSession;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy | HH:mm");

    public eventMB() {
        client = ClientBuilder.newClient();
        target = client.target(Environment.BASE + "/event");
        selectedEvent = new Event();
    }

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
        String eventIdParam = params.get("eventId");
        if (eventIdParam != null && !eventIdParam.isEmpty()) {
            try {
                eventID = Integer.parseInt(eventIdParam);
                loadEvent();
            } catch (NumberFormatException e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid event ID", null));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Event ID not provided", null));
        }
    }

    public void loadEvent() {
        Response response = target
                .path("/" + eventID)
                .request(MediaType.APPLICATION_JSON)
                .header("Authentication",userSession.getToken())
                .get();

        if (response.getStatus() == 200) {
            selectedEvent = response.readEntity(new GenericType<Event>() {});
            modifiedEvent.setDate(selectedEvent.getDate());
            modifiedEvent.setName(selectedEvent.getName());

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler beim Laden der Events", null));
        }
    }

    public String updateEvent() {
        if (modifiedEvent.getDate().isBefore(LocalDateTime.now())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Das Ereignisdatum liegt in der Vergangenheit", null));
            return null;
        }

        selectedEvent.setDate(modifiedEvent.getDate());
        selectedEvent.setName(modifiedEvent.getName());

        Response response = target
                .request(MediaType.APPLICATION_JSON)
                .header("Authentication", userSession.getToken())
                .put(Entity.json(selectedEvent));

        if (response.getStatus() == 200) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Event erfolgreich aktualisiert"));
            return "dashboard?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler beim Aktualisieren des Events", null));
            return null;
        }
    }

    public String deleteEvent() {
        Response response = target
                .path(String.valueOf(eventID))
                .request(MediaType.APPLICATION_JSON)
                .header("Authentication", userSession.getToken())
                .delete();

        if (response.getStatus() == 204) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Event erfolgreich gelöscht"));
            return "dashboard.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler beim Löschen des Events", null));
            return null;
        }
    }

    public boolean isOwner() {
        return selectedEvent.getOrganisator().getId() == userSession.getLoggedInUser().getId();
    }

    public boolean isMitglied() {
        return userSession.getLoggedInUser().getBenutzergruppe() == Benutzergruppe.MITGLIED;
    }

    public boolean isHelfer() {
        return selectedEvent.getHelferListe().stream().anyMatch(helper -> helper.getId() == userSession.getLoggedInUser().getId());
    }

    public boolean hasHelpers() {
        return selectedEvent != null && !selectedEvent.getHelferListe().isEmpty();
    }

    public boolean isEventWithinSevenDays() {
        if (selectedEvent != null && selectedEvent.getDate() != null) {
            return selectedEvent.getDate().isBefore(LocalDateTime.now().plusDays(7));
        }
        return false;
    }

    public String getHelferButtonText() {
        return isHelfer() ? "Ausschreiben" : "Einschreiben";
    }

    public String toggleHelfer() {
        Response response = target
                .path(eventID + "/helfer")
                .request(MediaType.APPLICATION_JSON)
                .header("Authentication", userSession.getToken())
                .post(null);

        if (response.getStatus() == 200) {
            Event helpingEvent = response.readEntity(new GenericType<Event>() {});

            List<Event> helferListe = userSession.getLoggedInUser().getEvents();
            boolean isInList = helferListe.stream().anyMatch(event -> event.getId() == helpingEvent.getId());

            if (isInList) {
                helferListe.removeIf(event -> event.getId() == helpingEvent.getId());
            } else {
                helferListe.add(helpingEvent);
            }
            userSession.getLoggedInUser().setEvents(helferListe);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Helferstatus erfolgreich geändert"));
            return "dashboard.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler beim Ändern des Helferstatus", null));
            return null;
        }
    }

    public String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }

    public Event getSelectedEvent()
    {
        return selectedEvent;
    }

    public void setSelectedEvent(Event selectedEvent)
    {
        this.selectedEvent = selectedEvent;
    }

    public int getEventID()
    {
        return eventID;
    }

    public void setEventID(int eventID)
    {
        this.eventID = eventID;
    }

    public Event getModifiedEvent()
    {
        return modifiedEvent;
    }

    public void setModifiedEvent(Event modifiedEvent)
    {
        this.modifiedEvent = modifiedEvent;
    }
}
