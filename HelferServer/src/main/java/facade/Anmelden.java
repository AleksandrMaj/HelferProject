package facade;

import core.entities.Benutzer;
import core.usecases.BenutzerManager;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class Anmelden
{
    @EJB
    private BenutzerManager benutzerManager;

    @POST
    @Path("/login")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Benutzer user) {
        Benutzer benutzer = benutzerManager.benutzerSuchen(user.getEmail(), user.getPasswort());

        if (benutzer != null) {
            return Response.status(Response.Status.OK).entity(benutzer).type(MediaType.APPLICATION_JSON).build();
        }

        return Response.status(Response.Status.NOT_FOUND).entity("E-Mail oder Passwort falsch").type(MediaType.TEXT_PLAIN).build();
    }
}