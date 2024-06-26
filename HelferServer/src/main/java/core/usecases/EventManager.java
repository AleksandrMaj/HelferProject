package core.usecases;

import core.entities.Benutzer;
import dataaccess.HelferDAO;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.List;

@Stateless
public class EventManager
{
    @EJB
    HelferDAO helferDAO;

    public void addHelfer(int eventID, int benutzerID) {
        helferDAO.createBenutzerForEvent(eventID, benutzerID);
    }

    public void removeHelfer(int eventID, int benutzerID) {
        helferDAO.deleteBenutzerFromEvent(eventID, benutzerID);
    }

    public List<Benutzer> getHelferFromEvent(int eventID) {
        return helferDAO.getAllHelferFromEvent(eventID);
    }
}
