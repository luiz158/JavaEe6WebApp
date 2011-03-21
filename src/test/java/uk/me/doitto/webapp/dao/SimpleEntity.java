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

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@NamedQuery(name = SimpleEntity.FIND_ALL, query = "SELECT e FROM SimpleEntity e")
public class SimpleEntity extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;

    private static final JAXBContext jaxbContext = initJaxbContext(SimpleEntity.class);
	
    public static final String FIND_ALL = "SimpleEntity.findAll";

	public SimpleEntity () {
		super();
	}
	
	public SimpleEntity (final SimpleEntity entity) {
		super(entity);
	}

    @Override
	public JAXBContext getJaxbContext () {
		return jaxbContext;
	}
}

