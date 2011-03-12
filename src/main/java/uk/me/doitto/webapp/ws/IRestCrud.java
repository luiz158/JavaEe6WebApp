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
import java.util.Date;
import java.util.List;

import javax.persistence.metamodel.SingularAttribute;
import javax.ws.rs.core.Response;

import uk.me.doitto.webapp.dao.AbstractEntity;
import uk.me.doitto.webapp.dao.IDb;

/**
 *
 * @param <T>
 * @author ian
 */
public interface IRestCrud<T extends AbstractEntity, PK extends Serializable> extends IDb<PK> {
	
    Response create (T t) ;

    T update (PK id, T t);
    
    List<T> getAll ();
    
    List<T> getRange (int first, int max);

    T getById (PK id);

    Response delete (PK id);
    
    List<T> before (String attribute, long date);
    
    List<T> since (String attribute, long date);
    
    List<T> during (String attribute, long date1, long date2);
    
    List<T> notDuring (String attribute, long date1, long date2);
    
	String count();
}
