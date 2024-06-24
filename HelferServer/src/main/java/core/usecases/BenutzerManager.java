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
        return benutzerDAO.suchen(username, password);
    }

    public void addBenutzer(Benutzer newBenutzer) {
        benutzerDAO.save(newBenutzer);
    }

    //TODO: Implement
    public void getHelferEventsFromBenutzer(int eventID) {
    }
}
