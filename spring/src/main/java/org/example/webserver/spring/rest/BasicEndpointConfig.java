package org.example.webserver.spring.rest;

import org.example.webserver.server.AppScopeComponent;
import org.example.webserver.server.rest.BasicEndpoint;
import org.example.webserver.spring.AppScopeComponentConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(AppScopeComponentConfig.class)
public class BasicEndpointConfig extends ResourceConfig {
    
    @Autowired
    private AppScopeComponent appScopeComponent;
    
    public BasicEndpointConfig() {
        BasicEndpoint basicEndpoint = new BasicEndpoint();
        basicEndpoint.setCounter(appScopeComponent);
        registerInstances(basicEndpoint);
    }
}
