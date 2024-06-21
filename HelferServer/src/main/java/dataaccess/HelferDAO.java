package dataaccess;

import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Singleton
public class HelferDAO
{
    @PersistenceContext
    private EntityManager em;
}
