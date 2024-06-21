package core.usecases;

import core.entities.Benutzer;
import jakarta.ejb.EJB;

public class HelferAuflisten
{
    @EJB
    EventManager eventManager;

    public Benutzer[] getHelfer(int eventID) {
        return new Benutzer[1];
    }
}
