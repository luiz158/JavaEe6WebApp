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

import java.io.Serializable;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import uk.me.doitto.webapp.beans.AlbumService;
import uk.me.doitto.webapp.entity.Album;
import uk.me.doitto.webapp.util.Globals;

/**
 *
 * @author ian
 */
@Path(AlbumRest.PATH)
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NEVER)
public class AlbumRest implements IRestCrud<Album>,Serializable {

	private static final long serialVersionUID = 1L;

    public static final String PATH = "/album";
    
    @EJB
    AlbumService albumService;

    @Context
    private UriInfo uriInfo;

    @PUT
    @Path("/create/{title}")
    public Response createXtor(@PathParam("title") final String title) {
    	Album album = new Album(title);
        albumService.create(album);
        URI uri = uriInfo.getBaseUriBuilder().path(AlbumRest.PATH + "/" + album.getId().toString()).build();
        return Response.created(uri).build();
    }

    @POST
    @Override
    public Response create (final JAXBElement<Album> jaxb) {
    	Album album = jaxb.getValue();
    	albumService.create(album);
        URI uri = uriInfo.getAbsolutePathBuilder().path(album.getId().toString()).build();
        return Response.created(uri).build();
    }

    @PUT
    @Override
    public Album update (final JAXBElement<Album> jaxb) {
    	return albumService.update(jaxb.getValue());
    }
    
    @GET
    @Override
    public List<Album> getAll() {
        return albumService.findAll();
    }

    @GET
    @Path("{id}/")
    @Override
    public Album getById (@PathParam("id") final Long id) {
         return albumService.find(id);
    }

    @DELETE
    @Path("{id}/")
    @Override
    public Response delete (@PathParam("id") final Long id) {
    	albumService.delete(id);
        return Response.ok().build();
    }

    @GET
    @Path("/titleidmap")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Long> getTitleIdMap() {
    	Globals.LOGGER.log(Level.FINE, "");
        Map<String, Long> albumTitleMap = new TreeMap<String, Long>();
        for (Album album : albumService.findAll()) {
            albumTitleMap.put(album.getName(), album.getId());
        }
        return albumTitleMap;
    }

    @GET
    @Path("/tracks/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Long> getTracks(@PathParam("id") final Long id) {
    	Globals.LOGGER.log(Level.FINE, "");
        return albumService.find(id).getTrackIdMap();
    }

    @GET
    @Path("/linktrack")
    @Produces(MediaType.TEXT_HTML)
    public Response linkTrack(@QueryParam("albumid") final Long id, @QueryParam("trackid") final Long trackId) {
    	Globals.LOGGER.log(Level.FINE, "Linking Album: {0} to Track: {1}", new Object[]{id, trackId});
        albumService.linkTrack(id, trackId);
        return Response.ok().build();
    }

    @GET
    @Path("/unlinktrack")
    @Produces(MediaType.TEXT_HTML)
    public Response unlinkTrack(@QueryParam("albumid") final Long id, @QueryParam("trackid") final Long trackId) {
    	Globals.LOGGER.log(Level.FINE, "Unlinking Album: {0} from Track: {1}", new Object[]{id, trackId});
        albumService.unlinkTrack(id, trackId);
        return Response.ok().build();
    }
}
