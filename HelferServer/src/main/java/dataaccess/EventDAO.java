package dataaccess;

import core.entities.Benutzer;
import core.entities.Event;
import jakarta.ejb.Singleton;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

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
        em.flush();
        return event;
    }

    public List<Event> findAllEvents()
    {
        List<EventEntity> eventEntities = em.createQuery("SELECT e FROM EventEntity e", EventEntity.class).getResultList();
        return eventEntities.stream().map(EventEntity::toEvent).toList();
    }

    public Event findEventById(int id)
    {
        EventEntity eventEntity = em.find(EventEntity.class, id);
        return eventEntity != null ? eventEntity.toEvent() : null;
    }

    @Transactional
    public Event modifyEvent(Event event)
    {
        EventEntity eventEntity = em.find(EventEntity.class, event.getId());
        if (eventEntity != null)
        {
            eventEntity.setName(event.getName());
            eventEntity.setDate(event.getDate());

            em.merge(eventEntity);
            em.flush();
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
            em.flush();
            return true;
        }
        return false;
    }

    @Transactional
    public boolean addHelfer(int eventID, Benutzer user)
    {
        EventEntity eventEntity = em.find(EventEntity.class, eventID);
        if (eventEntity == null) return false;
        BenutzerEntity benutzerEntity = new BenutzerEntity(user);
        if (!eventEntity.getHelferListe().contains(benutzerEntity))
        {
            List<BenutzerEntity> modifiedHelperList = eventEntity.getHelferListe();
            modifiedHelperList.add(benutzerEntity);
            eventEntity.setHelferListe(modifiedHelperList);

            em.merge(eventEntity);
            em.flush();
            return true;
        }
        return false;
    }

    @Transactional
    public boolean removeHelfer(int eventID, Benutzer user)
    {
        EventEntity eventEntity = em.find(EventEntity.class, eventID);
        if (eventEntity == null) return false;

        BenutzerEntity helperToRemove = null;
        for (BenutzerEntity helfer : eventEntity.getHelferListe()) {
            if (helfer.getId() == user.getId()) {
                helperToRemove = helfer;
                break;
            }
        }

        if (helperToRemove != null) {
            List<BenutzerEntity> modifiedHelperList = eventEntity.getHelferListe();
            modifiedHelperList.remove(helperToRemove);
            eventEntity.setHelferListe(modifiedHelperList);

            em.merge(eventEntity);
            em.flush();
            return true;
        }
        return false;
    }
}
