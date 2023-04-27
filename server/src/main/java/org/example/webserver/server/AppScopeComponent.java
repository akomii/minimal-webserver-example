package org.example.webserver.server;

/**
 * A component with application scope that implements the {@link InjectableComponentInterface}.
 * It provides a count-up functionality by increasing a counter each time it is called.
 */
public class AppScopeComponent implements InjectableComponentInterface {
    private int counter;
    
    @Override
    public int countUp() {
        return ++counter;
    }
}
