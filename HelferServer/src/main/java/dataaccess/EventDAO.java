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

    public Event addEvent(Event event) {
        em.persist(event);
        return event;
    }

    public List<Event> findAllEvents() {
        return em.createQuery("SELECT e FROM Event e", Event.class).getResultList();
    }

    public Event modifyEvent(Event event) {
        return em.merge(event);
    }

    public void deleteEvent(int id) {
        Event event = em.find(Event.class, id);
        if (event != null) {
            em.remove(event);
        }
    }

}
