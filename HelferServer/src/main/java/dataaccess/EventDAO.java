package dataaccess;


import core.entities.Event;
import jakarta.ejb.Singleton;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Singleton
public class EventDAO
{

    @PersistenceContext
    private EntityManager em;

    public Event addEvent(Event event)
    {
        EventEntity eventEntity = new EventEntity(event);
        em.persist(eventEntity);
        return event;
    }

    public List<Event> findAllEvents() {
        List<EventEntity> eventEntities = em.createQuery("SELECT e FROM EventEntity e", EventEntity.class).getResultList();
        return eventEntities.stream().map(EventEntity::toEvent).toList();
    }

    public Event modifyEvent(Event event)
    {
        EventEntity eventEntity = new EventEntity(event);
        em.merge(eventEntity);
        return event;
    }

    public void deleteEvent(int id)
    {
        EventEntity eventEntity = em.find(EventEntity.class, id);
        if (eventEntity != null)
        {
            em.remove(eventEntity);
        }
    }
}

//TODO: Correct return statements, because sometimes we need booleon or the object
