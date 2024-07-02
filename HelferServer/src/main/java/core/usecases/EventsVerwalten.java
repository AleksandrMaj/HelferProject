package core.usecases;

import core.entities.Benutzer;
import core.entities.Event;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.List;

@Stateless
public class EventsVerwalten implements IEventsVerwalten
{

    @EJB
    private EventManager eventManager;

    @Override
    public Event eventsAnlegen(Event event)
    {
        return eventManager.addEvent(event);
    }

    @Override
    public List<Event> getAllEvents()
    {
        List<Event> events = eventManager.getAllEvents();

        return events.stream()
                .map(event ->
                {
                    List<Benutzer> anonymHelferListe = event.getHelferListe().stream()
                            .map(helfer ->
                            {
                                helfer.anonymize();
                                return helfer;
                            })
                            .toList();
                    event.setHelferListe(anonymHelferListe);

                    event.getOrganisator().anonymizeWithoutName();
                    return event;
                })
                .toList();
    }

    @Override
    public Event getEventById(int id)
    {
        return eventManager.getEventById(id);
    }

    @Override
    public Event eventBearbeiten(Event event)
    {
        return eventManager.modifyEvent(event);
    }

    @Override
    public boolean eventLoeschen(int id)
    {
        return eventManager.deleteEvent(id);
    }

    @Override
    public boolean addHelfer(int eventID, Benutzer user)
    {
        return eventManager.addHelfer(eventID, user);
    }

    @Override
    public boolean removeHelfer(int eventID, Benutzer user)
    {
        return eventManager.removeHelfer(eventID, user);
    }
}
