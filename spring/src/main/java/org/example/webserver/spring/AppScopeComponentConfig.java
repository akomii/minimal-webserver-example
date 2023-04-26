package org.example.webserver.spring;

import org.example.webserver.server.AppScopeComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppScopeComponentConfig {
    
    @Bean
    public AppScopeComponent appScopeComponent() {
        return new AppScopeComponent();
    }
}
