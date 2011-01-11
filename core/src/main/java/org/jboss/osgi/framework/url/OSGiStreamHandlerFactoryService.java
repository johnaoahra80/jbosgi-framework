/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.osgi.framework.url;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

import org.jboss.osgi.framework.plugin.URLHandlerPlugin;

/**
 * A {@link URLStreamHandlerFactory} that provides {@link URLStreamHandler} instances
 * which are backed by an OSGi service.
 * 
 * The returned handler instances are proxies which allow the URL Stream Handler implementation
 * to be changed at a later point in time (the JRE caches the first URL Stream Handler returned
 * for a given protocol).
 *
 * @author <a href="david@redhat.com">David Bosschaert</a>
 * @author Thomas.Diesler@jboss.com
 * @since 10-Jan-2011
 */
public class OSGiStreamHandlerFactoryService implements URLStreamHandlerFactory
{
   private static URLHandlerPlugin delegate;
   
   public static void initStreamHandlerFactory(URLHandlerPlugin handlerPlugin)
   {
      delegate = handlerPlugin;
   }

   public static void destroyStreamHandlerFactory()
   {
      delegate = null;
   }

   @Override
   public URLStreamHandler createURLStreamHandler(String protocol)
   {
      return delegate != null ? delegate.createURLStreamHandler(protocol) : null;
   }
}
