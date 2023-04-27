package org.example.webserver.server.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.Setter;
import lombok.extern.java.Log;
import org.example.webserver.server.InjectableComponentInterface;

import java.util.logging.Level;

/**
 * An endpoint that provides a "Hello World" message and a basic user object as a RESTful API.
 */
@Log
@Path("/the/best/rest")
public class BasicEndpoint {
    
    /**
     * The counter component to keep track of how many times the endpoint has been called.
     */
    @Inject
    @Setter
    private InjectableComponentInterface counter;
    
    /**
     * Provides a "Hello World" message in plain text format.
     * Increases the counter component by 1 each time this method is called.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getHelloWorld() {
        int count = counter.countUp();
        log.log(Level.INFO, String.format("I was called %d %s", count, (count == 1 ? "time" : "times")));
        return String.format("You called Hello World %d %s", count, (count == 1 ? "time" : "times"));
    }
    
    /**
     * Provides a basic user object in either XML or JSON format.
     */
    @GET
    @Path("/user")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public BasicUser getBasicUser() {
        log.log(Level.INFO, "I returned John Doe");
        return new BasicUser(1, "John", "Doe");
    }
}
