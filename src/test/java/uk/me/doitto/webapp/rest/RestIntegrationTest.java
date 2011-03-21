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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.AssertTrue;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.me.doitto.webapp.dao.AbstractEntity;
import uk.me.doitto.webapp.dao.AbstractEntity_;
import uk.me.doitto.webapp.dao.SimpleEntity;
import uk.me.doitto.webapp.ws.AlbumRest;
import uk.me.doitto.webapp.ws.AppUserRest;
import uk.me.doitto.webapp.ws.ArtistRest;
import uk.me.doitto.webapp.ws.RestCrudBase;
import uk.me.doitto.webapp.ws.TrackRest;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

/**
 * @author ian
 *
 */
public class RestIntegrationTest {

    private WebClient webClient;
    
    private static final String HOST = "localhost";
    
    private static final String PORT = "9090";
    
    private static final String APP_URL = "http://" + HOST + ":" + PORT + "/jee6webapp";
    
    private static final String REST_URL = APP_URL + "/resources";
    
    private static final String CONTENT_TYPE = "Content-Type";

    private static final String ACCEPT = "Accept";

    private static final String ENCODING = "UTF-8";

    private static final String LOCATION = "Location";

    /**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
        webClient = new WebClient();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
        webClient.closeAllWindows();
	}

	private String getId (final String location) {
        String[] pieces = location.split("/");
        return pieces[pieces.length - 1];
	}
    
    @Test
    public void testAppUserRest () throws FailingHttpStatusCodeException, IOException {
    	WebRequest request;
    	WebResponse response;
    	String location;
    	String content;
        	
        	// get all, JSON & XML
        	request = new WebRequest(new URL(REST_URL + AppUserRest.PATH), HttpMethod.GET);
        	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
            response = webClient.getPage(request).getWebResponse();
            assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
            assertEquals("Wrong media type!", MediaType.APPLICATION_JSON, response.getResponseHeaderValue(CONTENT_TYPE));
            assertTrue("No content!", response.getContentAsString().length() > 0);

        	request = new WebRequest(new URL(REST_URL + AppUserRest.PATH), HttpMethod.GET);
        	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_XML);
            response = webClient.getPage(request).getWebResponse();
            assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
            assertEquals("Wrong media type!", MediaType.APPLICATION_XML, response.getResponseHeaderValue(CONTENT_TYPE));
            assertTrue("No content!", response.getContentAsString().length() > 0);

            // create
        	request = new WebRequest(new URL(REST_URL + AppUserRest.PATH), HttpMethod.POST);
        	request.setAdditionalHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON);
        	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
        	request.setRequestBody("{\"name\":\"testuser1\",\"password\":\"testpassword1\"}");
        	request.setCharset(ENCODING);
            response = webClient.getPage(request).getWebResponse();
            assertEquals("Incorrect response!", Response.Status.CREATED.getStatusCode(), response.getStatusCode());
            location = response.getResponseHeaderValue(LOCATION);
            assertTrue(location.matches(".*" + REST_URL + AppUserRest.PATH + ".*"));
            assertTrue("No content!", response.getContentAsString().length() > 0);
            
        	// get by ID, JSON
        	request = new WebRequest(new URL(location), HttpMethod.GET);
        	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
        	
            response = webClient.getPage(request).getWebResponse();
            content = response.getContentAsString();
            assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
            assertEquals("Wrong media type!", MediaType.APPLICATION_JSON, response.getResponseHeaderValue(CONTENT_TYPE));
            assertTrue("No content!", content.length() > 0);
            
            // modify
        	request = new WebRequest(new URL(location), HttpMethod.PUT);
        	request.setAdditionalHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON);
        	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
        	request.setRequestBody("{\"name\":\"testuser2\",\"password\":\"testpassword2\"}");
        	request.setCharset(ENCODING);
        	
            response = webClient.getPage(request).getWebResponse();
            content = response.getContentAsString();
            assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
            assertTrue("No content!", content.length() > 0);
            
        	// get by ID, XML
        	request = new WebRequest(new URL(location), HttpMethod.GET);
        	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_XML);
        	
            response = webClient.getPage(request).getWebResponse();
            content = response.getContentAsString();
            assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
            assertEquals("Wrong media type!", MediaType.APPLICATION_XML, response.getResponseHeaderValue(CONTENT_TYPE));
            assertTrue("No content!", content.length() > 0);
            
            //delete
            request = new WebRequest(new URL(location), HttpMethod.DELETE);
            response = webClient.getPage(request).getWebResponse();
            assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
            assertTrue("Should be no content!", response.getContentAsString().length() == 0);
            
        	// get all, JSON & XML
        	request = new WebRequest(new URL(REST_URL + AppUserRest.PATH), HttpMethod.GET);
        	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
            response = webClient.getPage(request).getWebResponse();
            assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
            assertEquals("Wrong media type!", MediaType.APPLICATION_JSON, response.getResponseHeaderValue(CONTENT_TYPE));
            assertTrue("No content!", response.getContentAsString().length() > 0);

        	request = new WebRequest(new URL(REST_URL + AppUserRest.PATH), HttpMethod.GET);
        	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_XML);
            response = webClient.getPage(request).getWebResponse();
            assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
            assertEquals("Wrong media type!", MediaType.APPLICATION_XML, response.getResponseHeaderValue(CONTENT_TYPE));
            assertTrue("No content!", response.getContentAsString().length() > 0);
    }
    
    @Test
    public void testArtistRest () throws FailingHttpStatusCodeException, IOException {
    	WebRequest request;
    	WebResponse response;
    	String location;
    	String content;
    	
    	// get all, JSON & XML
    	request = new WebRequest(new URL(REST_URL + ArtistRest.PATH), HttpMethod.GET);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
        response = webClient.getPage(request).getWebResponse();
        assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
        assertEquals("Wrong media type!", MediaType.APPLICATION_JSON, response.getResponseHeaderValue(CONTENT_TYPE));
        assertTrue("No content!", response.getContentAsString().length() > 0);

    	request = new WebRequest(new URL(REST_URL + ArtistRest.PATH), HttpMethod.GET);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_XML);
        response = webClient.getPage(request).getWebResponse();
        assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
        assertEquals("Wrong media type!", MediaType.APPLICATION_XML, response.getResponseHeaderValue(CONTENT_TYPE));
        assertTrue("No content!", response.getContentAsString().length() > 0);

        // create
    	request = new WebRequest(new URL(REST_URL + ArtistRest.PATH), HttpMethod.POST);
    	request.setAdditionalHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	request.setRequestBody("{\"name\":\"testartist1\"}");
    	request.setCharset(ENCODING);
        response = webClient.getPage(request).getWebResponse();
        assertEquals("Incorrect response!", Response.Status.CREATED.getStatusCode(), response.getStatusCode());
        location = response.getResponseHeaderValue(LOCATION);
        assertTrue(location.matches(".*" + REST_URL + ArtistRest.PATH + ".*"));
        assertTrue("No content!", response.getContentAsString().length() > 0);
        
    	// get by ID, JSON
    	request = new WebRequest(new URL(location), HttpMethod.GET);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	
        response = webClient.getPage(request).getWebResponse();
        content = response.getContentAsString();
        assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
        assertEquals("Wrong media type!", MediaType.APPLICATION_JSON, response.getResponseHeaderValue(CONTENT_TYPE));
        assertTrue("No content!", content.length() > 0);
        
        // modify
    	request = new WebRequest(new URL(location), HttpMethod.PUT);
    	request.setAdditionalHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	request.setRequestBody("{\"name\":\"testartist2\"}");
    	request.setCharset(ENCODING);
    	
        response = webClient.getPage(request).getWebResponse();
        content = response.getContentAsString();
        assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
        assertTrue("No content!", content.length() > 0);
        
    	// get by ID, XML
    	request = new WebRequest(new URL(location), HttpMethod.GET);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_XML);
    	
        response = webClient.getPage(request).getWebResponse();
        content = response.getContentAsString();
        assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
        assertEquals("Wrong media type!", MediaType.APPLICATION_XML, response.getResponseHeaderValue(CONTENT_TYPE));
        assertTrue("No content!", content.length() > 0);
        
        //delete
        request = new WebRequest(new URL(location), HttpMethod.DELETE);
        response = webClient.getPage(request).getWebResponse();
        assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
        assertTrue("Should be no content!", response.getContentAsString().length() == 0);
        
    	// get all, JSON & XML
    	request = new WebRequest(new URL(REST_URL + ArtistRest.PATH), HttpMethod.GET);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
        response = webClient.getPage(request).getWebResponse();
        assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
        assertEquals("Wrong media type!", MediaType.APPLICATION_JSON, response.getResponseHeaderValue(CONTENT_TYPE));
        assertTrue("No content!", response.getContentAsString().length() > 0);

    	request = new WebRequest(new URL(REST_URL + ArtistRest.PATH), HttpMethod.GET);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_XML);
        response = webClient.getPage(request).getWebResponse();
        assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
        assertEquals("Wrong media type!", MediaType.APPLICATION_XML, response.getResponseHeaderValue(CONTENT_TYPE));
        assertTrue("No content!", response.getContentAsString().length() > 0);
    }
    
    @Test
    public void testAlbumRest () throws FailingHttpStatusCodeException, IOException {
    	WebRequest request;
    	WebResponse response;
    	String location;
    	String content;
    	
    	// get all, JSON & XML
    	request = new WebRequest(new URL(REST_URL + AlbumRest.PATH), HttpMethod.GET);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
        response = webClient.getPage(request).getWebResponse();
        assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
        assertEquals("Wrong media type!", MediaType.APPLICATION_JSON, response.getResponseHeaderValue(CONTENT_TYPE));
        assertTrue("No content!", response.getContentAsString().length() > 0);

    	request = new WebRequest(new URL(REST_URL + AlbumRest.PATH), HttpMethod.GET);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_XML);
        response = webClient.getPage(request).getWebResponse();
        assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
        assertEquals("Wrong media type!", MediaType.APPLICATION_XML, response.getResponseHeaderValue(CONTENT_TYPE));
        assertTrue("No content!", response.getContentAsString().length() > 0);

        // create
    	request = new WebRequest(new URL(REST_URL + AlbumRest.PATH), HttpMethod.POST);
    	request.setAdditionalHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	request.setRequestBody("{\"name\":\"testalbum1\"}");
    	request.setCharset(ENCODING);
        response = webClient.getPage(request).getWebResponse();
        assertEquals("Incorrect response!", Response.Status.CREATED.getStatusCode(), response.getStatusCode());
        location = response.getResponseHeaderValue(LOCATION);
        assertTrue(location.matches(".*" + REST_URL + AlbumRest.PATH + ".*"));
        assertTrue("No content!", response.getContentAsString().length() > 0);
        
    	// get by ID, JSON
    	request = new WebRequest(new URL(location), HttpMethod.GET);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	
        response = webClient.getPage(request).getWebResponse();
        content = response.getContentAsString();
        assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
        assertEquals("Wrong media type!", MediaType.APPLICATION_JSON, response.getResponseHeaderValue(CONTENT_TYPE));
        assertTrue("No content!", content.length() > 0);
        
        // modify
    	request = new WebRequest(new URL(location), HttpMethod.PUT);
    	request.setAdditionalHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	request.setRequestBody("{\"name\":\"testalbum2\"}");
    	request.setCharset(ENCODING);
    	
        response = webClient.getPage(request).getWebResponse();
        content = response.getContentAsString();
        assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
        assertTrue("No content!", content.length() > 0);
        
    	// get by ID, XML
    	request = new WebRequest(new URL(location), HttpMethod.GET);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_XML);
    	
        response = webClient.getPage(request).getWebResponse();
        content = response.getContentAsString();
        assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
        assertEquals("Wrong media type!", MediaType.APPLICATION_XML, response.getResponseHeaderValue(CONTENT_TYPE));
        assertTrue("No content!", content.length() > 0);
        
        //delete
        request = new WebRequest(new URL(location), HttpMethod.DELETE);
        response = webClient.getPage(request).getWebResponse();
        assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
        assertTrue("Should be no content!", response.getContentAsString().length() == 0);
        
    	// get all, JSON & XML
    	request = new WebRequest(new URL(REST_URL + AlbumRest.PATH), HttpMethod.GET);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
        response = webClient.getPage(request).getWebResponse();
        assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
        assertEquals("Wrong media type!", MediaType.APPLICATION_JSON, response.getResponseHeaderValue(CONTENT_TYPE));
        assertTrue("No content!", response.getContentAsString().length() > 0);

    	request = new WebRequest(new URL(REST_URL + AlbumRest.PATH), HttpMethod.GET);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_XML);
        response = webClient.getPage(request).getWebResponse();
        assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
        assertEquals("Wrong media type!", MediaType.APPLICATION_XML, response.getResponseHeaderValue(CONTENT_TYPE));
        assertTrue("No content!", response.getContentAsString().length() > 0);
    }
    
    @Test
    public void testTrackRest () throws FailingHttpStatusCodeException, IOException {
    	WebRequest request;
    	WebResponse response;
    	String location;
    	String content;
    	
    	// get all, JSON & XML
    	request = new WebRequest(new URL(REST_URL + TrackRest.PATH), HttpMethod.GET);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	
        response = webClient.getPage(request).getWebResponse();
        assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
        assertEquals("Wrong media type!", MediaType.APPLICATION_JSON, response.getResponseHeaderValue(CONTENT_TYPE));
        assertTrue("No content!", response.getContentAsString().length() > 0);

    	request = new WebRequest(new URL(REST_URL + TrackRest.PATH), HttpMethod.GET);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_XML);
    	
        response = webClient.getPage(request).getWebResponse();
        assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
        assertEquals("Wrong media type!", MediaType.APPLICATION_XML, response.getResponseHeaderValue(CONTENT_TYPE));
        assertTrue("No content!", response.getContentAsString().length() > 0);

        // create
    	request = new WebRequest(new URL(REST_URL + TrackRest.PATH), HttpMethod.POST);
    	request.setAdditionalHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	request.setRequestBody("{\"name\":\"testtrack1\"}");
    	request.setCharset(ENCODING);
    	
        response = webClient.getPage(request).getWebResponse();
        location = response.getResponseHeaderValue(LOCATION);
        content = response.getContentAsString();
        assertEquals("Incorrect response!", Response.Status.CREATED.getStatusCode(), response.getStatusCode());
        assertTrue("Bad location!", location.matches(".*" + REST_URL + TrackRest.PATH + ".*"));
        assertTrue("No content!", content.length() > 0);
        
    	// get by ID, JSON
    	request = new WebRequest(new URL(location), HttpMethod.GET);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	
        response = webClient.getPage(request).getWebResponse();
        content = response.getContentAsString();
        assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
        assertEquals("Wrong media type!", MediaType.APPLICATION_JSON, response.getResponseHeaderValue(CONTENT_TYPE));
        assertTrue("No content!", content.length() > 0);
        
        // modify
    	request = new WebRequest(new URL(location), HttpMethod.PUT);
    	request.setAdditionalHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	request.setRequestBody("{\"name\":\"testtrack2\"}");
    	request.setCharset(ENCODING);
    	
        response = webClient.getPage(request).getWebResponse();
        content = response.getContentAsString();
        assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
        assertTrue("No content!", content.length() > 0);
        
    	// get by ID, XML
    	request = new WebRequest(new URL(location), HttpMethod.GET);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_XML);
    	
        response = webClient.getPage(request).getWebResponse();
        content = response.getContentAsString();
        assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
        assertEquals("Wrong media type!", MediaType.APPLICATION_XML, response.getResponseHeaderValue(CONTENT_TYPE));
        assertTrue("No content!", content.length() > 0);
        
        //delete
        request = new WebRequest(new URL(location), HttpMethod.DELETE);
        
        response = webClient.getPage(request).getWebResponse();
        assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
        assertTrue("Should be no content!", response.getContentAsString().length() == 0);
        
    	// get all, JSON & XML
    	request = new WebRequest(new URL(REST_URL + TrackRest.PATH), HttpMethod.GET);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	
        response = webClient.getPage(request).getWebResponse();
        content = response.getContentAsString();
        assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
        assertEquals("Wrong media type!", MediaType.APPLICATION_JSON, response.getResponseHeaderValue(CONTENT_TYPE));
        assertTrue("No content!", content.length() > 0);

    	request = new WebRequest(new URL(REST_URL + TrackRest.PATH), HttpMethod.GET);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_XML);
    	
        response = webClient.getPage(request).getWebResponse();
        content = response.getContentAsString();
        assertEquals("Incorrect response!", Response.Status.OK.getStatusCode(), response.getStatusCode());
        assertEquals("Wrong media type!", MediaType.APPLICATION_XML, response.getResponseHeaderValue(CONTENT_TYPE));
        assertTrue("No content!", content.length() > 0);
    }
    
    @Test
    public void testArtistAlbumLinking () throws FailingHttpStatusCodeException, IOException {
    	WebRequest request;
    	WebResponse response;
    	String content;
    	
        // create artist
    	request = new WebRequest(new URL(REST_URL + ArtistRest.PATH), HttpMethod.POST);
    	request.setAdditionalHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	request.setRequestBody("{\"name\":\"testartist2\"}");
    	request.setCharset(ENCODING);
        response = webClient.getPage(request).getWebResponse();
        String artistlocation = response.getResponseHeaderValue(LOCATION);
        String artistId = getId(artistlocation);
        System.out.println(response.getContentAsString());

        // create album
    	request = new WebRequest(new URL(REST_URL + AlbumRest.PATH), HttpMethod.POST);
    	request.setAdditionalHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	request.setRequestBody("{\"name\":\"testalbum3\"}");
    	request.setCharset(ENCODING);    	
        response = webClient.getPage(request).getWebResponse();
        String album1location = response.getResponseHeaderValue(LOCATION);
        String album1Id = getId(album1location);
        System.out.println(response.getContentAsString());
        
        // link them
    	request = new WebRequest(new URL(REST_URL + ArtistRest.PATH + "/" + ArtistRest.LINK_ALBUM), HttpMethod.GET);
    	List<NameValuePair> parameters1 = new ArrayList<NameValuePair>();
    	parameters1.add(new NameValuePair(ArtistRest.QP_ARTISTID, artistId));
    	parameters1.add(new NameValuePair(AlbumRest.QP_ALBUMID, album1Id));
    	request.setRequestParameters(parameters1);
    	request.setCharset(ENCODING);
        response = webClient.getPage(request).getWebResponse();
        
    	// get artist by ID, JSON
    	request = new WebRequest(new URL(artistlocation), HttpMethod.GET);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	request.setCharset(ENCODING);
        response = webClient.getPage(request).getWebResponse();
        content = response.getContentAsString();
        System.out.println(content);

        // create another album
    	request = new WebRequest(new URL(REST_URL + AlbumRest.PATH), HttpMethod.POST);
    	request.setAdditionalHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	request.setRequestBody("{\"name\":\"testalbum4\"}");
    	request.setCharset(ENCODING);    	
        response = webClient.getPage(request).getWebResponse();
        String album2location = response.getResponseHeaderValue(LOCATION);
        String album2Id = getId(album2location);
        System.out.println(response.getContentAsString());
        
        // link them
    	request = new WebRequest(new URL(REST_URL + ArtistRest.PATH + "/" + ArtistRest.LINK_ALBUM), HttpMethod.GET);
    	List<NameValuePair> parameters2 = new ArrayList<NameValuePair>();
    	parameters2.add(new NameValuePair(ArtistRest.QP_ARTISTID, artistId));
    	parameters2.add(new NameValuePair(AlbumRest.QP_ALBUMID, album2Id));
    	request.setRequestParameters(parameters2);
    	request.setCharset(ENCODING);
        response = webClient.getPage(request).getWebResponse();
        
    	// get artist by ID, JSON
    	request = new WebRequest(new URL(artistlocation), HttpMethod.GET);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	request.setCharset(ENCODING);
        response = webClient.getPage(request).getWebResponse();
        content = response.getContentAsString();
        System.out.println(content);
    }
    
    @Test
    public void testAlbumTrackLinking () throws FailingHttpStatusCodeException, IOException {
    	WebRequest request;
    	WebResponse response;
    	String content;
    	
        // create album
    	request = new WebRequest(new URL(REST_URL + AlbumRest.PATH), HttpMethod.POST);
    	request.setAdditionalHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	request.setRequestBody("{\"name\":\"testalbum2\"}");
    	request.setCharset(ENCODING);
        response = webClient.getPage(request).getWebResponse();
        String albumlocation = response.getResponseHeaderValue(LOCATION);
        String albumId = getId(albumlocation);
        System.out.println(response.getContentAsString());

        // create track
    	request = new WebRequest(new URL(REST_URL + TrackRest.PATH), HttpMethod.POST);
    	request.setAdditionalHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	request.setRequestBody("{\"name\":\"testtrack2\"}");
    	request.setCharset(ENCODING);    	
        response = webClient.getPage(request).getWebResponse();
        String track1location = response.getResponseHeaderValue(LOCATION);
        String track1Id = getId(track1location);
        System.out.println(response.getContentAsString());
        
        // link them
    	request = new WebRequest(new URL(REST_URL + AlbumRest.PATH + "/" + AlbumRest.LINK_TRACK), HttpMethod.GET);
    	List<NameValuePair> parameters1 = new ArrayList<NameValuePair>();
    	parameters1.add(new NameValuePair(AlbumRest.QP_ALBUMID, albumId));
    	parameters1.add(new NameValuePair(TrackRest.QP_TRACKID, track1Id));
    	request.setRequestParameters(parameters1);
    	request.setCharset(ENCODING);
        response = webClient.getPage(request).getWebResponse();
        
        // create another track
    	request = new WebRequest(new URL(REST_URL + TrackRest.PATH), HttpMethod.POST);
    	request.setAdditionalHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	request.setRequestBody("{\"name\":\"testtrack3\"}");
    	request.setCharset(ENCODING);    	
        response = webClient.getPage(request).getWebResponse();
        String track2location = response.getResponseHeaderValue(LOCATION);
        String track2Id = getId(track2location);
        System.out.println(response.getContentAsString());
        
        // link them
    	request = new WebRequest(new URL(REST_URL + AlbumRest.PATH + "/" + AlbumRest.LINK_TRACK), HttpMethod.GET);
    	List<NameValuePair> parameters2 = new ArrayList<NameValuePair>();
    	parameters2.add(new NameValuePair(AlbumRest.QP_ALBUMID, albumId));
    	parameters2.add(new NameValuePair(TrackRest.QP_TRACKID, track2Id));
    	request.setRequestParameters(parameters2);
    	request.setCharset(ENCODING);
        response = webClient.getPage(request).getWebResponse();
        
    	// get album by ID, JSON
    	request = new WebRequest(new URL(albumlocation), HttpMethod.GET);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	request.setCharset(ENCODING);
        response = webClient.getPage(request).getWebResponse();
        content = response.getContentAsString();
        System.out.println(content);
        
    	// get tracks by ID, JSON
    	request = new WebRequest(new URL(track1location), HttpMethod.GET);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	request.setCharset(ENCODING);
        response = webClient.getPage(request).getWebResponse();
        content = response.getContentAsString();
        System.out.println(content);

    	request = new WebRequest(new URL(track2location), HttpMethod.GET);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	request.setCharset(ENCODING);
        response = webClient.getPage(request).getWebResponse();
        content = response.getContentAsString();
        System.out.println(content);
}
    
    @Test
    public void testDates () throws InterruptedException, FailingHttpStatusCodeException, IOException {
    	WebRequest request;
    	List<NameValuePair> parameters;
    	
    	System.out.println("Users");
        // create a user
    	request = new WebRequest(new URL(REST_URL + AppUserRest.PATH), HttpMethod.POST);
    	request.setAdditionalHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	request.setRequestBody("{\"name\":\"userA\",\"password\":\"passwordA\"}");
    	request.setCharset(ENCODING);
    	System.out.println(webClient.getPage(request).getWebResponse().getContentAsString());
        
        Thread.sleep(100);
        String start = String.valueOf(new Date().getTime());
        Thread.sleep(100);
        
        // create a user
    	request = new WebRequest(new URL(REST_URL + AppUserRest.PATH), HttpMethod.POST);
    	request.setAdditionalHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	request.setRequestBody("{\"name\":\"userB\",\"password\":\"passwordB\"}");
    	request.setCharset(ENCODING);
    	System.out.println(webClient.getPage(request).getWebResponse().getContentAsString());
        
        Thread.sleep(100);        
        String end = String.valueOf(new Date().getTime());
        Thread.sleep(100);
        
        // create a user
    	request = new WebRequest(new URL(REST_URL + AppUserRest.PATH), HttpMethod.POST);
    	request.setAdditionalHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	request.setRequestBody("{\"name\":\"userC\",\"password\":\"passwordC\"}");
    	request.setCharset(ENCODING);
    	System.out.println(webClient.getPage(request).getWebResponse().getContentAsString());
    	System.out.println("");
        
        // Date
    	request = new WebRequest(new URL(REST_URL + AppUserRest.PATH + "/" + RestCrudBase.BEFORE), HttpMethod.GET);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	parameters = new ArrayList<NameValuePair>();
    	parameters.add(new NameValuePair(RestCrudBase.ATTRIBUTE, AbstractEntity.TimeStamp.created.toString()));
    	parameters.add(new NameValuePair(RestCrudBase.DATE, start));
    	request.setRequestParameters(parameters);
    	request.setCharset(ENCODING);
    	System.out.println("Before date 1");
    	System.out.println(webClient.getPage(request).getWebResponse().getContentAsString());
    	System.out.println("");
    	
    	request = new WebRequest(new URL(REST_URL + AppUserRest.PATH + "/" + RestCrudBase.SINCE), HttpMethod.GET);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	parameters = new ArrayList<NameValuePair>();
    	parameters.add(new NameValuePair(RestCrudBase.ATTRIBUTE, AbstractEntity.TimeStamp.modified.toString()));
    	parameters.add(new NameValuePair(RestCrudBase.DATE, start));
    	request.setRequestParameters(parameters);
    	request.setCharset(ENCODING);
    	System.out.println("Since date 1");
    	System.out.println(webClient.getPage(request).getWebResponse().getContentAsString());
    	System.out.println("");
    	
        // Date
    	request = new WebRequest(new URL(REST_URL + AppUserRest.PATH + "/" + RestCrudBase.BEFORE), HttpMethod.GET);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	parameters = new ArrayList<NameValuePair>();
    	parameters.add(new NameValuePair(RestCrudBase.ATTRIBUTE, AbstractEntity.TimeStamp.created.toString()));
    	parameters.add(new NameValuePair(RestCrudBase.DATE, end));
    	request.setRequestParameters(parameters);
    	request.setCharset(ENCODING);
    	System.out.println("Before date 2");
    	System.out.println(webClient.getPage(request).getWebResponse().getContentAsString());
    	System.out.println("");
    	
    	request = new WebRequest(new URL(REST_URL + AppUserRest.PATH + "/" + RestCrudBase.SINCE), HttpMethod.GET);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	parameters = new ArrayList<NameValuePair>();
    	parameters.add(new NameValuePair(RestCrudBase.ATTRIBUTE, AbstractEntity.TimeStamp.modified.toString()));
    	parameters.add(new NameValuePair(RestCrudBase.DATE, end));
    	request.setRequestParameters(parameters);
    	request.setCharset(ENCODING);
    	System.out.println("Since date 2");
    	System.out.println(webClient.getPage(request).getWebResponse().getContentAsString());
    	System.out.println("");
    	
    	// Two dates
    	request = new WebRequest(new URL(REST_URL + AppUserRest.PATH + "/" + RestCrudBase.DURING), HttpMethod.GET);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	parameters = new ArrayList<NameValuePair>();
    	parameters.add(new NameValuePair(RestCrudBase.ATTRIBUTE, AbstractEntity.TimeStamp.created.toString()));
    	parameters.add(new NameValuePair(RestCrudBase.START, start));
    	parameters.add(new NameValuePair(RestCrudBase.END, end));
    	request.setRequestParameters(parameters);
    	request.setCharset(ENCODING);
    	System.out.println("During interval");
    	System.out.println(webClient.getPage(request).getWebResponse().getContentAsString());
    	System.out.println("");
    	
    	request = new WebRequest(new URL(REST_URL + AppUserRest.PATH + "/" + RestCrudBase.NOT_DURING), HttpMethod.GET);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	parameters = new ArrayList<NameValuePair>();
    	parameters.add(new NameValuePair(RestCrudBase.ATTRIBUTE, AbstractEntity.TimeStamp.modified.toString()));
    	parameters.add(new NameValuePair(RestCrudBase.START, start));
    	parameters.add(new NameValuePair(RestCrudBase.END, end));
    	request.setRequestParameters(parameters);
    	request.setCharset(ENCODING);
    	System.out.println("Outside interval");
    	System.out.println(webClient.getPage(request).getWebResponse().getContentAsString());
    	System.out.println("");
    	
    	assertTrue(true);
    	
//        assertTrue("Wrong contents! ", dao.before(AbstractEntity_.created, date1).contains(entityA));
//        assertFalse("Wrong contents! ", dao.before(AbstractEntity_.accessed, date1).contains(entityB) || dao.before(AbstractEntity_.modified, date1).contains(entityC));
//        assertTrue("Wrong contents! ", dao.since(AbstractEntity_.created, date1).contains(entityB) && dao.since(AbstractEntity_.modified, date1).contains(entityC));
//        assertFalse("Wrong contents! ", dao.since(AbstractEntity_.accessed, date1).contains(entityA));
//        // Date 2
//        assertTrue("Wrong contents! ", dao.before(AbstractEntity_.created, date2).contains(entityA) && dao.before(AbstractEntity_.modified, date2).contains(entityB));
//        assertFalse("Wrong contents! ", dao.before(AbstractEntity_.accessed, date2).contains(entityC));
//        assertTrue("Wrong contents! ", dao.since(AbstractEntity_.created, date2).contains(entityC));
//        assertFalse("Wrong contents! ", dao.since(AbstractEntity_.accessed, date2).contains(entityA) || dao.since(AbstractEntity_.modified, date2).contains(entityB));
//        // Both dates
//        assertTrue("Wrong contents! ", dao.during(AbstractEntity_.created, date1, date2).contains(entityB));
//        assertFalse("Wrong contents! ", dao.during(AbstractEntity_.accessed, date1, date2).contains(entityA) || dao.during(AbstractEntity_.modified, date1, date2).contains(entityC));
//        assertTrue("Wrong contents! ", dao.notDuring(AbstractEntity_.created, date1, date2).contains(entityA) && dao.notDuring(AbstractEntity_.modified, date1, date2).contains(entityC));
//        assertFalse("Wrong contents! ", dao.notDuring(AbstractEntity_.accessed, date1, date2).contains(entityB));
    }
}
