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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.me.doitto.webapp.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import uk.me.doitto.webapp.entity.Album;
import uk.me.doitto.webapp.entity.AppUser;
import uk.me.doitto.webapp.entity.Artist;
import uk.me.doitto.webapp.entity.Track;
import uk.me.doitto.webapp.model.AlbumController;
import uk.me.doitto.webapp.model.AppUserController;
import uk.me.doitto.webapp.model.ArtistController;
import uk.me.doitto.webapp.model.TrackController;
import uk.me.doitto.webapp.ws.AlbumRest;
import uk.me.doitto.webapp.ws.AppUserRest;
import uk.me.doitto.webapp.ws.ArtistRest;
import uk.me.doitto.webapp.ws.TrackRest;

/**
 *
 * @author ian
 */
public class EjbTest {

    private static AppUserService userService;

    private static AppUserController userController;

    private static ArtistService artistService;

    private static ArtistController artistController;

    private static AlbumService albumService;

    private static AlbumController albumController;

    private static TrackService trackService;

    private static TrackController trackController;

    private static TrackRest trackRest;

    private static EJBContainer ec;

    private static Context ctx;

    private static String STRING_1 = "Hello World!";
    
    private static String STRING_2 = "Goodbye World!";
    
    @BeforeClass
    public static void setUpClass () throws Exception {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(EJBContainer.MODULES, new File("target/classes"));
        properties.put("org.glassfish.ejb.embedded.glassfish.installation.root", "glassfish");
        ec = EJBContainer.createEJBContainer(properties);
        ctx = ec.getContext();
        
        userService = (AppUserService)ctx.lookup("java:global/classes/AppUserService");
        userController = (AppUserController)ctx.lookup("java:global/classes/AppUserController");
        
        artistService = (ArtistService)ctx.lookup("java:global/classes/ArtistService");
        artistController = (ArtistController)ctx.lookup("java:global/classes/ArtistController");
        
        albumService = (AlbumService)ctx.lookup("java:global/classes/AlbumService");
        albumController = (AlbumController)ctx.lookup("java:global/classes/AlbumController");
       
        trackService = (TrackService)ctx.lookup("java:global/classes/TrackService");
        trackController = (TrackController)ctx.lookup("java:global/classes/TrackController");
    	trackRest = (TrackRest)ctx.lookup("java:global/classes/TrackRest");   	
    }

    @AfterClass
    public static void tearDownClass () throws Exception {
        if (ec != null) {
            ec.close();
        }
    }

