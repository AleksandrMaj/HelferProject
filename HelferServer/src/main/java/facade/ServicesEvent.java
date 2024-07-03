package facade;

import core.entities.Benutzer;
import core.entities.Event;
import core.enums.Benutzergruppe;
import core.services.Authentication;
import core.usecases.IEventsVerwalten;
import core.usecases.IHelferVerwalten;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
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

    @EJB
    private IHelferVerwalten helferVerwalten;

    @Inject
    private Authentication authentication;

    @POST
    public Response createEvent(EventTO event, @HeaderParam("Authentication") String token)
    {
        if (!authentication.tokenIsValid(token))
            return Response.status(Response.Status.UNAUTHORIZED).entity("Nicht autorisiert").build();

        Benutzer user = authentication.getUserFromToken(token);
        if (user.getBenutzergruppe() != Benutzergruppe.ORGANISATOR)
            return Response.status(Response.Status.UNAUTHORIZED).entity("Nicht autorisiert").build();

        if (event == null)
        {
            return Response.status(Response.Status.BAD_REQUEST).entity("Event-Daten sind erforderlich").build();
        }
        try
        {
            Event newEvent = event.toEvent();
            newEvent.setOrganisator(user);
            Event createdEvent = eventsVerwalten.eventAnlegen(newEvent);
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
            if (!authentication.tokenIsValid(token))
                return Response.status(Response.Status.UNAUTHORIZED).entity("Nicht autorisiert").build();

            Benutzer user = authentication.getUserFromToken(token);

            Event event = eventsVerwalten.getEventById(id);
            if (event == null)
                return Response.status(Response.Status.NOT_FOUND).entity("Event nicht gefunden").build();

            //TODO: CHECK
            event.getOrganisator().anonymizeWithoutName();
            event.getHelferListe().stream().map(helfer ->
            {
                helfer.anonymizeWithoutName();
                return helfer;
            });

            if (user.getBenutzergruppe() == Benutzergruppe.MITGLIED || user.getBenutzergruppe() == Benutzergruppe.ORGANISATOR && event.getOrganisator().getId() != user.getId())
            {
                List<Benutzer> anonymizedEvents = event.getHelferListe().stream().map(helfer ->
                {
                    helfer.anonymize();
                    return helfer;
                }).toList();
                event.setHelferListe(anonymizedEvents);
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
    public Response updateEvent(EventTO modifiedEventTO, @HeaderParam("Authentication") String token)
    {
        if (!authentication.tokenIsValid(token))
            return Response.status(Response.Status.UNAUTHORIZED).entity("Nicht autorisiert").build();

        if (modifiedEventTO.id() == 0)
            return Response.status(Response.Status.BAD_REQUEST).entity("Event-ID ist für die Aktualisierung erforderlich").build();

        Benutzer user = authentication.getUserFromToken(token);
        Event modifiedEvent = modifiedEventTO.toEvent();
        Event oldEvent = eventsVerwalten.getEventById(modifiedEvent.getId());

        if (oldEvent == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Event nicht gefunden").build();

        if (user.getBenutzergruppe() != Benutzergruppe.ORGANISATOR || oldEvent.getOrganisator().getId() != user.getId())
            return Response.status(Response.Status.UNAUTHORIZED).entity("Nicht autorisiert").build();

        try
        {
            Event updatedEvent = eventsVerwalten.eventBearbeiten(modifiedEvent);
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
        if (!authentication.tokenIsValid(token))
            return Response.status(Response.Status.UNAUTHORIZED).entity("Nicht autorisiert").build();

        Benutzer user = authentication.getUserFromToken(token);
        Event event = eventsVerwalten.getEventById(id);

        if (user.getBenutzergruppe() != Benutzergruppe.ORGANISATOR || event.getOrganisator().getId() != user.getId())
            return Response.status(Response.Status.UNAUTHORIZED).entity("Nicht autorisiert").build();

        try
        {
            eventsVerwalten.eventLoeschen(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e)
        {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Server-Fehler beim Löschen des Events").build();
        }
    }

    @POST
    @Path("/{id}/helfer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response toggleHelfer(@PathParam("id") int id, @HeaderParam("Authentication") String token)
    {
        if (!authentication.tokenIsValid(token))
            return Response.status(Response.Status.UNAUTHORIZED).entity("Nicht autorisiert").build();

        Benutzer user = authentication.getUserFromToken(token);
        if(user.getBenutzergruppe() == Benutzergruppe.ORGANISATOR)
            return Response.status(Response.Status.UNAUTHORIZED).entity("Nicht autorisiert").build();

        Event event = eventsVerwalten.getEventById(id);

        if (event == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Kein Event zu dieser ID").build();

        if (event.getHelferListe().stream().anyMatch(helfer -> helfer.getId() == user.getId()))
        {
            helferVerwalten.removeHelfer(id, user);
            return Response.ok(event).build();
        }
        helferVerwalten.addHelfer(id, user);
        return Response.ok(event).build();
    }
}