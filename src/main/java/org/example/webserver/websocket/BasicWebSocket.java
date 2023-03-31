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
        log.log(Level.INFO, "WebSocket opened for " + session.getId());
        SESSIONS.add(session);
    }
    
    @OnMessage
    public void onMessage(String message, Session session) throws EncodeException, IOException {
        log.log(Level.INFO, "Message received from " + session.getId() + ": " + message);
        session.getBasicRemote().sendObject(message);
    }
    
    public void broadcastMessage(String message) throws EncodeException, IOException {
        for (Session session : SESSIONS) {
            session.getBasicRemote().sendObject(message);
        }
    }
    
    @OnClose
    public void onClose(Session session, CloseReason reason) {
        log.log(Level.INFO, "WebSocket closed for " + session.getId() + ": " + reason.getReasonPhrase());
        SESSIONS.remove(session);
    }
    
    @OnError
    public void onError(Session session, Throwable throwable) {
        log.log(Level.SEVERE, "WebSocket error for " + session.getId() + ": " + throwable.getMessage());
    }
}
