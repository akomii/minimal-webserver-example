/*
 * MIT License
 *
 * Copyright (c) 2023 Alexander Kombeiz
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
