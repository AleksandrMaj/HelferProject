package core.usecases;

import core.entities.Benutzer;
import facade.BenutzerTO;

public interface IRegistrieren
{
    Benutzer neuenBenutzerRegistrieren(BenutzerTO benutzer);
}
