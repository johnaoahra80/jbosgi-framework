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
package org.jboss.osgi.framework.internal;

import org.jboss.logging.Logger;
import org.jboss.msc.service.ServiceBuilder;
import org.jboss.msc.service.ServiceController.Mode;
import org.jboss.msc.service.ServiceTarget;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.value.InjectedValue;
import org.jboss.osgi.framework.ServiceNames;
import org.osgi.framework.launch.Framework;

/**
 * A service that represents the INIT state of the {@link Framework}.
 *
 *  See {@link Framework#init()} for details.
 *
 * @author thomas.diesler@jboss.com
 * @since 04-Apr-2011
 */
public final class FrameworkInit extends FrameworkService {

    // Provide logging
    static final Logger log = Logger.getLogger(FrameworkInit.class);

    private final InjectedValue<FrameworkService> injectedFramework = new InjectedValue<FrameworkService>();

    static void addService(ServiceTarget serviceTarget) {
        FrameworkInit service = new FrameworkInit();
        ServiceBuilder<FrameworkService> builder = serviceTarget.addService(ServiceNames.FRAMEWORK_INIT, service);
        builder.addDependency(ServiceNames.FRAMEWORK_CREATE, FrameworkService.class, service.injectedFramework);
        builder.addDependency(InternalServices.CORE_SERVICES);
        builder.addDependency(InternalServices.PERSISTENT_BUNDLES_INSTALLER);
        builder.addDependency(InternalServices.PERSISTENT_BUNDLES_INSTALLER_COMPLETE);
        builder.setInitialMode(Mode.ON_DEMAND);
        builder.install();
    }

    private FrameworkInit() {
    }

    @Override
    public void start(StartContext context) throws StartException {
        super.start(context);
        log.debugf("OSGi Framework initilized");
    }

    @Override
    FrameworkState getFrameworkState() {
        return injectedFramework.getValue().getFrameworkState();
    }
}