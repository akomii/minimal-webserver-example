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

import lombok.AllArgsConstructor;
import org.example.webserver.server.AppScopeComponent;
import org.example.webserver.server.InjectableComponentInterface;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * A binder that binds the {@link AppScopeComponent} to the {@link InjectableComponentInterface}.
 * This allows the {@link AppScopeComponent} to be injected as a {@link InjectableComponentInterface}.
 */
@AllArgsConstructor
public class AppBinder extends AbstractBinder {
  
  private AppScopeComponent appScopeComponent;
  
  @Override
  protected void configure() {
    bind(appScopeComponent).to(InjectableComponentInterface.class);
  }
}
