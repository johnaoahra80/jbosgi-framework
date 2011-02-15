/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package org.jboss.test.osgi.framework.bundle;


import java.lang.reflect.Method;
import java.util.Properties;

import org.jboss.osgi.framework.bundle.FrameworkState;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Version;

/**
 * Unit tests for the FrameworkState class.
 *
 * @author David Bosschaert
 */
public class FrameworkStateTestCase {
    Properties savedProps;

    @Before
    public void setUp() {
        savedProps = new Properties();
        savedProps.putAll(System.getProperties());
    }

    @After
    public void tearDown() {
        System.setProperties(savedProps);
    }

    @Test
    public void testOSVersionIsValidOSGiVersion() throws Exception {
        Version.parseVersion(callGetOSVersionInOSGiFormat());
    }

    @Test
    public void testVersion1() throws Exception {
        System.setProperty("os.version", "1");
        Version v = Version.parseVersion(callGetOSVersionInOSGiFormat());
        Assert.assertEquals("1.0.0", v.toString());
    }

    @Test
    public void testVersion2() throws Exception {
        System.setProperty("os.version", "1.2.3.beta-4");
        Version v = Version.parseVersion(callGetOSVersionInOSGiFormat());
        Assert.assertEquals("1.2.3.beta-4", v.toString());
    }

    @Test
    public void testVersion3() throws Exception {
        System.setProperty("os.version", "1.2.3.4.5.beta6");
        Version v = Version.parseVersion(callGetOSVersionInOSGiFormat());
        Assert.assertEquals("1.2.3.4", v.toString());
    }

    @Test
    public void testVersion4() throws Exception {
        System.setProperty("os.version", "1.aaa.3.4.5.beta6");
        Version v = Version.parseVersion(callGetOSVersionInOSGiFormat());
        Assert.assertEquals("1.0.0", v.toString());
    }

    @Test
    public void testVersion5() throws Exception {
        System.setProperty("os.version", "burp");
        Version v = Version.parseVersion(callGetOSVersionInOSGiFormat());
        Assert.assertEquals("0.0.0", v.toString());
    }

    @Test
    public void testVersion6() throws Exception {
        System.setProperty("os.version", "");
        Version v = Version.parseVersion(callGetOSVersionInOSGiFormat());
        Assert.assertEquals("0.0.0", v.toString());
    }

    private String callGetOSVersionInOSGiFormat() throws Exception {
        Method method = FrameworkState.class.getDeclaredMethod("getOSVersionInOSGiFormat");
        method.setAccessible(true);
        return (String) method.invoke(null);
    }
}
