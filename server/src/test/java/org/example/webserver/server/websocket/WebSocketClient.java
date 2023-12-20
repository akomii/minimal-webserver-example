/*
 * MIT License
 *
 * Copyright (c) 2023 Alexander Kombeiz
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
