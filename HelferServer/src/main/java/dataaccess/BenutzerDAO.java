package dataaccess;

import core.entities.Benutzer;
import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Singleton
public class BenutzerDAO
{
    @PersistenceContext
    private EntityManager em;

    public Benutzer findByCredentials(String username, String password) {
        TypedQuery<BenutzerEntity> query = em.createQuery(
                "SELECT b FROM BenutzerEntity b WHERE b.email = :email AND b.passwort = :password", BenutzerEntity.class);
        query.setParameter("email", username);
        query.setParameter("password", password);

        try {
            BenutzerEntity user = query.getSingleResult();
            user.setEvents(findEventsByHelper(user.getId()));
            return user.toBenutzer();
        } catch (Exception e) {
            return null;
        }
    }

    //Prevent not correct loading of helper events from a user
    private List<EventEntity> findEventsByHelper(int userId) {
        TypedQuery<EventEntity> query = em.createQuery(
                "SELECT e FROM EventEntity e JOIN e.helferListe h WHERE h.id = :userId", EventEntity.class);
        query.setParameter("userId", userId);

        return query.getResultList();
    }

    public Benutzer findById(int id) {
        BenutzerEntity entity = em.find(BenutzerEntity.class, id);
        if (entity != null) {
            return entity.toBenutzer();
        }
        return null;
    }

    public Benutzer findByEmail(String email) {
        TypedQuery<BenutzerEntity> query = em.createQuery(
                "SELECT b FROM BenutzerEntity b WHERE b.email = :email", BenutzerEntity.class);
        query.setParameter("email", email);

        try {
            BenutzerEntity entity = query.getSingleResult();
            return entity.toBenutzer();
        } catch (Exception e) {
            return null;
        }
    }

    public Benutzer save(Benutzer benutzer) {
        BenutzerEntity benutzerEntity = new BenutzerEntity(benutzer);
        em.persist(benutzerEntity);
        return benutzer;
    }
}