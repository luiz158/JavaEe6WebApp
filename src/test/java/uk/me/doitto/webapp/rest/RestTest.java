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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import uk.me.doitto.webapp.entity.Artist;
import uk.me.doitto.webapp.ws.ArtistRest;

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
    
    // This won't work as service EJBs aren't injected
    @Ignore
    @Test
    public void testArtistRestCrud () {
        WebResource webResource = resource();
        webResource = webResource.path(ArtistRest.PATH);
        Artist artist = new Artist();
        artist.setName("test artist");
        Response response = webResource.post(Response.class, artist);
        
        @SuppressWarnings("unchecked")
		List<Artist> responseMsg = webResource.get(List.class);
        assertTrue(responseMsg.contains("Current time is"));
        
//        Artist t = new Artist();
//        t.setName(STRING_1);
//
//    	artistRest.create(new JAXBElement<Artist>(new QName("planet"), Artist.class, t));  	
    }
//    public void testArtistServiceCrud () {
//        Artist t = new Artist();
//        t.setName(STRING_1);
//        assertTrue("", t.isNew());
//
//        artistService.create(t);
//        assertFalse("", t.isNew());
//
//        Artist s = artistService.find(t.getId());
//        assertEquals(STRING_1, s.getName());
//
//        s.setName(STRING_2);
//        artistService.update(s);
//        s = artistService.find(t.getId());
//        assertEquals(STRING_2, s.getName());
//
//        List<Artist> list1 = artistService.findAll();
//        assertEquals(true, list1.contains(s));
//
////        List<Artist> list2 = artistService.findByNamedQuery(Artist.FIND_ALL, null, 0, 0);
////        assertEquals(true, list2.contains(s));
//
//        artistService.delete(s.getId());
//        List<Artist> list3 = artistService.findAll();
//        assertEquals(false, list3.contains(s));
//     }
}
