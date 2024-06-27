package core.usecases;

import core.entities.Benutzer;
import core.entities.Event;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.List;

@Stateless
public class HelferAuflisten implements IHelferAuflisten
{
    @EJB private EventManager eventManager;
    @EJB private BenutzerManager benutzerManager;

    @Override
    public List<Event> getAllEventsFromBenutzer(int benutzerID)
    {
        return benutzerManager.getEventsFromBenutzer(benutzerID);
    }

    @Override
    public List<Benutzer> getAllBenutzerFromEvent(int eventID)
    {
        return eventManager.getHelferFromEvent(eventID);
    }
}
