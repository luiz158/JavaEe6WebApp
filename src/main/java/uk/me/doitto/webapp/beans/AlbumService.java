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

import uk.me.doitto.webapp.dao.Crud;
import uk.me.doitto.webapp.entity.Album;

/**
 *
 * @author ian
 */
@Stateless
public class AlbumService extends Crud<Album> {

	private static final long serialVersionUID = 1L;

    @EJB
    private final TrackService trackService = new TrackService();

    public AlbumService() {
        super(Album.class);
    }
    
    public void linkTrack(final Long id, final Long trackId) {
    	assert id != null;
    	assert trackId != null;
    	LOGGER.log(Level.FINE, "Linking Album: {0} to Track: {1}", new Object[]{id, trackId});
        find(id).addToTrackListing(trackService.find(trackId));
    }

    public void unlinkTrack(final Long id, final Long trackId) {
    	assert id != null;
    	assert trackId != null;
    	LOGGER.log(Level.FINE, "Unlinking Album: {0} from Track: {1}", new Object[]{id, trackId});
        find(id).removeFromTrackListing(trackService.find(trackId));
    }
}
