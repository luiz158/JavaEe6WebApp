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

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

/**
 * Top level interface for all persistent entities, can be used to generate a
 * base class
 * 
 * @param <PK>
 *            The database primary key for this PersistentEntity
 * @author Ian Smith
 */
public interface PersistentEntity<PK extends Serializable> extends IDb<PK> {

	/**
	 * Obtain the database primary key for this PersistentEntity
	 * 
	 * @return The primary key
	 */
	PK getId ();

	/**
	 * Obtain the database locking version for this PersistentEntity
	 * 
	 * @return The version
	 */
	int getVersion ();

	/**
	 * Has the entity been stored in the database yet?
	 * 
	 * @return true if it has, false if not
	 */
	boolean isNew ();

	/**
	 * @return the entity name, used by equals() unless it is overridden
	 */
	String getName ();

	void setName (String name);

	/**
	 * @return the creation date for this entity
	 */
	Date getCreated ();

	/**
	 * @return the latest modification date for this entity
	 */
	Date getModified ();

	void setModified (Date modified);

	/**
	 * @return date that this entity was last retrieved by id (not by a general query)
	 */
	Date getAccessed ();

	void setAccessed (Date accessed);

	/**
	 * Obtain a deep copy of the entity
	 * 
	 * @return the copied object
	 * @throws EntityException
	 *             if Java serialisation fails
	 */
	PersistentEntity<PK> deepCopy () throws IOException, ClassNotFoundException;
}
