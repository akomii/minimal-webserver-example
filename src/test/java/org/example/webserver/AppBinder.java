package org.example.webserver;

import org.example.webserver.rest.AppScopeComponent;
import org.example.webserver.rest.InjectableComponentInterface;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import lombok.Setter;

public class AppBinder extends AbstractBinder{

	@Setter
	AppScopeComponent appComponent;
	
	@Override
	protected void configure() {
		bind(appComponent).to(InjectableComponentInterface.class);
	}

}
