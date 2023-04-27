package org.example.webserver.spring;

import org.example.webserver.server.AppScopeComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for creating the {@link AppScopeComponent} bean.
 */
@Configuration
public class AppScopeComponentConfig {
    
    /**
     * Creates a new instance of {@link AppScopeComponent} and adds it to the Spring application context as a singleton bean.
     */
    @Bean
    public AppScopeComponent appScopeComponent() {
        return new AppScopeComponent();
    }
}
