package core.usecases;

import core.entities.Event;

import java.util.List;

public interface IEventsVerwalten
{

    Event eventAnlegen(Event event);

    Event eventBearbeiten(Event event);

    boolean eventLoeschen(int id);

    List<Event> getAllEvents();

    Event getEventById(int id);
}
