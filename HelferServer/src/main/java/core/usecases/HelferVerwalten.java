package core.usecases;

import core.entities.Benutzer;
import jakarta.ejb.EJB;

public class HelferVerwalten implements IHelferVerwalten
{
    @EJB
    private EventManager eventManager;

    @Override
    public boolean addHelfer(int eventID, Benutzer user)
    {
        return eventManager.addHelfer(eventID, user);

    }

    @Override
    public boolean removeHelfer(int eventID, Benutzer user)
    {
        return eventManager.removeHelfer(eventID, user);

    }
}
