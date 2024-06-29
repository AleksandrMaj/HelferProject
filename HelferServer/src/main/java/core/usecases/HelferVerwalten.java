package core.usecases;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class HelferVerwalten implements IHelferVerwalten
{
    @EJB private EventManager eventManager;

    @Override
    public void addHelfer(int eventID, int benutzerID)
    {
        //eventManager.addHelfer(eventID, benutzerID);
    }

    @Override
    public void deleteHelfer(int eventID, int benutzerID)
    {
        //eventManager.removeHelfer(eventID, benutzerID); //TODO:
    }
}
