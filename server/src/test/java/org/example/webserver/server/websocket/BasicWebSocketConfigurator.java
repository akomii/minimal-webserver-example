package org.example.webserver.server.websocket;

import jakarta.websocket.server.ServerEndpointConfig.Configurator;
import lombok.AllArgsConstructor;
import org.example.webserver.server.InjectableComponentInterface;

/**
 * A WebSocket endpoint configurator that sets the {@link InjectableComponentInterface} for the {@link BasicWebSocket}.
 */
@AllArgsConstructor
public class BasicWebSocketConfigurator extends Configurator {
    
    private InjectableComponentInterface counter;
    
    /**
     * Sets the {@link InjectableComponentInterface} for the {@link BasicWebSocket} if it is an instance of {@link BasicWebSocket}.
     */
    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        T endpoint = super.getEndpointInstance(endpointClass);
        if (endpoint instanceof BasicWebSocket basicWebSocket)
            basicWebSocket.setCounter(counter);
        return endpoint;
    }
}
