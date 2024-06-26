package facade;

import core.entities.Benutzer;
import core.entities.Event;
import core.usecases.IHelferAuflisten;
import core.usecases.IHelferVerwalten;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/helfer")
public class Helfer
{
    @EJB private IHelferVerwalten helferVerwalten;
    @EJB private IHelferAuflisten helferAuflisten;

    @GET
    @Path("/{eventID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllHelferFromEvent(@PathParam("eventID") int eventID) {
        try {
            List<Benutzer> benutzerList = helferAuflisten.getAllBenutzerFromEvent(eventID);
            return Response.ok(benutzerList).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/benutzer/{benutzerID}/events")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEventsFromBenutzer(@PathParam("benutzerID") int benutzerID) {
        try {
            List<Event> eventList = helferAuflisten.getAllEventsFromBenutzer(benutzerID);
            return Response.ok(eventList).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/{eventID}/{benutzerID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createHelfer(@PathParam("eventID") int eventID, @PathParam("benutzerID") int benutzerID) {
        try {
            helferVerwalten.addHelfer(eventID, benutzerID);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{eventID}/{benutzerID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteHelfer(@PathParam("eventID") int eventID, @PathParam("benutzerID") int benutzerID) {
        try {
            helferVerwalten.deleteHelfer(eventID, benutzerID);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
