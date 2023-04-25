package org.example.webserver.server;

public class AppScopeComponent implements InjectableComponentInterface {
    private int counter;
    
    @Override
    public int countUp() {
        return ++counter;
    }
}
