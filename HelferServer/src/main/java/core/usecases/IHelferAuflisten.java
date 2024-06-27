package core.usecases;

import core.entities.Benutzer;
import core.entities.Event;
import java.util.List;

public interface IHelferAuflisten {
    List<Event> getAllEventsFromBenutzer(int benutzerID);
    List<Benutzer> getAllBenutzerFromEvent(int eventID);
}
