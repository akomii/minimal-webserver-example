package org.example.webserver.spring.websocket;

import jakarta.websocket.server.ServerEndpointConfig.Configurator;
import lombok.AllArgsConstructor;
import org.example.webserver.server.InjectableComponentInterface;
import org.example.webserver.server.websocket.BasicWebSocket;

/**
 * Configurator class for setting the {@link InjectableComponentInterface} for {@link BasicWebSocket}.
 */
@AllArgsConstructor
public class BasicWebSocketConfigurator extends Configurator {
    
    private InjectableComponentInterface counter;
    
    /**
     * Overrides the {@link Configurator#getEndpointInstance(Class)} method to set the {@link InjectableComponentInterface}
     * for the {@link BasicWebSocket} instance.
     */
    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        T endpoint = super.getEndpointInstance(endpointClass);
        if (endpoint instanceof BasicWebSocket basicWebSocket)
            basicWebSocket.setCounter(counter);
        return endpoint;
    }
}