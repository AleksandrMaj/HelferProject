package facade;

import core.entities.Benutzer;
import core.usecases.BenutzerManager;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import core.enums.Benutzergruppe;

@Path("/")
public class Registrieren {
    @EJB
    private BenutzerManager benutzerManager;

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(Benutzer user) {
        try {
            // Set default values or perform additional logic
            user.setBenutzergruppe(Benutzergruppe.MITGLIED); // Set default user group if needed

            //TODO: Check so that no E-Mail is getting used twice

            // Save the user using benutzerDAO
            benutzerManager.addBenutzer(user);

            // Return a success response
            return Response.status(Response.Status.CREATED)
                    .entity(user)
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