package org.example.webserver;

import jakarta.servlet.Servlet;
import jakarta.websocket.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.websocket.server.JettyWebSocketServlet;
import org.eclipse.jetty.websocket.server.JettyWebSocketServletFactory;
import org.eclipse.jetty.websocket.server.config.JettyWebSocketServletContainerInitializer;
import org.example.webserver.websocket.BasicWebSocket;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;


public class TestBasicWebSocket {
    
    private static final int SERVER_PORT = 8081;
    private static final String BASE_URI = "ws://localhost:" + SERVER_PORT;
    private static Server jettyWebSocketServer;
    
    private static final CountDownLatch latch = new CountDownLatch(1);
    
    
    @BeforeClass
    public static void startServer() throws Exception {
        jettyWebSocketServer = new Server(SERVER_PORT);
        ContextHandlerCollection handlers = new ContextHandlerCollection();
        
        
        ResourceHandler rh = new ResourceHandler();
        rh.setDirectoriesListed(false);
        rh.setBaseResource(Resource.newClassPathResource("/WEB-STATIC/"));
        
        ContextHandler ch = new ContextHandler("/");
        ch.setHandler(rh);
        ch.clearAliasChecks();
        ch.setAllowNullPathInfo(true);
        
        handlers.addHandler(ch);
        
        Servlet websocketServlet = new JettyWebSocketServlet() {
            @Override
            protected void configure(JettyWebSocketServletFactory factory) {
                factory.addMapping("/the/best/websocket", (req, res) -> new BasicWebSocket());
            }
        };
        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.addServlet(new ServletHolder(websocketServlet), "/ws");
        JettyWebSocketServletContainerInitializer.configure(servletContextHandler, null);
        handlers.addHandler(servletContextHandler);
        
        jettyWebSocketServer.setHandler(handlers);
        jettyWebSocketServer.start();
    }
    
    @AfterClass
    public static void stopServer() throws Exception {
        jettyWebSocketServer.stop();
    }
    
    @Test
    public void testBasicWebSocket() throws Exception {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        BasicWebSocketClient endpoint = new BasicWebSocketClient();
        container.connectToServer(endpoint, new URI(BASE_URI + "/the/best/websocket"));
        
        Session session = endpoint.getSession();
        
        String msg = "Hello World";
        
        session.getBasicRemote().sendText(msg);
        
        latch.await(1000, TimeUnit.MILLISECONDS);
        
        assertEquals("You said: " + msg, endpoint.getMessage());
        
        session.close();
    }
    
    @ClientEndpoint
    public static class BasicWebSocketClient {
        private Session session;
        private String message;
        
        @OnOpen
        public void onOpen(Session session) {
            this.session = session;
        }
        
        @OnMessage
        public void onMessage(String message) {
            this.message = message;
            latch.countDown();
        }
        
        @OnClose
        public void onClose(CloseReason reason) {
            System.out.println("WebSocket closed: " + reason.getReasonPhrase());
        }
        
        public Session getSession() {
            return session;
        }
        
        public String getMessage() {
            return message;
        }
    }
}
