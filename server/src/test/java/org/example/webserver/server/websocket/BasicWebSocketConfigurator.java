package org.example.webserver.server.websocket;

import jakarta.websocket.server.ServerEndpointConfig.Configurator;
import lombok.AllArgsConstructor;
import org.example.webserver.server.AppScopeComponent;

@AllArgsConstructor
public class BasicWebSocketConfigurator extends Configurator {
    
    private AppScopeComponent appComponent;
    
    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        T endpoint = super.getEndpointInstance(endpointClass);
        if (endpoint instanceof BasicWebSocket)
            ((BasicWebSocket) endpoint).setCounter(appComponent);
        return endpoint;
    }
}
