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

package uk.me.doitto.webapp.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.metamodel.SingularAttribute;

/**
 *
 * @param <T> persistent class
 * @param <PK> Database primary key type, should be serializable !
 * @author ian
 */
public interface ICrud<T, PK extends Serializable> extends IDb<PK> {

    void create (T t);

    void delete (PK id);

    T find (PK id);

    List<T> findAll ();

    List<T> findRange (int first, int max);

    T update (T t);

    List<T> findByNamedQuery (String queryName, Map<String, Object> parameters, int first, int max);
    
    List<T> before (SingularAttribute<? super T, Date> attribute, Date date);
    
    List<T> since (SingularAttribute<? super T, Date> attribute, Date date);
    
    List<T> during (SingularAttribute<? super T, Date> attribute, Date date1, Date date2);
    
    List<T> notDuring (SingularAttribute<? super T, Date> attribute, Date date1, Date date2);
    
    List<T> search (SingularAttribute<? super T, String> attribute, String queryString);
    
	int count();
}
