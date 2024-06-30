package dataaccess;

import core.entities.Benutzer;
import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Singleton
public class BenutzerDAO
{
    @PersistenceContext
    private EntityManager em;

    public Benutzer suchen(String username, String password) {
        TypedQuery<BenutzerEntity> query = em.createQuery(
                "SELECT b FROM BenutzerEntity b WHERE b.email = :email AND b.passwort = :password", BenutzerEntity.class);
        query.setParameter("email", username);
        query.setParameter("password", password);

        try {
            return query.getSingleResult().toBenutzer();
        } catch (Exception e) {
            return null;
        }
    }

    public void save(Benutzer benutzer) {
        BenutzerEntity benutzerEntity = new BenutzerEntity(benutzer);
        em.persist(benutzerEntity);
    }
}
