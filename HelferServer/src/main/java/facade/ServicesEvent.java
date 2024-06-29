package facade;

import core.entities.Event;
import core.usecases.IEventsVerwalten;
import jakarta.annotation.security.PermitAll;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/events")
public class ServicesEvent{

    @EJB
    private IEventsVerwalten eventsVerwalten;

    @POST
    public Response createEvent(Event event) {
        Event createdEvent = eventsVerwalten.eventsAnlegen(event);
        return Response.ok(createdEvent).build();
    }

    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public Response testService() {
        return Response.ok("Service is up and running").build();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEvents() {
        List<Event> events = eventsVerwalten.getAllEvents();
        return Response.ok(events).build();
    }

    @PUT
    public Response updateEvent(Event event) {
        Event updatedEvent = eventsVerwalten.eventBearbeiten(event);
        return Response.ok(updatedEvent).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteEvent(@PathParam("id") int id) {
        eventsVerwalten.eventLoeschen(id);
        return Response.noContent().build();
    }
}
