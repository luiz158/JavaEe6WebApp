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

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import uk.me.doitto.webapp.beans.TrackService;
import uk.me.doitto.webapp.dao.Crud;
import uk.me.doitto.webapp.entity.Track;

/**
 *
 * @author ian
 */
@Path(TrackRest.PATH)
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NEVER)
public class TrackRest extends RestCrudBase<Track> {

	private static final long serialVersionUID = 1L;

    public static final String PATH = "/track";
    
    public static final String QP_TRACKID = "trackid";
    
    @EJB
    private TrackService trackService;

    @Context
    private transient UriInfo uriInfo;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Track overlay (final Track incoming, final Track existing) {
		super.overlay(incoming, existing);
    	if (incoming.getDuration() != 0) {
    		existing.setDuration(incoming.getDuration());
    	}
    	if (incoming.getUrl() != null) {
    		existing.setUrl(incoming.getUrl());
    	}
		return existing;
	}

    @POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Override
    public Response create (final Track track) {
    	assert track != null;
    	Track combined = overlay(track, new Track());
    	trackService.create(combined);
    	assert !combined.isNew();
        URI uri = uriInfo.getAbsolutePathBuilder().path(combined.getId().toString()).build();
        return Response.created(uri).entity(combined).build();
    }

	@Override
	protected Crud<Track> getService() {
		return trackService;
	}
}
