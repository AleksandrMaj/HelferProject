package core.usecases;

import core.entities.Benutzer;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class Anmelden implements IAnmelden
{
    @EJB
    private BenutzerManager benutzerManager;

    @Override
    public Benutzer einloggen(Benutzer user)
    {
        return benutzerManager.benutzerSuchen(user.getEmail(), user.getPasswort());
    }
}