package core.usecases;

import core.entities.Benutzer;
import dataaccess.BenutzerDAO;
import jakarta.ejb.Stateless;

import jakarta.ejb.EJB;

@Stateless
public class BenutzerManager
{
    @EJB
    private BenutzerDAO benutzerDAO;

    public Benutzer benutzerSuchen(String username, String password)
    {
        return benutzerDAO.findByCredentials(username, password);
    }

    public Benutzer addBenutzer(Benutzer newBenutzer) {
        return benutzerDAO.save(newBenutzer);
    }

    public boolean emailExists(String email) {
        return benutzerDAO.findByEmail(email) != null;
    }

    public Benutzer findById(int id) {
        return benutzerDAO.findById(id);
    }
}
