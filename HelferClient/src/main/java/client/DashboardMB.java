package client;

import entities.Event;
import jakarta.annotation.PostConstruct;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class DashboardMB {

    private Client client;
    private WebTarget target;
    private Event event;
    private List<Event> events;
    private List<Event> myEvents;

    @Inject
    UserSession userSession;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy | HH:mm");

    public DashboardMB() {
        client = ClientBuilder.newClient();
        target = client.target(Environment.BASE + "/event");
        event = new Event();
    }

    @PostConstruct
    public void init() {
        loadEvents();
        loadMyEvents();
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

    public void loadMyEvents() {
        if (userSession.isOrganisator()) {
            myEvents = events.stream()
                    .filter(e -> e.getOrganisator().getId() == userSession.getLoggedInUser().getId() ||
                            e.getHelferListe().stream().anyMatch(h -> h.getId() == userSession.getLoggedInUser().getId()))
                    .collect(Collectors.toList());
            return;
        }
        myEvents = userSession.getLoggedInUser().getEvents();
    }

    public String createEvent() {
        if (event.getDate().isBefore(LocalDateTime.now())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Das Ereignisdatum liegt in der Vergangenheit", null));
            return null;
        }

        Response response = target
                .request(MediaType.APPLICATION_JSON)
                .header("Authentication", userSession.getToken())
                .post(Entity.json(event));

        if (response.getStatus() == 201) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Event erfolgreich erstellt"));
            loadEvents(); // Liste aktualisieren
            loadMyEvents(); // Persönliche Events aktualisieren
            event = new Event(); // Formular zurücksetzen
            return "dashboard?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler beim Erstellen des Events", null));
            return null;
        }
    }

    public boolean hasEvents() {
        return myEvents != null && !myEvents.isEmpty();
    }

    public String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }

    public String getProfileIconUrl() {
        if (userSession.getLoggedInUser() != null) {
            String seed = userSession.getLoggedInUser().getVorname() + " " + userSession.getLoggedInUser().getName();
            return "https://api.dicebear.com/9.x/initials/svg?seed=" + seed;
        }
        return "";
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

    public List<Event> getMyEvents() {
        return myEvents;
    }

    public void setMyEvents(List<Event> myEvents) {
        this.myEvents = myEvents;
    }
}
