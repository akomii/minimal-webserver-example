package org.example.webserver.server.websocket;

import jakarta.websocket.server.ServerEndpointConfig.Configurator;
import lombok.AllArgsConstructor;
import org.example.webserver.server.AppScopeComponent;

@AllArgsConstructor
public class BasicWebSocketConfigurator extends Configurator {
    
    private AppScopeComponent appScopeComponent;
    
    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        T endpoint = super.getEndpointInstance(endpointClass);
        if (endpoint instanceof BasicWebSocket basicWebSocket)
            basicWebSocket.setCounter(appScopeComponent);
        return endpoint;
    }
}
