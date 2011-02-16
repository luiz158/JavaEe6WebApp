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

import java.util.List;

import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;

import uk.me.doitto.webapp.dao.AbstractEntity;

/**
 *
 * @param <T>
 * @author ian
 */
public interface IRestCrud<T extends AbstractEntity> {

    Response create (JAXBElement<T> jaxb) ;

    T update (JAXBElement<T> jaxb);
    
    List<T> getAll ();

    T getById (Long id);

    Response delete (Long id);
}
