package core.usecases;

import core.entities.Benutzer;
import core.entities.Event;
import dataaccess.EventDAO;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.List;

@Stateless
public class EventManager
{

    @EJB
    private EventDAO eventDAO;

    public Event addEvent(Event event)
    {
        return eventDAO.addEvent(event);
    }

    public List<Event> getAllEvents()
    {
        return eventDAO.findAllEvents();
    }

    public Event getEventById(int id)
    {
        return eventDAO.findEventById(id);
    }

    public Event modifyEvent(Event event)
    {
        return eventDAO.modifyEvent(event);
    }

    public boolean deleteEvent(int id)
    {
        return eventDAO.deleteEvent(id);
    }

    public boolean addHelfer(int eventID, Benutzer user) {
        return eventDAO.addHelfer(eventID, user);
    }

    public boolean removeHelfer(int eventID, Benutzer user) {
        return eventDAO.removeHelfer(eventID, user);

    }
}
