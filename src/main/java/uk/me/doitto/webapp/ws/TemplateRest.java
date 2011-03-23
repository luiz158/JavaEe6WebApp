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

/**
 * 
 */
package uk.me.doitto.webapp.ws;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author super
 *
 */
@Path("/helloworld")
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TemplateRest implements Serializable {

	private static final long serialVersionUID = 1L;

    final static String world = "Hello World!";

    @GET 
    @Produces("text/plain")
    public String getClichedMessage() {
        return world;
    }
    
    @Path("date")
    @GET
    @Produces("text/plain")
    public String getWorld () {
        return world + " - Current time is: " + new Date();
    }
}
