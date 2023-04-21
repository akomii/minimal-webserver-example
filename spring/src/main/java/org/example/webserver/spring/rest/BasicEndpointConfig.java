package org.example.webserver.spring.rest;

import org.example.webserver.server.rest.AppScopeComponent;
import org.example.webserver.server.rest.BasicEndpoint;
import org.example.webserver.server.rest.InjectableComponentInterface;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BasicEndpointConfig extends ResourceConfig {
    public BasicEndpointConfig() {
        register(BasicEndpoint.class);
    }
    
    @Bean
    public InjectableComponentInterface counter() {
        return new AppScopeComponent();
    }
}
