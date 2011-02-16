/**
 * Copyright (C) 2010 Ian C. Smith <m4r35n357@gmail.com>
 *
 * This file is part of JavaEE6Webapp.
 *
 *     JavaEE6Webapp is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     JavaEE6Webapp is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with JavaEE6Webapp.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * 
 */
package uk.me.doitto.webapp.rest;

import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import uk.me.doitto.webapp.entity.Artist;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.header.MediaTypes;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.LowLevelAppDescriptor.Builder;
import com.sun.jersey.test.framework.spi.container.TestContainerFactory;
import com.sun.jersey.test.framework.spi.container.inmemory.InMemoryTestContainerFactory;

/**
 * @author super
 *
 */
public class RestTest extends JerseyTest {
	
    public RestTest () throws Exception {
        super(new Builder("uk.me.doitto.webapp.ws").build());
    }
    
    @Override
    protected TestContainerFactory getTestContainerFactory() {
        return new InMemoryTestContainerFactory();
    }
    
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//	}
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//	}
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@Before
//	public void setUp() throws Exception {
//		// TODO this breaks the Jersey Test Framework!
//	}
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@After
//	public void tearDown() throws Exception {
//	}

    @Test
    public void testDeployed() {
        WebResource webResource = resource();
        webResource = webResource.path("helloworld");
        String s = webResource.get(String.class);
        assertFalse(s.length() == 0);
    }

    /**
     * Test that the expected response is sent back.
     * @throws java.lang.Exception
     */
    @Test
    public void testHelloWorld () throws Exception {
        WebResource webResource = resource();
//        webResource = webResource.path("resources");
        webResource = webResource.path("helloworld");
        String responseMsg = webResource.get(String.class);
        Assert.assertEquals("Hello World!", responseMsg);        
    }

    @Test
    public void testHelloWorldDate () throws Exception {
        WebResource webResource = resource();
        webResource = webResource.path("helloworld");
        webResource = webResource.path("date");
        String responseMsg = webResource.get(String.class);
        Assert.assertTrue(responseMsg.contains("Current time is"));        
    }
    @Test
    public void testApplicationWadl() {
        WebResource webResource = resource();
        String serviceWadl = webResource.path("application.wadl").accept(MediaTypes.WADL).get(String.class);
        Assert.assertTrue(serviceWadl.length() > 0);
    }
    
    @Ignore
    @Test
    public void testArtistRestCrud () {
        WebResource webResource = resource();
        webResource = webResource.path("artist");
        List<Artist> responseMsg = webResource.get(List.class);
//        assertTrue(responseMsg.contains("Current time is"));
        
//        Artist t = new Artist();
//        t.setName(STRING_1);
//
//    	artistRest.create(new JAXBElement<Artist>(new QName("planet"), Artist.class, t));  	
    }
}
