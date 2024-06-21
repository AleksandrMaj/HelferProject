package core.usecases;

import core.entities.Benutzer;
import jakarta.ejb.Stateless;

@Stateless
public class EventManager
{
    //TODO: Add eventDAO

    public void addHelfer(int eventID, int benutzerID) {
        /*entityManager.getTransaction().begin();
        Benutzer benutzer = entityManager.find(Benutzer.class, benutzerID);
        if (benutzer != null) {
            Helfer helfer = new Helfer();
            helfer.setEventId(eventID);
            helfer.setMitglied(benutzer);
            entityManager.persist(helfer);
        } else {
            System.out.println("Benutzer mit ID " + benutzerID + " nicht gefunden.");
        }
        entityManager.getTransaction().commit();*/
    }

    public void removeHelfer(int eventID, int benutzerID) {
        /*entityManager.getTransaction().begin();
        TypedQuery<Helfer> query = entityManager.createQuery(
                "SELECT h FROM Helfer h WHERE h.eventId = :eventID AND h.mitglied.id = :benutzerID",
                Helfer.class);
        query.setParameter("eventID", eventID);
        query.setParameter("benutzerID", benutzerID);

        try {
            Helfer helfer = query.getSingleResult();
            entityManager.remove(helfer);
        } catch (Exception e) {
            System.out.println("Helfer nicht gefunden oder Fehler beim Löschen: " + e.getMessage());
        }
        entityManager.getTransaction().commit();*/
    }

    //TODO: Implement
    public Benutzer[] getHelferFromEvent(int eventID) {
        return new Benutzer[1];
    }
}
