package core.usecases;

import core.entities.Benutzer;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class Registrieren implements IRegistrieren
{
    @EJB
    private BenutzerManager benutzerManager;

    @Override
    public Benutzer neuenBenutzerRegistrieren(Benutzer user)
    {
        if (benutzerManager.emailExists(user.getEmail()))
        {
            throw new IllegalArgumentException("E-Mail bereits registriert");
        }

        return benutzerManager.addBenutzer(user);
    }
}