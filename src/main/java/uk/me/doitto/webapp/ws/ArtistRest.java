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

import uk.me.doitto.webapp.beans.ArtistService;
import uk.me.doitto.webapp.entity.Artist;
import uk.me.doitto.webapp.util.Globals;

/**
 *
 * @author ian
 */
@Path(ArtistRest.PATH)
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NEVER)
public class ArtistRest implements IRestCrud<Artist> {

	private static final long serialVersionUID = 1L;

    public static final String PATH = "/artist";
    
    @EJB
    private ArtistService artistService;

    @Context
    private UriInfo uriInfo;

    @PUT
    @Path("/create/{title}")
    public Response createXtor(@PathParam("title") final String title) {
    	Artist artist = new Artist(title);
    	artistService.create(artist);
        URI uri = uriInfo.getBaseUriBuilder().path(ArtistRest.PATH + "/" + artist.getId().toString()).build();
        return Response.created(uri).build();
    }

    @POST
    @Override
    public Response create (final JAXBElement<Artist> jaxb) {
    	Artist artist = jaxb.getValue();
    	artistService.create(artist);
        URI uri = uriInfo.getAbsolutePathBuilder().path(artist.getId().toString()).build();
        return Response.created(uri).build();
    }

    @PUT
    @Override
    public Artist update (final JAXBElement<Artist> jaxb) {
    	return artistService.update(jaxb.getValue());
    }
    
    @GET
    @Override
    public List<Artist> getAll() {
        return artistService.findAll();
    }

    @GET
    @Path("{id}/")
    @Override
    public Artist getById (@PathParam("id") final Long id) {
         return artistService.find(id);
    }

    @DELETE
    @Path("{id}/")
    @Override
    public Response delete (@PathParam("id") final Long id) {
    	artistService.delete(id);
        return Response.ok().build();
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
    @Path("/linkalbum")
    @Produces(MediaType.TEXT_HTML)
    public Response linkAlbum(@QueryParam("artistid") final Long id, @QueryParam("albumid") final Long albumId) {
    	Globals.LOGGER.log(Level.FINE, "Linking Artist: {0} to Album: {1}", new Object[]{id, albumId});
        artistService.linkAlbum(id, albumId);
        return Response.ok().build();
    }

    @GET
    @Path("/unlinkalbum")
    @Produces(MediaType.TEXT_HTML)
    public Response unlinkAlbum(@QueryParam("artistid") final Long id, @QueryParam("albumid") final Long albumId) {
    	Globals.LOGGER.log(Level.FINE, "Unlinking Artist: {0} from Album: {1}", new Object[]{id, albumId});
        artistService.unlinkAlbum(id, albumId);
        return Response.ok().build();
    }
}
