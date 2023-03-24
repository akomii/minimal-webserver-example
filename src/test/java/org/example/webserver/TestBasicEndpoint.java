package org.example.webserver;


import org.eclipse.jetty.server.Server;
import org.example.webserver.rest.BasicEndpoint;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static junit.framework.TestCase.assertEquals;

public class TestBasicEndpoint {
    
    private static final String BASE_URI = "http://localhost:8080";
    private static Server jettyServer;
    
    @BeforeClass
    public static void startServer() throws Exception {
        ResourceConfig config = new ResourceConfig(BasicEndpoint.class);
        jettyServer = JettyHttpContainerFactory.createServer(URI.create(BASE_URI), config);
        jettyServer.start();
    }
    
    @AfterClass
    public static void stopServer() throws Exception {
        jettyServer.stop();
    }
    
    @Test
    public void testHelloWorld() throws IOException, InterruptedException {
        HttpClient HTTPCLIENT = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(BASE_URI + "/the/best/rest")).GET().build();
        HttpResponse<String> response = HTTPCLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals("Hello World", response.body());
    }
}
