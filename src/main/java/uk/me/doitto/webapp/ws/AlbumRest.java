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

import uk.me.doitto.webapp.beans.AlbumService;
import uk.me.doitto.webapp.dao.Crud;
import uk.me.doitto.webapp.entity.Album;
import uk.me.doitto.webapp.util.Globals;

/**
 *
 * @author ian
 */
@Path(AlbumRest.PATH)
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NEVER)
public class AlbumRest extends RestCrudBase<Album> {

	private static final long serialVersionUID = 1L;

    public static final String PATH = "/album";
    
    public static final String LINK_TRACK = "linktrack";
    
    public static final String UNLINK_TRACK = "unlinktrack";
    
    public static final String QP_ALBUMID = "albumid";
    
    @EJB
    private AlbumService albumService;

    @Context
    private transient UriInfo uriInfo;

	@Override
	protected Album overlay (final Album incoming, final Album existing) {
		super.overlay(incoming, existing);
    	if (incoming.getLabel() != null) {
    		existing.setLabel(incoming.getLabel());
    	}
    	if (incoming.getCatId() != null) {
    		existing.setCatId(incoming.getCatId());
    	}
    	if (incoming.getDate() != null) {
    		existing.setDate(incoming.getDate());
    	}
		return existing;
	}

    @POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public Response create (final Album album) {
    	assert album != null;
    	Album combined = overlay(album, new Album());
    	albumService.create(combined);
    	assert !combined.isNew();
        URI uri = uriInfo.getAbsolutePathBuilder().path(combined.getId().toString()).build();
        return Response.created(uri).entity(combined).build();
    }

    @GET
    @Path(LINK_TRACK)
    @Produces(MediaType.TEXT_HTML)
    public Response linkTrack(@QueryParam(QP_ALBUMID) final Long id, @QueryParam(TrackRest.QP_TRACKID) final Long trackId) {
		assert id >= 0;
		assert trackId >= 0;
    	Globals.LOGGER.log(Level.FINE, "Linking Album: {0} to Track: {1}", new Object[]{id, trackId});
        albumService.linkTrack(id, trackId);
        return Response.ok().build();
    }

    @GET
    @Path(UNLINK_TRACK)
    @Produces(MediaType.TEXT_HTML)
    public Response unlinkTrack(@QueryParam(QP_ALBUMID) final Long id, @QueryParam(TrackRest.QP_TRACKID) final Long trackId) {
		assert id >= 0;
		assert trackId >= 0;
    	Globals.LOGGER.log(Level.FINE, "Unlinking Album: {0} from Track: {1}", new Object[]{id, trackId});
        albumService.unlinkTrack(id, trackId);
        return Response.ok().build();
    }

	@Override
	protected Crud<Album> getService() {
		return albumService;
	}
}
