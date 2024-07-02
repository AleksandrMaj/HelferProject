package core.services;

import core.entities.Benutzer;
import core.usecases.BenutzerManager;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public class Authentication
{
    private static final String SECRET_KEY = "EventMaster";
    private static final Map<String, Integer> tokenStore = new HashMap<>();

    @EJB
    private BenutzerManager benutzerManager;

    public String generateToken(int userID) {
        String token = Base64.getEncoder().encodeToString((userID + ":" + UUID.randomUUID().toString() + ":" + SECRET_KEY).getBytes());
        tokenStore.put(token, userID);
        return token;
    }

    public boolean tokenIsValid(String token) {
        return tokenStore.containsKey(token.substring("Bearer ".length()));
    }

    public Benutzer getUserFromToken(String token) {
        int userID = tokenStore.get(token.substring("Bearer ".length()));
        return benutzerManager.findById(userID);
    }

    public static void invalidateToken(String token) {
        tokenStore.remove(token);
    }
}