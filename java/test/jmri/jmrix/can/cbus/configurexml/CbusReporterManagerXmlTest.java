package jmri.jmrix.can.cbus.configurexml;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * CbusReporterManagerXmlTest.java
 *
 * Description: tests for the CbusReporterManagerXml class
 *
 * @author   Paul Bender  Copyright (C) 2016
 */
public class CbusReporterManagerXmlTest {

    @Test
    public void testCtor(){
      Assert.assertNotNull("CbusReporterManagerXml constructor",new CbusReporterManagerXml());
    }

    // The minimal setup for log4J
    @Before
    public void setUp() {
        apps.tests.Log4JFixture.setUp();
    }

    @After
    public void tearDown() {
        apps.tests.Log4JFixture.tearDown();
    }

}

