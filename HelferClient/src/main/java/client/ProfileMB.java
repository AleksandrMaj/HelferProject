package client;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class ProfileMB
{
    @Inject
    UserSession userSession;

    public UserSession getUserSession()
    {
        return userSession;
    }

    public void setUserSession(UserSession userSession)
    {
        this.userSession = userSession;
    }
}
