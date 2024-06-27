package core.usecases;

import core.entities.Benutzer;
import facade.BenutzerTO;

public interface IAnmelden
{
    Benutzer einloggen(BenutzerTO user);
}
