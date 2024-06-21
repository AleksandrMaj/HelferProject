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
        TypedQuery<Benutzer> query = em.createQuery(
                "SELECT b FROM Benutzer b WHERE b.email = :email AND b.passwort = :password", Benutzer.class);
        query.setParameter("email", username);
        query.setParameter("password", password);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public void save(Benutzer benutzer) {
        em.persist(benutzer);
    }
}
