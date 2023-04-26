package org.example.webserver.server.rest;

import lombok.AllArgsConstructor;
import org.example.webserver.server.AppScopeComponent;
import org.example.webserver.server.InjectableComponentInterface;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

@AllArgsConstructor
public class AppBinder extends AbstractBinder {
    
    private AppScopeComponent appScopeComponent;
    
    @Override
    protected void configure() {
        bind(appScopeComponent).to(InjectableComponentInterface.class);
    }
}
