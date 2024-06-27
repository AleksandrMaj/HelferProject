package core.usecases;

import core.entities.Benutzer;
import core.enums.Benutzergruppe;
import facade.BenutzerTO;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class Registrieren implements IRegistrieren {
    @EJB
    private BenutzerManager benutzerManager;

    @Override
    public Benutzer neuenBenutzerRegistrieren(BenutzerTO benutzer)
    {
        Benutzer newUser = benutzer.toBenutzer();
        newUser.setBenutzergruppe(Benutzergruppe.MITGLIED);

        //TODO: Check so that no E-Mail is getting used twice

        benutzerManager.addBenutzer(newUser);
        return newUser;
    }
}