    @Before
    public void setUp() {
	// TODO this breaks the Jersey Test Framework!
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAppUserServiceCrud () {
    	AppUser t = new AppUser();
        t.setName(STRING_1);
        assertTrue("", t.isNew());

        userService.create(t);
        assertFalse("", t.isNew());

        AppUser s = userService.find(t.getId());
        assertEquals("", STRING_1, s.getName());
        assertEquals("", s, t);

        s.setName(STRING_2);
        userService.update(s);
        s = userService.find(t.getId());
        assertEquals(STRING_2, s.getName());

        List<AppUser> list1 = userService.findAll();
        assertEquals(true, list1.contains(s));

//        List<AppUser> list2 = artistService.findByNamedQuery(Artist.FIND_ALL, null, 0, 0);
//        assertEquals(true, list2.contains(s));

        userService.remove(s.getId());
        List<AppUser> list3 = userService.findAll();
        assertEquals(false, list3.contains(s));
     }

    @Test
    public void testAppUserControllerCrud () {
    	String editPage = "/pages/editUser.xhtml?faces-redirect=true";
    	String listPage = "/pages/listUsers.xhtml?faces-redirect=true";
    	
        assertEquals(editPage, userController.jsfCreate());
        assertTrue("Should be new (NULL ID)!", userController.getItem().isNew());
        
        userController.getItem().setName(STRING_1);
        assertEquals(listPage, userController.jsfSave());
        assertFalse("Should NOT be new!", userController.getItem().isNew());

        assertEquals(editPage, userController.jsfEdit(userController.getItem().getId()));
        assertEquals(STRING_1, userController.getItem().getName());

        userController.getItem().setName(STRING_2);
        assertEquals(listPage, userController.jsfSave());
        
        assertEquals(editPage, userController.jsfEdit(userController.getItem().getId()));
        assertEquals(STRING_2, userController.getItem().getName());

        assertEquals(listPage, userController.jsfList());
        assertEquals(true, userController.getItems().contains(userController.getItem()));

        assertEquals(listPage, userController.jsfDelete(userController.getItem().getId()));
        
        assertEquals(listPage, userController.jsfList());
        assertEquals(false, userController.getItems().contains(userController.getItem()));
    }

    @Test
    public void testAppUserRest () {
        try {
        	ctx.lookup("java:global/classes/AppUserRest");
        	assertTrue(true);
		} catch (NamingException e) {
			e.printStackTrace();
			fail("Should not reach here!");
		}
     }

    @Test
    public void testArtistServiceCrud () {
        Artist t = new Artist();
        t.setName(STRING_1);
        assertTrue("", t.isNew());

        artistService.create(t);
        assertFalse("", t.isNew());

        Artist s = artistService.find(t.getId());
        assertEquals(STRING_1, s.getName());

        s.setName(STRING_2);
        artistService.update(s);
        s = artistService.find(t.getId());
        assertEquals(STRING_2, s.getName());

        List<Artist> list1 = artistService.findAll();
        assertEquals(true, list1.contains(s));

//        List<Artist> list2 = artistService.findByNamedQuery(Artist.FIND_ALL, null, 0, 0);
//        assertEquals(true, list2.contains(s));

        artistService.remove(s.getId());
        List<Artist> list3 = artistService.findAll();
        assertEquals(false, list3.contains(s));
     }
    
    @Test
    public void testArtistServiceLinking () {
        Artist artist = new Artist();
        artist.setName("LinkedArtist");
        artistService.create(artist);

        Album album = new Album();
        album.setName("LinkedAlbum");
        albumService.create(album);
        
        assertFalse("", artistService.find(artist.getId()).getAlbums().contains(albumService.find(album.getId())));        
        artistService.linkAlbum(artist.getId(), album.getId());        
        assertTrue("", artistService.find(artist.getId()).getAlbums().contains(albumService.find(album.getId())));        
        artistService.unlinkAlbum(artist.getId(), album.getId());        
        assertFalse("", artistService.find(artist.getId()).getAlbums().contains(albumService.find(album.getId())));        
    }

    @Test
    public void testArtistControllerCrud () {
    	String editPage = "/pages/editArtist.xhtml?faces-redirect=true";
    	String listPage = "/pages/listArtists.xhtml?faces-redirect=true";
    	
        assertEquals(editPage, artistController.jsfCreate());
        assertTrue("Should be new (NULL ID)!", artistController.getItem().isNew());
        
        artistController.getItem().setName(STRING_1);
        assertEquals(listPage, artistController.jsfSave());
        assertFalse("Should NOT be new!", artistController.getItem().isNew());

        assertEquals(editPage, artistController.jsfEdit(artistController.getItem().getId()));
        assertEquals(STRING_1, artistController.getItem().getName());

        artistController.getItem().setName(STRING_2);
        assertEquals(listPage, artistController.jsfSave());
        
        assertEquals(editPage, artistController.jsfEdit(artistController.getItem().getId()));
        assertEquals(STRING_2, artistController.getItem().getName());

        assertEquals(listPage, artistController.jsfList());
        assertEquals(true, artistController.getItems().contains(artistController.getItem()));

        assertEquals(listPage, artistController.jsfDelete(artistController.getItem().getId()));
        
        assertEquals(listPage, artistController.jsfList());
        assertEquals(false, artistController.getItems().contains(artistController.getItem()));
    }

    @Test
    public void testArtistControllerLinking () {
        artistController.jsfCreate();
        artistController.getItem().setName("LinkedArtist");
        artistController.jsfSave();
        Artist artist = artistController.getItem();

        albumController.jsfCreate();
        albumController.getItem().setName("LinkedAlbum");
        albumController.jsfSave();
        Album album = albumController.getItem();
        
        assertFalse("", artistService.find(artist.getId()).getAlbums().contains(albumService.find(album.getId())));        
        artistController.linkAlbum(artistController.getItem().getId(), albumController.getItem().getId());        
        assertTrue("", artistService.find(artist.getId()).getAlbums().contains(albumService.find(album.getId())));        
        artistController.unlinkAlbum(artistController.getItem().getId(), albumController.getItem().getId());        
        assertFalse("", artistService.find(artist.getId()).getAlbums().contains(albumService.find(album.getId())));        
    }

    @Test
    public void testArtistRest () {
        try {
        	ctx.lookup("java:global/classes/ArtistRest");
		} catch (NamingException e) {
			e.printStackTrace();
			fail("Should not reach here!");
		}
    }
//    @Ignore
//    @Test
//    public void testArtistRestCrud () {
//        WebResource webResource = resource();
//        webResource = webResource.path("artist");
//        List<Artist> responseMsg = webResource.get(List.class);
////        assertTrue(responseMsg.contains("Current time is"));
//        
////        Artist t = new Artist();
////        t.setName(STRING_1);
////
////    	artistRest.create(new JAXBElement<Artist>(new QName("planet"), Artist.class, t));  	
//    }

    @Test
    public void testAlbumServiceCrud () {
        Album t = new Album();
        t.setName(STRING_1);
        assertTrue("", t.isNew());

        albumService.create(t);
        assertFalse("", t.isNew());

        Album s = albumService.find(t.getId());
        assertEquals(STRING_1, s.getName());

        s.setName(STRING_2);
        albumService.update(s);
        s = albumService.find(t.getId());
        assertEquals(STRING_2, s.getName());

        List<Album> list1 = albumService.findAll();
        assertEquals(true, list1.contains(s));

//        List<Album> list2 = artistService.findByNamedQuery(Artist.FIND_ALL, null, 0, 0);
//        assertEquals(true, list2.contains(s));

        albumService.remove(s.getId());
        List<Album> list3 = albumService.findAll();
        assertEquals(false, list3.contains(s));
     }

    @Test
    public void testAlbumServiceLinking () {
        Album album = new Album();
        album.setName("LinkedAlbum");
        albumService.create(album);
        
        Track track = new Track();
        track.setName("LinkedTrack");
        trackService.create(track);

        assertFalse("", albumService.find(album.getId()).getTracks().contains(trackService.find(track.getId())));        
        albumService.linkTrack(album.getId(), track.getId());
        assertTrue("", albumService.find(album.getId()).getTracks().contains(trackService.find(track.getId())));        
        albumService.unlinkTrack(album.getId(), track.getId());
        assertFalse("", albumService.find(album.getId()).getTracks().contains(trackService.find(track.getId())));        
    }

    @Test
    public void testAlbumControllerCrud () {
    	String editPage = "/pages/editAlbum.xhtml?faces-redirect=true";
    	String listPage = "/pages/listAlbums.xhtml?faces-redirect=true";
    	
        assertEquals(editPage, albumController.jsfCreate());
        assertTrue("Should be new (NULL ID)!", albumController.getItem().isNew());
        
        albumController.getItem().setName(STRING_1);
        assertEquals(listPage, albumController.jsfSave());
        assertFalse("Should NOT be new!", albumController.getItem().isNew());

        assertEquals(editPage, albumController.jsfEdit(albumController.getItem().getId()));
        assertEquals(STRING_1, albumController.getItem().getName());

        albumController.getItem().setName(STRING_2);
        assertEquals(listPage, albumController.jsfSave());
        
        assertEquals(editPage, albumController.jsfEdit(albumController.getItem().getId()));
        assertEquals(STRING_2, albumController.getItem().getName());

        assertEquals(listPage, albumController.jsfList());
        assertEquals(true, albumController.getItems().contains(albumController.getItem()));

        assertEquals(listPage, albumController.jsfDelete(albumController.getItem().getId()));
        
        assertEquals(listPage, albumController.jsfList());
        assertEquals(false, albumController.getItems().contains(albumController.getItem()));
    }

    @Test
    public void testAlbumControllerLinking () {
        albumController.jsfCreate();
        albumController.getItem().setName("LinkedAlbum");
        albumController.jsfSave();
        Album album = albumController.getItem();
        
        trackController.jsfCreate();
        trackController.getItem().setName("LinkedTrack");
        trackController.jsfSave();
        Track track = trackController.getItem();

        assertFalse("", albumService.find(album.getId()).getTracks().contains(trackService.find(track.getId())));        
        albumController.linkTrack(albumController.getItem().getId(), trackController.getItem().getId());        
        assertTrue("", albumService.find(album.getId()).getTracks().contains(trackService.find(track.getId())));        
        albumController.unlinkTrack(albumController.getItem().getId(), trackController.getItem().getId());        
        assertFalse("", albumService.find(album.getId()).getTracks().contains(trackService.find(track.getId())));        
    }

    @Test
    public void testAlbumRest () {
        try {
        	ctx.lookup("java:global/classes/AlbumRest");
		} catch (NamingException e) {
			e.printStackTrace();
			fail("Should not reach here!");
		}
    }
    
    @Test
    public void testTrackServiceCrud () {
    	Track t = new Track();
        t.setName(STRING_1);
        assertTrue("", t.isNew());

        trackService.create(t);
        assertFalse("", t.isNew());

        Track s = trackService.find(t.getId());
        assertEquals(STRING_1, s.getName());

        s.setName(STRING_2);
        trackService.update(s);
        s = trackService.find(t.getId());
        assertEquals(STRING_2, s.getName());

        List<Track> list1 = trackService.findAll();
        assertEquals(true, list1.contains(s));

//        List<Track> list2 = artistService.findByNamedQuery(Artist.FIND_ALL, null, 0, 0);
//        assertEquals(true, list2.contains(s));

        trackService.remove(s.getId());
        List<Track> list3 = trackService.findAll();
        assertEquals(false, list3.contains(s));
     }

    @Test
    public void testTrackControllerCrud () {
    	String editPage = "/pages/editTrack.xhtml?faces-redirect=true";
    	String listPage = "/pages/listTracks.xhtml?faces-redirect=true";
    	
        assertEquals(editPage, trackController.jsfCreate());
        assertTrue("Should be new (NULL ID)!", trackController.getItem().isNew());
        
        trackController.getItem().setName(STRING_1);
        assertEquals(listPage, trackController.jsfSave());
        assertFalse("Should NOT be new!", trackController.getItem().isNew());

        assertEquals(editPage, trackController.jsfEdit(trackController.getItem().getId()));
        assertEquals(STRING_1, trackController.getItem().getName());

        trackController.getItem().setName(STRING_2);
        assertEquals(listPage, trackController.jsfSave());
        
        assertEquals(editPage, trackController.jsfEdit(trackController.getItem().getId()));
        assertEquals(STRING_2, trackController.getItem().getName());

        assertEquals(listPage, trackController.jsfList());
        assertEquals(true, trackController.getItems().contains(trackController.getItem()));

        assertEquals(listPage, trackController.jsfDelete(trackController.getItem().getId()));
        
        assertEquals(listPage, trackController.jsfList());
        assertEquals(false, trackController.getItems().contains(trackController.getItem()));
    }

    @Ignore
    @Test
    public void testTrackRest () {
    	trackRest.getAll();
    	
    	Track t = new Track();
        t.setName(STRING_1);
        assertTrue("", t.isNew());

    	trackRest.create(t);
        assertFalse("", t.isNew());

        Track s = trackService.find(t.getId());
        assertEquals(STRING_1, s.getName());

        s.setName(STRING_2);
        trackRest.update(1L, s);
        s = trackService.find(t.getId());
        assertEquals(STRING_2, s.getName());

        List<Track> list1 = trackService.findAll();
        assertEquals(true, list1.contains(s));

        trackRest.delete(s.getId());
        List<Track> list3 = trackService.findAll();
        assertEquals(false, list3.contains(s));
    }
}
