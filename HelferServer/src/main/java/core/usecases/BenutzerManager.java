package core.usecases;

import core.entities.Benutzer;
import core.entities.Event;
import dataaccess.BenutzerDAO;
import dataaccess.HelferDAO;
import jakarta.ejb.Stateless;

import jakarta.ejb.EJB;

import java.util.List;

@Stateless
public class BenutzerManager
{
    @EJB
    private BenutzerDAO benutzerDAO;

    @EJB
    private HelferDAO helferDAO;

    public Benutzer benutzerSuchen(String username, String password)
    {
        return benutzerDAO.suchen(username, password);
    }

    public void addBenutzer(Benutzer newBenutzer) {
        benutzerDAO.save(newBenutzer);
    }

    public List<Event> getEventsFromBenutzer(int eventID) {
        return helferDAO.getAllEventsFromBenutzer(eventID);
    }
}
