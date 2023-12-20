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

package org.example.webserver.spring.websocket;

import jakarta.websocket.server.ServerEndpointConfig.Configurator;
import lombok.AllArgsConstructor;
import org.example.webserver.server.InjectableComponentInterface;
import org.example.webserver.server.websocket.BasicWebSocket;

/**
 * Configurator class for setting the {@link InjectableComponentInterface} for {@link BasicWebSocket}.
 */
@AllArgsConstructor
public class BasicWebSocketConfigurator extends Configurator {
  
  private InjectableComponentInterface counter;
  
  /**
   * Overrides the {@link Configurator#getEndpointInstance(Class)} method to set the {@link InjectableComponentInterface}
   * for the {@link BasicWebSocket} instance.
   */
  @Override
  public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
    T endpoint = super.getEndpointInstance(endpointClass);
    if (endpoint instanceof BasicWebSocket basicWebSocket)
      basicWebSocket.setCounter(counter);
    return endpoint;
  }
}