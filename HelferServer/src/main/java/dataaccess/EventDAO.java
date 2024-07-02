package dataaccess;

import core.entities.Event;
import jakarta.ejb.Singleton;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

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

    public List<Event> findAllEvents()
    {
        List<EventEntity> eventEntities = em.createQuery("SELECT e FROM EventEntity e", EventEntity.class).getResultList();
        return eventEntities.stream().map(EventEntity::toEvent).toList();
    }

    public Event findEventById(int id) {
        EventEntity eventEntity = em.find(EventEntity.class, id);
        return eventEntity != null ? eventEntity.toEvent() : null;
    }


    public Event modifyEvent(Event event)
    {
        EventEntity eventEntity = em.find(EventEntity.class, event.getId());
        if (eventEntity != null)
        {
            //TODO: test if this is working properly after protecting routes
            eventEntity.setName(event.getName());
            eventEntity.setDate(event.getDate());
            eventEntity.setOrganisator(new BenutzerEntity(event.getOrganisator()));
            eventEntity.setHelferListe(event.getHelferListe().stream().map(BenutzerEntity::new).toList());

            em.merge(eventEntity);
            return eventEntity.toEvent();
        }
        throw new IllegalArgumentException("Kein Event gefunden mit der ID: " + event.getId());
    }

    public boolean deleteEvent(int id)
    {
        EventEntity eventEntity = em.find(EventEntity.class, id);
        if (eventEntity != null)
        {
            em.remove(eventEntity);
            return true;
        }
        return false;
    }
}
