package org.example.webserver.spring.websocket;

import jakarta.websocket.server.ServerEndpointConfig;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jakarta.server.config.JakartaWebSocketServletContainerInitializer;
import org.example.webserver.server.InjectableComponentInterface;
import org.example.webserver.server.websocket.BasicWebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for creating and customizing the Jetty WebSocket server for {@link BasicWebSocket}.
 */
@Configuration
public class BasicWebSocketConfig {
    
    @Autowired
    private InjectableComponentInterface counter;
    
    /**
     * Creates a new instance of {@link WebServerFactoryCustomizer} to customize the Jetty WebSocket server.
     * Adds a server customizer to configure the {@link BasicWebSocket} endpoint.
     */
    @Bean
    public WebServerFactoryCustomizer<JettyServletWebServerFactory> jettyWebSocketCustomizer() {
        return factory -> factory.addServerCustomizers(server -> {
            ServletContextHandler contextHandler = (ServletContextHandler) server.getHandler();
            JakartaWebSocketServletContainerInitializer.configure(contextHandler, (servletContext, serverContainer) -> {
                ServerEndpointConfig config = ServerEndpointConfig.Builder
                        .create(BasicWebSocket.class, "/the/best/websocket")
                        .configurator(new BasicWebSocketConfigurator(counter))
                        .build();
                serverContainer.addEndpoint(config);
            });
        });
    }
}
