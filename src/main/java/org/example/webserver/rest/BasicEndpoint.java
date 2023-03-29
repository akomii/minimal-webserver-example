package org.example.webserver.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.logging.Level;
import java.util.logging.Logger;


@Path("/the/best/rest")
public class BasicEndpoint {
    
    private static final Logger LOGGER = Logger.getLogger(BasicEndpoint.class.getName());
    @Inject
    private InjectableComponentInterface counter;
   
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getHelloWorld() {
    	int count = counter.countUp();
        LOGGER.log(Level.INFO, "I was called "+count);
        return "Hello World "+count;
    }
    
    @GET
    @Path("/user")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public BasicUser getBasicUser() {
        LOGGER.log(Level.INFO, "I return a User");
        return new BasicUser(1, "John", "Doe");
    }
}
