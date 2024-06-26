package dataaccess;


import core.entities.Event;
import jakarta.ejb.Singleton;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Singleton
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

    public List<Event> findAllEvents() {
        return em.createQuery("SELECT e FROM Event e", Event.class).getResultList();
    }

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
