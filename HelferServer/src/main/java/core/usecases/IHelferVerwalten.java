package core.usecases;

import core.entities.Benutzer;

public interface IHelferVerwalten {
    boolean addHelfer(int eventID, Benutzer user);
    boolean removeHelfer(int eventID, Benutzer user);
}
