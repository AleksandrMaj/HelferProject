package core.usecases;

import core.entities.Benutzer;
import core.enums.Benutzergruppe;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class Registrieren implements IRegistrieren {
    @EJB
    private BenutzerManager benutzerManager;

    @Override
    public Benutzer neuenBenutzerRegistrieren(Benutzer benutzer)
    {
        benutzer.setBenutzergruppe(Benutzergruppe.MITGLIED); // Set default user group if needed

        //TODO: Check so that no E-Mail is getting used twice

        benutzerManager.addBenutzer(benutzer);
        return benutzer;
    }
}