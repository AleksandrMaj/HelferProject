package core.usecases;

import core.entities.Event;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.List;

@Stateless
public class EventsVerwalten implements IEventsVerwalten {

    @EJB
    private EventManager eventManager;

    @Override
    public Event eventsAnlegen(Event event) {
        return eventManager.addEvent(event);
    }

    public List<Event> getAllEvents() {
        return eventManager.getAllEvents();
    }

    public Event eventBearbeiten(Event event) {
        return eventManager.modifyEvent(event);
    }

    public boolean eventLoeschen(int id) {
         return eventManager.deleteEvent(id);
    }
}
