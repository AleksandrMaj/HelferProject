package core.usecases;

import core.entities.Benutzer;

public interface IAnmelden
{
    Benutzer einloggen(Benutzer user);
}
