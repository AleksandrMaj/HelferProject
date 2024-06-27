package dataaccess;

import core.entities.Event;
import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import core.entities.Benutzer;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Singleton
public class HelferDAO
{
    @PersistenceContext
    private EntityManager em;

    public List<Benutzer> getAllHelferFromEvent(int eventID)
    {
        TypedQuery<Benutzer> query = em.createQuery(
                "SELECT b FROM Benutzer b, Helfer h WHERE h.eventID = :eventID AND h.benutzerID = b.id", Benutzer.class);
        query.setParameter("eventID", eventID);
        return query.getResultList();
    }

    public List<Event> getAllEventsFromBenutzer(int benutzerID)
    {
        TypedQuery<Event> query = em.createQuery(
                "SELECT e FROM Event e, Helfer h WHERE h.benutzerID = :benutzerID AND h.eventID = e.eventID", Event.class);
        query.setParameter("benutzerID", benutzerID);
        return query.getResultList();
    }

    public void createBenutzerForEvent(int eventID, int benutzerID)
    {
        Query query = em.createNativeQuery("INSERT INTO Helfer (eventID, benutzerID) VALUES (?, ?)");
        query.setParameter(1, eventID);
        query.setParameter(2, benutzerID);
        query.executeUpdate();
    }

    public void deleteBenutzerFromEvent(int eventID, int benutzerID)
    {
        Query query = em.createNativeQuery("DELETE FROM Helfer WHERE eventID = ? AND benutzerID = ?");
        query.setParameter(1, eventID);
        query.setParameter(2, benutzerID);
        query.executeUpdate();
    }
}
