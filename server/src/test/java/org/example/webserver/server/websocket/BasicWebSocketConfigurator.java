package org.example.webserver.server.websocket;

import jakarta.websocket.server.ServerEndpointConfig;
import lombok.AllArgsConstructor;
import org.example.webserver.server.InjectableComponentInterface;

@AllArgsConstructor
public class BasicWebSocketConfigurator extends ServerEndpointConfig.Configurator {
    
    private InjectableComponentInterface counter;
    
    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        T endpoint = super.getEndpointInstance(endpointClass);
        if (endpoint instanceof BasicWebSocket)
            ((BasicWebSocket) endpoint).setCounter(counter);
        return endpoint;
    }
}
