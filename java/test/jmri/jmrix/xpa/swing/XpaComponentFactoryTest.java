package jmri.jmrix.xpa.swing;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Paul Bender Copyright (C) 2017	
 */
public class XpaComponentFactoryTest {

    @Test
    public void testCTor() {
        jmri.jmrix.xpa.XpaSystemConnectionMemo memo = new jmri.jmrix.xpa.XpaSystemConnectionMemo();
        XpaComponentFactory t = new XpaComponentFactory(memo);
        Assert.assertNotNull("exists",t);
    }

    // The minimal setup for log4J
    @Before
    public void setUp() {
        apps.tests.Log4JFixture.setUp();
        jmri.util.JUnitUtil.resetInstanceManager();
    }

    @After
    public void tearDown() {
        jmri.util.JUnitUtil.resetInstanceManager();
        apps.tests.Log4JFixture.tearDown();
    }

    private final static Logger log = LoggerFactory.getLogger(XpaComponentFactoryTest.class.getName());

}
