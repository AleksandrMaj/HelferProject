package core.usecases;

import core.entities.Benutzer;
import facade.BenutzerTO;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class Anmelden implements IAnmelden
{
    @EJB
    private BenutzerManager benutzerManager;

    @Override
    public Benutzer einloggen(BenutzerTO user)
    {
        return benutzerManager.benutzerSuchen(user.email(), user.passwort());
    }
}