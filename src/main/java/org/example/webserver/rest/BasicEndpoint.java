package org.example.webserver.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.logging.Level;
import java.util.logging.Logger;


@Path("/the/best/rest")
public class BasicEndpoint {
    
    private static final Logger LOGGER = Logger.getLogger(BasicEndpoint.class.getName());
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getHelloWorld() {
        LOGGER.log(Level.INFO, "I was called");
        return "Hello World";
    }
}
