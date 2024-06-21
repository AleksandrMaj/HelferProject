package client;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Environment
{
    public static final String BASE = "http://localhost:8080/HelferServer/webapi";
}
