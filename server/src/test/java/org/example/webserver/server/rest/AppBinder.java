package org.example.webserver.server.rest;

import lombok.Setter;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class AppBinder extends AbstractBinder {
    
    @Setter
    AppScopeComponent appComponent;
    
    @Override
    protected void configure() {
        bind(appComponent).to(InjectableComponentInterface.class);
    }
}
