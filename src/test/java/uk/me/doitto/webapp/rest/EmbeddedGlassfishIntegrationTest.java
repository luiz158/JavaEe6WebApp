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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.me.doitto.webapp.ws.AlbumRest;
import uk.me.doitto.webapp.ws.AppUserRest;
import uk.me.doitto.webapp.ws.ArtistRest;
import uk.me.doitto.webapp.ws.TrackRest;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

/**
 * @author ian
 *
 */
public class EmbeddedGlassfishIntegrationTest {

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

    
//    @Ignore
    @Test
    public void testIndex () {
    	String url = APP_URL + "/index.xhtml";
        try {
//            HtmlPage page = webClient.getPage(url);
//            assertEquals("HtmlUnit - Welcome to HtmlUnit", page.getTitleText());
//
//            final String pageAsXml = page.asXml();
//            assertTrue(pageAsXml.contains("<body class=\"composite\">"));
//
//            final String pageAsText = page.asText();
//            assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));
            url = APP_URL + "/next.xhtml";
            webClient.getPage(url);
            url = APP_URL + "/pages/another.xhtml";
            webClient.getPage(url);
        } catch (Exception e) {
            fail("Unexpected exception in test. Is Glassfish Running at " + url + " ? ->" + e);
        }
    }
    
    @Test
    public void testAppUserController () {
    	String url = APP_URL + "/pages/listUsers.xhtml";
        try {
            HtmlPage page = webClient.getPage(url);
            String pageAsText = page.asText();
            assertTrue("String not found!", pageAsText.contains("LIST"));
        } catch (Exception e) {
            fail("Unexpected exception in test. Is Glassfish Running at " + url + " ? ->" + e);
        }
    	url = APP_URL + "/pages/editUser.xhtml";
        try {
            HtmlPage page = webClient.getPage(url);
            String pageAsText = page.asText();
            assertTrue("String not found!", pageAsText.contains("EDIT"));
        } catch (Exception e) {
            fail("Unexpected exception in test. Is Glassfish Running at " + url + " ? ->" + e);
        }
    }
    
    @Test
    public void testArtistController () {
    	String url = APP_URL + "/pages/listArtists.xhtml";
        try {
            HtmlPage page = webClient.getPage(url);
            String pageAsText = page.asText();
            assertTrue("String not found!", pageAsText.contains("LIST"));
        } catch (Exception e) {
            fail("Unexpected exception in test. Is Glassfish Running at " + url + " ? ->" + e);
        }
    	url = APP_URL + "/pages/editArtist.xhtml";
        try {
            HtmlPage page = webClient.getPage(url);
            String pageAsText = page.asText();
            assertTrue("String not found!", pageAsText.contains("EDIT"));
        } catch (Exception e) {
            fail("Unexpected exception in test. Is Glassfish Running at " + url + " ? ->" + e);
        }
    }
    
    @Test
    public void testAlbumController () {
    	String url = APP_URL + "/pages/listAlbums.xhtml";
        try {
            HtmlPage page = webClient.getPage(url);
            String pageAsText = page.asText();
            assertTrue("String not found!", pageAsText.contains("LIST"));
        } catch (Exception e) {
            fail("Unexpected exception in test. Is Glassfish Running at " + url + " ? ->" + e);
        }
    	url = APP_URL + "/pages/editAlbum.xhtml";
        try {
            HtmlPage page = webClient.getPage(url);
            String pageAsText = page.asText();
            assertTrue("String not found!", pageAsText.contains("EDIT"));
        } catch (Exception e) {
            fail("Unexpected exception in test. Is Glassfish Running at " + url + " ? ->" + e);
        }
    }
    
    @Test
    public void testTrackController () {
    	String url = APP_URL + "/pages/listTracks.xhtml";
        try {
            HtmlPage page = webClient.getPage(url);
            String pageAsText = page.asText();
            assertTrue("String not found!", pageAsText.contains("LIST"));
        } catch (Exception e) {
            fail("Unexpected exception in test. Is Glassfish Running at " + url + " ? ->" + e);
        }
    	url = APP_URL + "/pages/editTrack.xhtml";
        try {
            HtmlPage page = webClient.getPage(url);
            String pageAsText = page.asText();
            assertTrue("String not found!", pageAsText.contains("EDIT"));
        } catch (Exception e) {
            fail("Unexpected exception in test. Is Glassfish Running at " + url + " ? ->" + e);
        }
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
    public void testAlbumTrackLinking () throws FailingHttpStatusCodeException, IOException {
    	WebRequest request;
    	WebResponse response;
    	String content;
    	
        // create Album
    	request = new WebRequest(new URL(REST_URL + AlbumRest.PATH), HttpMethod.POST);
    	request.setAdditionalHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	request.setRequestBody("{\"name\":\"testalbum2\"}");
    	request.setCharset(ENCODING);
        response = webClient.getPage(request).getWebResponse();
        String albumlocation = response.getResponseHeaderValue(LOCATION);
        String[] albumArray = albumlocation.split("/");
        String albumId = albumArray[albumArray.length - 1];

        // create Track
    	request = new WebRequest(new URL(REST_URL + TrackRest.PATH), HttpMethod.POST);
    	request.setAdditionalHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	request.setRequestBody("{\"name\":\"testtrack2\"}");
    	request.setCharset(ENCODING);    	
        response = webClient.getPage(request).getWebResponse();
        String tracklocation = response.getResponseHeaderValue(LOCATION);
        String[] trackArray = tracklocation.split("/");
        String trackId = trackArray[trackArray.length - 1];
        
        // link them
    	request = new WebRequest(new URL(REST_URL + AlbumRest.PATH + "/" + AlbumRest.LINK_TRACK), HttpMethod.GET);
    	List<NameValuePair> parameters = new ArrayList<NameValuePair>();
    	parameters.add(new NameValuePair(AlbumRest.QP_ALBUMID, albumId));
    	parameters.add(new NameValuePair(AlbumRest.QP_TRACKID, trackId));
    	request.setRequestParameters(parameters);
    	request.setCharset(ENCODING);
        response = webClient.getPage(request).getWebResponse();
        
    	// get album by ID, JSON
    	request = new WebRequest(new URL(albumlocation), HttpMethod.GET);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	request.setCharset(ENCODING);
        response = webClient.getPage(request).getWebResponse();
        content = response.getContentAsString();
        System.out.println(content);
        
    	// get track by ID, JSON
    	request = new WebRequest(new URL(tracklocation), HttpMethod.GET);
    	request.setAdditionalHeader(ACCEPT, MediaType.APPLICATION_JSON);
    	request.setCharset(ENCODING);
        response = webClient.getPage(request).getWebResponse();
        content = response.getContentAsString();
        System.out.println(content);

        assertTrue("", true);
    }
}
