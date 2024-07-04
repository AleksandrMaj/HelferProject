package facade;

import core.entities.Benutzer;
import core.services.Authentication;
import core.usecases.IAnmelden;
import core.usecases.IRegistrieren;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.regex.Pattern;

@Path("/auth")
public class ServicesBenutzer
{
    @EJB
    private IAnmelden anmelden;
    @EJB
    private IRegistrieren registrieren;
    @Inject
    private Authentication authentication;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );

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
                        .header("Authentication", "Bearer " + authentication.generateToken(benutzer.getId()))
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
        if (!isValidUserAccount(newUser))
        {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Ungültige Daten für Registrierung")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }

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

    private boolean isValidUserAccount(BenutzerTO user)
    {
        if (user.name() == null || user.name().trim().isEmpty()) return false;
        if (user.vorname() == null || user.vorname().trim().isEmpty()) return false;
        if (user.adresse() == null) return false;
        if (user.benutzergruppe() == null) return false;
        if (!EMAIL_PATTERN.matcher(user.email()).matches()) return false;
        return user.passwort() != null && !user.passwort().trim().isEmpty();
    }
}