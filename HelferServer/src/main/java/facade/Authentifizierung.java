package facade;

import core.entities.Benutzer;
import core.enums.Benutzergruppe;
import core.usecases.BenutzerManager;
import core.usecases.IAnmelden;
import core.usecases.IRegistrieren;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
public class Authentifizierung
{
    @EJB private IAnmelden anmelden;
    @EJB private IRegistrieren registrieren;

    @POST
    @Path("/login")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Benutzer user) {
        Benutzer benutzer = anmelden.einloggen(user);

        if (benutzer != null) {
            return Response.status(Response.Status.OK).entity(benutzer).type(MediaType.APPLICATION_JSON).build();
        }

        return Response.status(Response.Status.NOT_FOUND).entity("E-Mail oder Passwort falsch").type(MediaType.TEXT_PLAIN).build();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(Benutzer newBenutzer) {
        try {
            Benutzer benutzer = registrieren.neuenBenutzerRegistrieren(newBenutzer);

            // Return a success response
            return Response.status(Response.Status.CREATED)
                    .entity(benutzer)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            // Handle exceptions and return an error response
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Registrierung fehlgeschlagen")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }
}
