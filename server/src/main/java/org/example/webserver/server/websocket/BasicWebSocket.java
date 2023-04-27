package org.example.webserver.server.websocket;

import jakarta.inject.Inject;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.Setter;
import lombok.extern.java.Log;
import org.example.webserver.server.InjectableComponentInterface;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

/**
 * A WebSocket endpoint that provides a count-up functionality and message broadcasting.
 */
@Log
@ServerEndpoint("/the/best/websocket")
public class BasicWebSocket {
    
    /**
     * The counter component to keep track of how many times the WebSocket endpoint has been called.
     */
    @Inject
    @Setter
    private InjectableComponentInterface counter;
    
    private static final Set<Session> SESSIONS = new HashSet<>();
    
    @OnOpen
    public void onOpen(Session session) {
        log.log(Level.INFO, String.format("WebSocket opened for %s", session.getId()));
        SESSIONS.add(session);
    }
    
    /**
     * Called when a message is received from a WebSocket session.
     * Increases the counter component by 1 and sends a message back to the session.
     */
    @OnMessage
    public void onMessage(String message, Session session) throws EncodeException, IOException {
        int count = counter.countUp();
        log.log(Level.INFO, String.format("Message received from %s: %s", session.getId(), message));
        session.getBasicRemote().sendObject(String.format("This endpoint was called %d %s", count, (count == 1 ? "time" : "times")));
    }
    
    /**
     * Sends a message to all connected WebSocket sessions.
     */
    public void broadcastMessage(String message) {
        for (Session session : SESSIONS) {
            try {
                log.log(Level.INFO, String.format("Broadcasting message to %s: %s", session.getId(), message));
                session.getBasicRemote().sendObject(message);
            } catch (IOException | EncodeException e) {
                log.log(Level.SEVERE, String.format("Failed to broadcast message: %s", e.getMessage()));
            }
        }
    }
    
    @OnClose
    public void onClose(Session session, CloseReason reason) {
        log.log(Level.INFO, String.format("WebSocket closed for %s: %s", session.getId(), reason.getReasonPhrase()));
        SESSIONS.remove(session);
    }
    
    @OnError
    public void onError(Session session, Throwable throwable) {
        log.log(Level.SEVERE, String.format("WebSocket error for %s: %s", session.getId(), throwable.getMessage()));
    }
}
