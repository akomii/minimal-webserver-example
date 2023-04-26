package org.example.webserver.spring.websocket;

import jakarta.websocket.server.ServerEndpointConfig.Configurator;
import lombok.AllArgsConstructor;
import org.example.webserver.server.AppScopeComponent;
import org.example.webserver.server.websocket.BasicWebSocket;

@AllArgsConstructor
public class BasicWebSocketConfigurator extends Configurator {
    
    private final AppScopeComponent appScopeComponent;
    
    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        T endpoint = super.getEndpointInstance(endpointClass);
        if (endpoint instanceof BasicWebSocket basicWebSocket)
            basicWebSocket.setCounter(appScopeComponent);
        return endpoint;
    }
}