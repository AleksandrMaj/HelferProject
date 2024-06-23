package core.usecases;

import core.entities.Benutzer;

public interface IRegistrieren
{
    Benutzer neuenBenutzerRegistrieren(Benutzer benutzer);
}
