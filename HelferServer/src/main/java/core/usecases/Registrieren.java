package core.usecases;

import core.entities.Benutzer;
import core.enums.Benutzergruppe;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class Registrieren implements IRegistrieren
{
    @EJB
    private BenutzerManager benutzerManager;

    @Override
    public Benutzer neuenBenutzerRegistrieren(Benutzer benutzer)
    {
        if (benutzerManager.emailExists(benutzer.getEmail()))
        {
            throw new IllegalArgumentException("E-Mail bereits registriert");
        }

        benutzer.setBenutzergruppe(Benutzergruppe.MITGLIED);
        return benutzerManager.addBenutzer(benutzer);
    }
}