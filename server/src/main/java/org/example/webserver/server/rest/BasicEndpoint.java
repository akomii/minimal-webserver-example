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

package org.example.webserver.server.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.Setter;
import lombok.extern.java.Log;
import org.example.webserver.server.InjectableComponentInterface;

import java.util.logging.Level;

/**
 * An endpoint that provides a "Hello World" message and a basic user object as a RESTful API.
 */
@Log
@Path("/the/best/rest")
public class BasicEndpoint {
  
  /**
   * The counter component to keep track of how many times the endpoint has been called.
   */
  @Inject
  @Setter
  private InjectableComponentInterface counter;
  
  /**
   * Provides a "Hello World" message in plain text format.
   * Increases the counter component by 1 each time this method is called.
   */
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String getHelloWorld() {
    int count = counter.countUp();
    log.log(Level.INFO, String.format("I was called %d %s", count, (count == 1 ? "time" : "times")));
    return String.format("You called Hello World %d %s", count, (count == 1 ? "time" : "times"));
  }
  
  /**
   * Provides a basic user object in either XML or JSON format.
   */
  @GET
  @Path("/user")
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public BasicUser getBasicUser() {
    log.log(Level.INFO, "I returned John Doe");
    return new BasicUser(1, "John", "Doe");
  }
}
