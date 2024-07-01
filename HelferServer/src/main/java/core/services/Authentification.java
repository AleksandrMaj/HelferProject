package core.services;

import jakarta.ejb.Singleton;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Singleton
public class Authentification
{
    private static final String SECRET_KEY = "VERTEILTE_ANWENDUNGEN"; // Use a more secure secret in production
    private static final long EXPIRATION_TIME = TimeUnit.HOURS.toMillis(10); // Token validity of 10 hours
    private static final Map<String, Integer> tokenStore = new HashMap<>(); // Store tokens in-memory

    public static String generateToken(int userID) {
        String token = Base64.getEncoder().encodeToString((userID + ":" + UUID.randomUUID().toString() + ":" + SECRET_KEY).getBytes());
        tokenStore.put(token, userID); // Store token with associated username
        return token;
    }

    public static boolean validateToken(String token) {
        return tokenStore.containsKey(token);
    }

    public static int getUserIdFromToken(String token) {
        return tokenStore.get(token);
    }

    public static void invalidateToken(String token) {
        tokenStore.remove(token);
    }
}
