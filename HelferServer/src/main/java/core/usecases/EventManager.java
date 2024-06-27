package core.usecases;

import core.entities.Event;
import dataaccess.EventDAO;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.List;

@Stateless
public class EventManager {

    @EJB
    private EventDAO eventDAO;

    public Event addEvent(Event event) {
        return eventDAO.addEvent(event);
    }

    public List<Event> getAllEvents() {
        return eventDAO.findAllEvents();
    }

    public Event modifyEvent(Event event) {
        return eventDAO.modifyEvent(event);
    }

    public void deleteEvent(int id) {
        eventDAO.deleteEvent(id);
    }
}
