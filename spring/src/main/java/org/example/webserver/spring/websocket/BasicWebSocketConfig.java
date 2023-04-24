package org.example.webserver.spring.websocket;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jakarta.server.config.JakartaWebSocketServletContainerInitializer;
import org.example.webserver.server.websocket.BasicWebSocket;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BasicWebSocketConfig {
    
    @Bean
    public WebServerFactoryCustomizer<JettyServletWebServerFactory> jettyWebSocketCustomizer() {
        return factory -> factory.addServerCustomizers(server -> {
            ServletContextHandler contextHandler = (ServletContextHandler) server.getHandler();
            JakartaWebSocketServletContainerInitializer.configure(contextHandler, (servletContext, serverContainer) -> {
                serverContainer.addEndpoint(BasicWebSocket.class);
            });
        });
    }
}
