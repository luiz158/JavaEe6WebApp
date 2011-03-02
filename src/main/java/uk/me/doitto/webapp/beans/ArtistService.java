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
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.bean.ManagedBean;

import uk.me.doitto.webapp.dao.Crud;
import uk.me.doitto.webapp.entity.Artist;

/**
 *
 * @author ian
 */
@ManagedBean
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ArtistService extends Crud<Artist> {

	private static final long serialVersionUID = 1L;

    @EJB
	private final AlbumService albumService = new AlbumService();

    public ArtistService() {
        super(Artist.class);
    }
    
    public void linkAlbum (final Long id, final Long albumId) {
        LOGGER.log(Level.FINE, "Linking Artist: {0} to Album: {1}", new Object[]{id, albumId});
        find(id).addAlbum(albumService.find(albumId));
    }

    public void unlinkAlbum (final Long id, final Long albumId) {
    	LOGGER.log(Level.FINE, "Unlinking Artist: {0} from Album: {1}", new Object[]{id, albumId});
        find(id).removeAlbum(albumService.find(albumId));
    }
}
