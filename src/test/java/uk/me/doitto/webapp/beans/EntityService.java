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
package uk.me.doitto.webapp.beans;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;

import uk.me.doitto.jpadao.Crud;
import uk.me.doitto.webapp.entity.SimpleEntity;

/**
 * @author ian
 *
 */
@ManagedBean
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class EntityService extends Crud<SimpleEntity> {
	
	private static final long serialVersionUID = 1L;

	public EntityService (final EntityManager em) {
		super(SimpleEntity.class, em);
	}

}
