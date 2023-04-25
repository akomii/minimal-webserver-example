package org.example.webserver.spring.rest;

import org.example.webserver.server.AppScopeComponent;
import org.example.webserver.server.rest.BasicEndpoint;
import org.example.webserver.server.InjectableComponentInterface;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BasicEndpointConfig extends ResourceConfig {
    public BasicEndpointConfig() {
        register(BasicEndpoint.class);
    }
    
    @Bean
    public InjectableComponentInterface counter() {
        return new AppScopeComponent();
    }
}
