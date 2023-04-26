package org.example.webserver.server;

import jakarta.websocket.ContainerProvider;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;
import jakarta.websocket.server.ServerEndpointConfig;
import jakarta.ws.rs.core.UriBuilder;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.jakarta.server.config.JakartaWebSocketServletContainerInitializer;
import org.example.webserver.server.rest.AppBinder;
import org.example.webserver.server.rest.BasicEndpoint;
import org.example.webserver.server.websocket.BasicWebSocket;
import org.example.webserver.server.websocket.BasicWebSocketConfigurator;
import org.example.webserver.server.websocket.WebSocketClient;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestWebServer {
    private static final int PORT = 8080;
    
    private static final URI BASE_ENDPOINT_URI = URI.create("http://localhost:" + PORT + "/the/best/rest");
    
    private static final URI WEBSOCKET_URI = URI.create("ws://localhost:" + PORT + "/the/best/websocket");
    
    private static Server jettyServer;
    
    @BeforeAll
    public static void startServer() throws Exception {
        jettyServer = new Server(PORT);
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setContextPath("/");
        AppScopeComponent appScopeComponent = new AppScopeComponent();
        registerRestEndpoints(contextHandler, appScopeComponent);
        registerWebSocketEndpoints(contextHandler, appScopeComponent);
        jettyServer.setHandler(contextHandler);
        jettyServer.start();
    }
    
    private static void registerRestEndpoints(ServletContextHandler contextHandler, AppScopeComponent appScopeComponent) {
        ResourceConfig config = new ResourceConfig();
        config.register(BasicEndpoint.class);
        config.register(new AppBinder(appScopeComponent));
        ServletContainer servletContainer = new ServletContainer(config);
        ServletHolder servletHolder = new ServletHolder(servletContainer);
        contextHandler.addServlet(servletHolder, "/*");
    }
    
    private static void registerWebSocketEndpoints(ServletContextHandler contextHandler, AppScopeComponent appScopeComponent) {
        JakartaWebSocketServletContainerInitializer.configure(contextHandler, (servletContext, serverContainer) -> {
            ServerEndpointConfig websocketConfig = ServerEndpointConfig.Builder
                    .create(BasicWebSocket.class, "/the/best/websocket")
                    .configurator(new BasicWebSocketConfigurator(appScopeComponent))
                    .build();
            serverContainer.addEndpoint(websocketConfig);
        });
    }
    
    @AfterAll
    public static void stopServer() throws Exception {
        if (jettyServer != null)
            jettyServer.stop();
    }
    
    @Order(1)
    @Test
    void testHelloWorld() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(BASE_ENDPOINT_URI).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals("You called Hello World 1 time", response.body());
        // second call
        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals("You called Hello World 2 times", response.body());
    }
    
    @Order(2)
    @Test
    void testBasicUserXML() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        URI userURI = UriBuilder.fromUri(BASE_ENDPOINT_URI).path("user").build();
        HttpRequest request = HttpRequest.newBuilder(userURI).GET().header("Accept", "application/xml").build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><user id=\"1\"><firstName>John</firstName><lastName>Doe</lastName></user>", response.body());
    }
    
    @Order(3)
    @Test
    void testBasicUserJSON() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        URI userURI = UriBuilder.fromUri(BASE_ENDPOINT_URI).path("user").build();
        HttpRequest request = HttpRequest.newBuilder(userURI).GET().header("Accept", "application/json").build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals("{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\"}", response.body());
    }
    
    /**
     * It shall say "3 times" as the counter of {@link InjectableComponentInterface} is shared with
     * the REST endpoint (see {@link #testHelloWorld()})
     */
    @Order(4)
    @Test
    void testWebSocketMessage() throws Exception {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        WebSocketClient client = new WebSocketClient(new LinkedBlockingQueue<>());
        try (Session session = container.connectToServer(client, WEBSOCKET_URI)) {
            session.getBasicRemote().sendText("Hello, WebSocket!");
            String receivedMessage = client.messageQueue.poll(5, TimeUnit.SECONDS);
            assertEquals("This endpoint was called 3 times", receivedMessage);
        }
    }
    
    @Order(5)
    @Test
    void testWebSocketBroadcast() throws Exception {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        // Init clients and connect to websocket
        WebSocketClient client1 = new WebSocketClient(new LinkedBlockingQueue<>());
        Session session1 = container.connectToServer(client1, WEBSOCKET_URI);
        WebSocketClient client2 = new WebSocketClient(new LinkedBlockingQueue<>());
        Session session2 = container.connectToServer(client2, WEBSOCKET_URI);
        // Broadcasting a message from the server
        BasicWebSocket basicWebSocket = new BasicWebSocket();
        String message1 = "Broadcast message 1";
        basicWebSocket.broadcastMessage(message1);
        // Check received messages
        String receivedMessage1 = client1.messageQueue.poll(5, TimeUnit.SECONDS);
        assertEquals(message1, receivedMessage1);
        String receivedMessage2 = client2.messageQueue.poll(5, TimeUnit.SECONDS);
        assertEquals(message1, receivedMessage2);
        // Close session1 and broadcast another message
        session1.close();
        TimeUnit.SECONDS.sleep(5); // wait a bit so that server also closes session1
        String message2 = "Broadcast message 2";
        basicWebSocket.broadcastMessage(message2);
        // Check received messages
        String receivedMessage3 = client1.messageQueue.poll(5, TimeUnit.SECONDS);
        assertNull(receivedMessage3, "Client 1 should not receive the message after disconnecting");
        String receivedMessage4 = client2.messageQueue.poll(5, TimeUnit.SECONDS);
        assertEquals(message2, receivedMessage4);
        // Close session2
        session2.close();
    }
}
