package dataaccess;

import com.sun.tools.javac.comp.Todo;
import core.entities.Event;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class EventDAO {

    @PersistenceContext
    private EntityManager em;

    public Event createEvent(Event event) {
        em.persist(event);
        return event;
    }

    public Event findEventById(int id) {
        return em.find(Event.class, id);
    }

    //Todo: Error? Blicke nicht genau durch warum hab nur so eine Ahnung
    /*public List<Event> findAllEvents() {
        return em.createQuery("SELECT e FROM Event e", Event.class).getResultList();
    }*/

    public Event updateEvent(Event event) {
        return em.merge(event);
    }

    public void deleteEvent(int id) {
        Event event = findEventById(id);
        if (event != null) {
            em.remove(event);
        }
    }
}
