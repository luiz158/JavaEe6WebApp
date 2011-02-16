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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlElement;

/**
 * Abstract implementation of PersistentEntity, in which the primary key type
 * parameter is set to Long
 * 
 * @author Ian Smith
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class AbstractEntity implements PersistentEntity<Long>, Comparable<AbstractEntity> {

	/**
	 * For use by persistence provider
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlElement
	protected Long id;

	/**
	 * For use by persistence provider
	 */
	@Version
	@XmlElement
	protected int version;

	/**
	 * for default equals(Object) & hashCode()
	 */
//	@Size(min = 1)
//	@XmlElement
	protected String name;

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
//	@XmlElement
	private Date created;

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
//	@XmlElement
	private Date modified;

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
//	@XmlElement
	private Date accessed;

	protected AbstractEntity () {
		Date date = new Date();
		created = date;
		modified = date;
		accessed = date;
	}

	protected AbstractEntity (final String name) {
		this();
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long getId () {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isNew () {
		return (id == null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getVersion () {
		return version;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName () {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setName (final String name) {
		this.name = name;
	}

	@Override
	public Date getAccessed () {
		return new Date(accessed.getTime());
	}

	@Override
	public void setAccessed (final Date accessed) {
		this.accessed = new Date(accessed.getTime());
	}

	@Override
	public Date getCreated () {
		return new Date(created.getTime());
	}

	@Override
	public Date getModified () {
		return new Date(modified.getTime());
	}

	@Override
	public void setModified (final Date modified) {
		this.modified = new Date(modified.getTime());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@Override
	public AbstractEntity deepCopy () throws IOException, ClassNotFoundException {
		Object obj = null;
		ObjectOutputStream out = null;
		try {
			// Write the object out to a byte array
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			out = new ObjectOutputStream(bos);
			out.writeObject(this);
			out.flush();
			out.close();
			// Make an input stream from the byte array and read a copy of the
			// object back in.
			obj = new ObjectInputStream(new ByteArrayInputStream(
					bos.toByteArray())).readObject();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException ex) {
				Logger.getLogger(AbstractEntity.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return (AbstractEntity) obj;
	}

	@Override
	public int compareTo (final AbstractEntity obj) {
		if (this == obj) {
			return 0;
		}
		return name.compareTo(obj.getName());
	}

	@Override
	public boolean equals (final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final AbstractEntity other = (AbstractEntity) obj;
		if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode () {
		int hash = 5;
		hash = 67 * hash + (this.name != null ? this.name.hashCode() : 0);
		return hash;
	}

	@Override
	public String toString () {
		return "[" + this.getClass().getSimpleName() + ": " + id + " v" + version + " - " + name + "]";
	}
}
