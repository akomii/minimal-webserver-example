package org.example.webserver.spring.rest;

import org.example.webserver.server.InjectableComponentInterface;
import org.example.webserver.server.rest.BasicEndpoint;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for creating the {@link BasicEndpoint} instance and setting the {@link InjectableComponentInterface}.
 * <p>
 * Extends the {@link ResourceConfig} class to register the {@link BasicEndpoint} instance with the Jersey resource configuration.
 */
@Configuration
public class BasicEndpointConfig extends ResourceConfig {
    
    @Autowired
    private InjectableComponentInterface counter;
    
    /**
     * Constructs a new instance of {@link BasicEndpoint} and sets the {@link InjectableComponentInterface}.
     * Registers the {@link BasicEndpoint} instance with the Jersey resource configuration.
     */
    public BasicEndpointConfig() {
        BasicEndpoint basicEndpoint = new BasicEndpoint();
        basicEndpoint.setCounter(counter);
        registerInstances(basicEndpoint);
    }
}
