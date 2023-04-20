package org.example.webserver.websocket;

import jakarta.websocket.ContainerProvider;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jakarta.server.config.JakartaWebSocketServletContainerInitializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TestBasicWebSocket {
    private static final int PORT = 8081;
    
    private static final URI DEFAULT_ENDPOINT = URI.create("ws://localhost:" + PORT + "/the/best/websocket");
    private static Server jettyWebSocketServer;
    
    @BeforeEach
    public void setUp() throws Exception {
        jettyWebSocketServer = new Server(PORT);
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");
        jettyWebSocketServer.setHandler(contextHandler);
        JakartaWebSocketServletContainerInitializer.configure(contextHandler, (servletContext, serverContainer) -> {
            serverContainer.addEndpoint(BasicWebSocket.class);
        });
        jettyWebSocketServer.start();
    }
    
    @AfterEach
    public void tearDown() throws Exception {
        if (jettyWebSocketServer != null) {
            jettyWebSocketServer.stop();
        }
    }
    
    @Test
    void testWebSocketMessage() throws Exception {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        WebSocketClient client = new WebSocketClient(new LinkedBlockingQueue<>());
        try (Session session = container.connectToServer(client, DEFAULT_ENDPOINT)) {
            session.getBasicRemote().sendText("Hello, WebSocket!");
            String receivedMessage = client.messageQueue.poll(5, TimeUnit.SECONDS);
            assertEquals("Hello, WebSocket!", receivedMessage);
        }
    }
    
    @Test
    void testWebSocketBroadcast() throws Exception {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        // Init clients and connect to websocket
        WebSocketClient client1 = new WebSocketClient(new LinkedBlockingQueue<>());
        Session session1 = container.connectToServer(client1, DEFAULT_ENDPOINT);
        WebSocketClient client2 = new WebSocketClient(new LinkedBlockingQueue<>());
        Session session2 = container.connectToServer(client2, DEFAULT_ENDPOINT);
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
        TimeUnit.SECONDS.sleep(5); // wait a bit so that server also closes session
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
