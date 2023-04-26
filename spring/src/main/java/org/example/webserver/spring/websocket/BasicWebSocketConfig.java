package org.example.webserver.spring.websocket;

import jakarta.websocket.server.ServerEndpointConfig;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jakarta.server.config.JakartaWebSocketServletContainerInitializer;
import org.example.webserver.server.AppScopeComponent;
import org.example.webserver.server.websocket.BasicWebSocket;
import org.example.webserver.spring.AppScopeComponentConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(AppScopeComponentConfig.class)
public class BasicWebSocketConfig {
    
    @Autowired
    private AppScopeComponent appScopeComponent;
    
    @Bean
    public WebServerFactoryCustomizer<JettyServletWebServerFactory> jettyWebSocketCustomizer() {
        return factory -> factory.addServerCustomizers(server -> {
            ServletContextHandler contextHandler = (ServletContextHandler) server.getHandler();
            JakartaWebSocketServletContainerInitializer.configure(contextHandler, (servletContext, serverContainer) -> {
                ServerEndpointConfig config = ServerEndpointConfig.Builder
                        .create(BasicWebSocket.class, "/the/best/websocket")
                        .configurator(new BasicWebSocketConfigurator(appScopeComponent))
                        .build();
                serverContainer.addEndpoint(config);
            });
        });
    }
}
