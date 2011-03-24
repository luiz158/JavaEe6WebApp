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
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Abstract implementation of PersistentEntity, in which the primary key type
 * parameter is set to Long
 * 
 * @author Ian Smith
 */
@SuppressWarnings("serial")
@MappedSuperclass
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractEntity implements PersistentEntity<Long>, Comparable<AbstractEntity> {

	/**
	 * For use by persistence provider, not sent to REST output
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlTransient
	protected Long id;

	/**
	 * For use in REST output, not persisted
	 */
	@XmlID
	@Transient
	protected String xmlId;
	
	/**
	 * For use by persistence provider
	 */
	@Version
	protected int version;

	/**
	 * for default equals(Object) & hashCode()
	 */
	protected String name;

    @Temporal(TemporalType.TIMESTAMP)
	private Date created;

    @Temporal(TemporalType.TIMESTAMP)
	private Date modified;

    @Temporal(TemporalType.TIMESTAMP)
	private Date accessed;

	protected AbstractEntity () {
		final Date date = new Date();
		created = new Date(date.getTime());
		modified = new Date(date.getTime());
		accessed = new Date(date.getTime());
	}

	/**
	 * Copy constructor
	 * @param entity
	 */
	protected AbstractEntity (final AbstractEntity entity) {
		assert entity != null;
		if (! isNew()) {
			this.id = Long.valueOf(id.longValue());
		}
		this.version = entity.version;
		this.name = entity.name;
		this.created = new Date(entity.created.getTime());
		this.modified = new Date(entity.modified.getTime());
		this.accessed = new Date(entity.accessed.getTime());
	}
	
    protected static JAXBContext initJaxbContext (final Class<? extends AbstractEntity> clazz) {
    	try {
			return JAXBContext.newInstance(clazz);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
    }
    
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long getId () {
		return id;
	}

	/**
	 * Populate xmlId field when retrieved from database.  
	 * Only ever called by persistence framework, so suppress warning
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unused")
	@PostLoad
	private void initXmlId () {
		xmlId = id.toString();
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date getCreated () {
		return new Date(created.getTime());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date getModified () {
		return new Date(modified.getTime());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setModified (final Date modified) {
		assert modified != null;
		this.modified = new Date(modified.getTime());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date getAccessed () {
		return new Date(accessed.getTime());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAccessed (final Date accessed) {
		assert accessed != null;
		this.accessed = new Date(accessed.getTime());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@Override
	public final AbstractEntity deepCopy () throws IOException, ClassNotFoundException {
		ObjectOutputStream oos = null;
		try {
			// Write the object out to a byte array
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(this);
			oos.flush();
			oos.close();
			// Make an input stream from the byte array and read a copy of the object back in.
			return getClass().cast(new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray())).readObject());
		} finally {
			try {
				if (oos != null) {
					oos.close();
				}
			} catch (IOException ex) {
				throw new IOException(ex);
			}
		}
	}

	/**
	 * Used internally by toXml() to provide a default JAXB XML toString() implementation
	 * @return a class-specific context instance
	 */
	protected abstract JAXBContext getJaxbContext ();
	
	/**
	 * @return a default JAXB XML String representation of the entity
	 * @throws JAXBException
	 */
	private String toXml () throws JAXBException {
		Writer writer = new StringWriter();
		Marshaller marshaller = getJaxbContext().createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		marshaller.marshal(this, writer);
		return writer.toString();
	}
	
	@Override
	public String toString () {		
		try {
			return toXml();
		} catch (JAXBException e) {
			return "[" + this.getClass().getSimpleName() + ": " + id + " v" + version + " - " + name + "]";
		}
	}

	@Override
	public int compareTo (final AbstractEntity obj) {
		assert obj != null;
		if (this == obj) {
			return 0;
		}
		return id.compareTo(obj.getId());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractEntity other = (AbstractEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
