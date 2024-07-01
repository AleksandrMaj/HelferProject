package facade;

import core.entities.Event;
import core.usecases.IEventsVerwalten;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/event")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ServicesEvent
{
    @EJB
    private IEventsVerwalten eventsVerwalten;

    @POST
    public Response createEvent(EventTO event)
    {
        if (event == null)
        {
            return Response.status(Response.Status.BAD_REQUEST).entity("Event-Daten sind erforderlich").build();
        }
        try
        {
            Event createdEvent = eventsVerwalten.eventsAnlegen(event.toEvent());
            return Response.status(Response.Status.CREATED).entity(createdEvent).build();
        } catch (Exception e)
        {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Server-Fehler beim Erstellen eines Events").build();
        }
    }

    @GET
    public Response getAllEvents()
    {
        try
        {
            List<Event> events = eventsVerwalten.getAllEvents();
            if (events.isEmpty())
            {
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            return Response.ok(events).build();
        } catch (Exception e)
        {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Server-Fehler beim Abrufen der Events").build();
        }
    }

    @PUT
    public Response updateEvent(EventTO event)
    {
        if (event == null || event.id() == 0)
        {
            return Response.status(Response.Status.BAD_REQUEST).entity("Event-ID ist für die Aktualisierung erforderlich").build();
        }
        try
        {
            Event updatedEvent = eventsVerwalten.eventBearbeiten(event.toEvent());
            return Response.ok(updatedEvent).build();
        } catch (Exception e)
        {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Server-Fehler beim Aktualisieren des Events").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteEvent(@PathParam("id") int id)
    {
        try
        {
            eventsVerwalten.eventLoeschen(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e)
        {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Server-Fehler beim Löschen des Events").build();
        }
    }
}
