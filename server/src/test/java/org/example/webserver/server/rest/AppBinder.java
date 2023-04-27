package org.example.webserver.server.rest;

import lombok.AllArgsConstructor;
import org.example.webserver.server.AppScopeComponent;
import org.example.webserver.server.InjectableComponentInterface;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * A binder that binds the {@link AppScopeComponent} to the {@link InjectableComponentInterface}.
 * This allows the {@link AppScopeComponent} to be injected as a {@link InjectableComponentInterface}.
 */
@AllArgsConstructor
public class AppBinder extends AbstractBinder {
    
    private AppScopeComponent appScopeComponent;
    
    @Override
    protected void configure() {
        bind(appScopeComponent).to(InjectableComponentInterface.class);
    }
}
