package core.usecases;

import core.entities.Event;

import java.util.List;

public interface IEventsVerwalten
{

    Event eventsAnlegen(Event event);

    Event eventBearbeiten(Event event);

    boolean eventLoeschen(int id);

    List<Event> getAllEvents();

    Event getEventById(int id);
}
