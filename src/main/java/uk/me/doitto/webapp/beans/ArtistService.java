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

import java.util.logging.Level;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import uk.me.doitto.webapp.dao.Crud;
import uk.me.doitto.webapp.entity.Artist;

/**
 *
 * @author ian
 */
@Stateless
public class ArtistService extends Crud<Artist> {

	private static final long serialVersionUID = 1L;

	// see Effective Java Second Edition Item 71: lazy initialization of a static field
	// needed because this class is tested outside any container so can't just get an initial context
	private static class FieldHolder {
		
		static final ArtistService dwrTrackService = newInstance();
		
	    static ArtistService newInstance () {
	    	try {   		
	        	return (ArtistService)new InitialContext().lookup("java:global/myapp/ArtistService");
			} catch (NamingException e) {
				throw new RuntimeException("Could not create an ArtistService EJB for DWR", e);
			}
	    }
	}
		
    @EJB
	private AlbumService albumService;

    public ArtistService () {
        super(Artist.class);
    }
    
    /**
     * For DWR
     * @return the bean
     */
	public static ArtistService getInstance () {
		return FieldHolder.dwrTrackService;
	}
    
    public void linkAlbum (final Long id, final Long albumId) {
    	assert albumService != null;
    	assert id != null;
    	assert albumId != null;
        LOGGER.log(Level.FINE, "Linking Artist: {0} to Album: {1}", new Object[]{id, albumId});
        find(id).addAlbum(albumService.find(albumId));
    }

    public void unlinkAlbum (final Long id, final Long albumId) {
    	assert albumService != null;
    	assert id != null;
    	assert albumId != null;
    	LOGGER.log(Level.FINE, "Unlinking Artist: {0} from Album: {1}", new Object[]{id, albumId});
        find(id).removeAlbum(albumService.find(albumId));
    }
}
