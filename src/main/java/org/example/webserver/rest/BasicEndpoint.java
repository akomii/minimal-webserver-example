package org.example.webserver.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.java.Log;

import java.util.logging.Level;

@Log
@Path("/the/best/rest")
public class BasicEndpoint {
    
    @Inject
    private InjectableComponentInterface counter;
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getHelloWorld() {
        int count = counter.countUp();
        log.log(Level.INFO, "I was called " + count + " " + (count == 1 ? "time" : "times"));
        return "You called Hello World " + count + " " + (count == 1 ? "time" : "times");
    }
    
    @GET
    @Path("/user")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public BasicUser getBasicUser() {
        log.log(Level.INFO, "I return a User");
        return new BasicUser(1, "John", "Doe");
    }
}
