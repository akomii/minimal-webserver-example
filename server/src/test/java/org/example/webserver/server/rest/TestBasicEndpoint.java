package org.example.webserver.server.rest;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBasicEndpoint {
    
    private static final String BASE_URI = "http://localhost:8080";
    private static Server jettyServer;
    
    @BeforeAll
    public static void startServer() throws Exception {
        ResourceConfig config = new ResourceConfig(BasicEndpoint.class);
        AppScopeComponent inst = new AppScopeComponent();
        AppBinder binder = new AppBinder();
        binder.setAppComponent(inst);
        config.register(binder);
        jettyServer = JettyHttpContainerFactory.createServer(URI.create(BASE_URI), config);
        jettyServer.start();
    }
    
    @AfterAll
    public static void stopServer() throws Exception {
        if (jettyServer != null)
            jettyServer.stop();
    }
    
    @Test
    void testHelloWorld() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(BASE_URI + "/the/best/rest")).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals("You called Hello World 1 time", response.body());
        // second call
        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals("You called Hello World 2 times", response.body());
    }
    
    @Test
    void testBasicUserXML() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(BASE_URI + "/the/best/rest/user")).GET().header("Accept", "application/xml").build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><user id=\"1\"><firstName>John</firstName><lastName>Doe</lastName></user>", response.body());
    }
    
    @Test
    void testBasicUserJSON() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(BASE_URI + "/the/best/rest/user")).GET().header("Accept", "application/json").build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals("{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\"}", response.body());
    }
}
