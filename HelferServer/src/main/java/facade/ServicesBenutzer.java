package facade;

import core.entities.Benutzer;
import core.services.Authentication;
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
public class ServicesBenutzer
{
    @EJB
    private IAnmelden anmelden;
    @EJB
    private IRegistrieren registrieren;

    @POST
    @Path("/login")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(BenutzerTO user)
    {
        try
        {
            Benutzer benutzer = anmelden.einloggen(user.toBenutzer());

            if (benutzer != null)
            {
                return Response
                        .ok(benutzer)
                        .header("Authentication", "Bearer " + Authentication.generateToken(benutzer.getId()))
                        .build();
            }
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("E-Mail oder Passwort falsch")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        } catch (Exception e)
        {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Anmeldung aufgrund eines Serverfehlers fehlgeschlagen")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(BenutzerTO newUser)
    {
        try
        {
            Benutzer user = registrieren.neuenBenutzerRegistrieren(newUser.toBenutzer());

            return Response.status(Response.Status.CREATED)
                    .entity(user)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (IllegalArgumentException e)
        {
            return Response.status(Response.Status.CONFLICT)
                    .entity(e.getMessage())
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        } catch (Exception e)
        {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Registrierung aufgrund eines Server-Fehlers fehlgeschlagen")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }
}