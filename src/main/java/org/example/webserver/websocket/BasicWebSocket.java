package org.example.webserver.websocket;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

@Log
@ServerEndpoint("/the/best/websocket")
public class BasicWebSocket {
    
    private static final Set<Session> SESSIONS = new HashSet<>();
    
    @OnOpen
    public void onOpen(Session session) {
        log.log(Level.INFO, String.format("WebSocket opened for %s", session.getId()));
        SESSIONS.add(session);
    }
    
    @OnMessage
    public void onMessage(String message, Session session) throws EncodeException, IOException {
        log.log(Level.INFO, String.format("Message received from %s: %s", session.getId(), message));
        session.getBasicRemote().sendObject(message);
    }
    
    public void broadcastMessage(String message) {
        for (Session session : SESSIONS) {
            try {
                log.log(Level.INFO, String.format("Broadcasting message to %s: %s", session.getId(), message));
                session.getBasicRemote().sendObject(message);
            } catch (IOException | EncodeException e) {
                log.log(Level.SEVERE, "Failed to broadcast message: " + e.getMessage());
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
