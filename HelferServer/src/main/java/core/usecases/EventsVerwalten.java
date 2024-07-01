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

    //TODO: Remove password and so on when getting list of helfers

    public Event eventBearbeiten(Event event) {
        return eventManager.modifyEvent(event);
    }

    public void eventLoeschen(int id) {
         eventManager.deleteEvent(id);
    }
}
