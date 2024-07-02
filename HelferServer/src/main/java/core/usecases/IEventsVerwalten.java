package core.usecases;

import core.entities.Benutzer;
import core.entities.Event;

import java.util.List;

public interface IEventsVerwalten
{

    Event eventsAnlegen(Event event);

    Event eventBearbeiten(Event event);

    boolean eventLoeschen(int id);

    List<Event> getAllEvents();

    Event getEventById(int id);

    boolean addHelfer(int eventID, Benutzer user);

    boolean removeHelfer(int eventID, Benutzer user);
}
