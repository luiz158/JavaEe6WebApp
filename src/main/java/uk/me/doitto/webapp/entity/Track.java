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
package uk.me.doitto.webapp.entity;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;

import uk.me.doitto.webapp.dao.AbstractEntity;

/**
 * Class representing a single track
 * 
 * @author Ian Smith
 *
 */
@Entity
@XmlRootElement
public class Track extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    private static final JAXBContext jaxbContext = initJaxbContext(Track.class);

    private int duration = 0;
    
    private String url = "http://doitto.me.uk/";

    /**
     * Intentionally mutable field, so use a concurrent collection
     */
    @XmlIDREF
    @XmlList
    @ManyToMany(mappedBy = "tracks", fetch = FetchType.EAGER)
    private Set<Album> albums = new ConcurrentSkipListSet<Album>();
    
    // for hibernate
    public Track () {
    }

    // Copy constructor
    public Track (final Track track) {
    	super(track);
    	duration = track.duration;
        url = track.url;
    }

    @Override
	public JAXBContext getJaxbcontext () {
		return jaxbContext;
	}

    public int getDuration () {
		return duration;
	}

	public void setDuration (int duration) {
		assert duration >= 0;
		this.duration = duration;
	}

	public String getUrl () {
        return url;
    }

    public void setUrl (final String url) {
    	assert url != null;
        this.url = url;
    }

	public Set<Album> getAlbums () {
        // intentionally mutable, just return reference
		return albums;
	}

	public void setAlbums (final Set<Album> albums) {
        // intentionally mutable, just pass reference
		this.albums = albums;
	}
}
