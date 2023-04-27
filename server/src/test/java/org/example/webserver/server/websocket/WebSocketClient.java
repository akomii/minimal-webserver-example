package org.example.webserver.server.websocket;

import jakarta.websocket.*;

import java.util.concurrent.BlockingQueue;

/**
 * A WebSocket client endpoint that listens for messages and adds them to a blocking queue.
 */
@ClientEndpoint
public class WebSocketClient {
    
    public BlockingQueue<String> messageQueue;
    
    public WebSocketClient(BlockingQueue<String> messageQueue) {
        this.messageQueue = messageQueue;
    }
    
    @OnOpen
    public void onOpen(Session session) {
        System.out.printf("WebSocket opened for %s%n", session.getId());
    }
    
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.printf("Recevied a message from %s : %s%n", session.getId(), message);
        messageQueue.add(message);
    }
    
    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.printf("WebSocket closed for %s : %s%n", session.getId(), reason.getCloseCode());
    }
    
    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.printf("WebSocket error for %s : %s%n", session.getId(), throwable.getMessage());
    }
}
