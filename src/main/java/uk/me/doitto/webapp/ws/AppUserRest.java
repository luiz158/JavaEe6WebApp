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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import uk.me.doitto.webapp.beans.AppUserService;
import uk.me.doitto.webapp.entity.AppUser;

/**
 *
 * @author ian
 */
@Path(AppUserRest.PATH)
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NEVER)
public class AppUserRest implements IRestCrud<AppUser> {

	private static final long serialVersionUID = 1L;

    public static final String PATH = "/appuser";
    
    @EJB
    AppUserService appUserService;
    
    @Context
    private UriInfo uriInfo;

    @PUT
    @Path("/create/{username}/{password}")
    public Response createXtor(@PathParam("username") final String username, @PathParam("password") final String password) {
    	AppUser appUser = new AppUser(username, password);
    	appUserService.create(appUser);
        URI uri = uriInfo.getBaseUriBuilder().path(AppUserRest.PATH + "/" + appUser.getId().toString()).build();
        return Response.created(uri).entity(appUser).build();
    }

    @POST
    @Override
    public Response create (final JAXBElement<AppUser> jaxb) {
    	AppUser appUser = jaxb.getValue();
    	appUserService.create(appUser);
        URI uri = uriInfo.getAbsolutePathBuilder().path(appUser.getId().toString()).build();
        return Response.created(uri).entity(appUser).build();
    }

    @PUT
    @Override
    public AppUser update (final JAXBElement<AppUser> jaxb) {
    	return appUserService.update(jaxb.getValue());
    }
    
    @GET
    @Override
    public List<AppUser> getAll() {
        return appUserService.findAll();
    }

    @GET
    @Path("{id}/")
    @Override
    public AppUser getById (@PathParam("id") final Long id) {
         return appUserService.find(id);
    }

    @DELETE
    @Path("{id}/")
    @Override
    public Response delete (@PathParam("id") final Long id) {
    	appUserService.delete(id);
        return Response.ok().build();
    }
}
