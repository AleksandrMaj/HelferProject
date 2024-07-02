package facade;

import core.entities.Benutzer;
import core.entities.Event;
import core.enums.Benutzergruppe;
import core.services.Authentication;
import core.usecases.IEventsVerwalten;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.LinkedList;
import java.util.List;

@Path("/event")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ServicesEvent
{
    @EJB
    private IEventsVerwalten eventsVerwalten;

    @POST
    public Response createEvent(EventTO event, @HeaderParam("Authentication") String token)
    {
        if (Authentication.tokenIsValid(token))
        {
            Benutzer user = Authentication.getUserFromToken(token);

            if (user.getBenutzergruppe() != Benutzergruppe.ORGANISATOR)
                return Response.status(Response.Status.UNAUTHORIZED).entity("Nicht autorisiert").build();
        }

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
    @Path("/{id}")
    public Response getEventById(@PathParam("id") int id, @HeaderParam("Authentication") String token)
    {
        try
        {
            if (!Authentication.tokenIsValid(token))
            {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Nicht autorisiert").build();
            }
            Benutzer user = Authentication.getUserFromToken(token);

            Event event = eventsVerwalten.getEventById(id);
            if (event == null)
            {
                return Response.status(Response.Status.NOT_FOUND).entity("Event nicht gefunden").build();
            }

            if (user.getBenutzergruppe() == Benutzergruppe.MITGLIED || user.getBenutzergruppe() == Benutzergruppe.ORGANISATOR && !event.getOrganisator().equals(user))
            {
                event.getHelferListe().stream().map(helfer ->
                {
                    helfer.anonymize();
                    return helfer;
                });
            }

            return Response.ok(event).build();
        } catch (Exception e)
        {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Server-Fehler beim Abrufen des Events").build();
        }
    }


    @GET
    public Response getAllEvents()
    {
        try
        {
            List<Event> events = eventsVerwalten.getAllEvents();
            if (events.isEmpty())
                return Response.status(Response.Status.NO_CONTENT).build();

            return Response.ok(events).build();
        } catch (Exception e)
        {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Server-Fehler beim Abrufen der Events").build();
        }
    }

    @PUT
    public Response updateEvent(EventTO event, @HeaderParam("Authentication") String token)
    {
        if (Authentication.tokenIsValid(token))
        {
            Benutzer user = Authentication.getUserFromToken(token);

            if (user.getBenutzergruppe() != Benutzergruppe.ORGANISATOR || event.organisator().getId() != user.getId())
                return Response.status(Response.Status.UNAUTHORIZED).entity("Nicht autorisiert").build();
        }

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
    public Response deleteEvent(@PathParam("id") int id, @HeaderParam("Authentication") String token)
    {
        if (Authentication.tokenIsValid(token))
        {
            Benutzer user = Authentication.getUserFromToken(token);
            Event event = eventsVerwalten.getEventById(id);

            if (user.getBenutzergruppe() != Benutzergruppe.ORGANISATOR || event.getOrganisator().getId() != user.getId())
                return Response.status(Response.Status.UNAUTHORIZED).entity("Nicht autorisiert").build();
        }

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