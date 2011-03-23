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
package uk.me.doitto.webapp.ws;

import java.net.URI;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import uk.me.doitto.webapp.beans.ArtistService;
import uk.me.doitto.webapp.dao.Crud;
import uk.me.doitto.webapp.entity.Artist;
import uk.me.doitto.webapp.util.Globals;

/**
 *
 * @author ian
 */
@Path(ArtistRest.PATH)
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NEVER)
public class ArtistRest extends RestCrudBase<Artist> {

	private static final long serialVersionUID = 1L;

    public static final String PATH = "/artist";
    
    public static final String LINK_ALBUM = "linkalbum";
    
    public static final String UNLINK_ALBUM = "unlinkalbum";
    
    public static final String QP_ARTISTID = "artistid";
    
    @EJB
    private ArtistService artistService;

    @Context
    private transient UriInfo uriInfo;
    
    @Override
	protected Artist overlay (final Artist incoming, final Artist existing) {
		return super.overlay(incoming, existing);
    }
    
    @POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public Response create (final Artist artist) {
		assert artist != null;
    	Artist combined = overlay(artist, new Artist());
    	artistService.create(combined);
    	assert !combined.isNew();
        URI uri = uriInfo.getAbsolutePathBuilder().path(combined.getId().toString()).build();
        return Response.created(uri).entity(combined).build();
    }

    public Map<String, Long> getArtistNameIdMap() {
        Globals.LOGGER.log(Level.FINE, "");
        Map<String, Long> artistNameMap = new TreeMap<String, Long>();
        for (Artist artist : artistService.findAll()) {
            artistNameMap.put(artist.getName(), artist.getId());
        }
        return artistNameMap;
    }

    @GET
    @Path(LINK_ALBUM)
    @Produces(MediaType.TEXT_HTML)
    public Response linkAlbum(@QueryParam(QP_ARTISTID) final Long id, @QueryParam(AlbumRest.QP_ALBUMID) final Long albumId) {
		assert id >= 0;
		assert albumId >= 0;
    	Globals.LOGGER.log(Level.FINE, "Linking Artist: {0} to Album: {1}", new Object[]{id, albumId});
        artistService.linkAlbum(id, albumId);
        return Response.ok().build();
    }

    @GET
    @Path(UNLINK_ALBUM)
    @Produces(MediaType.TEXT_HTML)
    public Response unlinkAlbum(@QueryParam(QP_ARTISTID) final Long id, @QueryParam(AlbumRest.QP_ALBUMID) final Long albumId) {
		assert id >= 0;
		assert albumId >= 0;
    	Globals.LOGGER.log(Level.FINE, "Unlinking Artist: {0} from Album: {1}", new Object[]{id, albumId});
        artistService.unlinkAlbum(id, albumId);
        return Response.ok().build();
    }

	@Override
	protected Crud<Artist> getService() {
		return artistService;
	}
}
