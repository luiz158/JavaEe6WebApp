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

import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.validation.constraints.Past;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;

import uk.me.doitto.webapp.dao.AbstractEntity;

/**
 * Class representing a compilation of two or more tracks, eg. an album, single, EP etc.
 * 
 * This class is used both for persistent storage by hibernate, and as a form backing object in the web user interface.
 * 
 * @author Ian Smith
 *
 */
@Entity
@XmlRootElement
public class Album extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    private static final JAXBContext jaxbContext = initJaxbContext(Album.class);

    private String label = "UnknownLabel";

    private String catId = "UnknownId";

    @Temporal(javax.persistence.TemporalType.DATE)
    @Past
    private Date releaseDate;
    
    /**
     * Intentionally mutable field, so use a concurrent collection
     */
    @XmlIDREF
    @XmlList
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Track> tracks = new ConcurrentSkipListSet<Track>();

    // for hibernate
    public Album () {
    }

    // Copy constructor
    public Album (final Album album) {
    	super(album);
        label = album.label;
        catId = album.catId;
        Date albumReleaseDate = album.releaseDate;
        if (albumReleaseDate != null) {
            releaseDate = new Date(albumReleaseDate.getTime());
        }
        // intentionally mutable, just pass reference
        tracks = album.tracks;
    }

    @Override
	public JAXBContext getJaxbContext () {
		return jaxbContext;
	}

    /**
     * Getter for the label field
     * @return label
     */
    public String getLabel () {
        return label;
    }

    /**
     * Setter for the label field
     * @param label
     */
    public void setLabel (final String label) {
    	assert label != null;
        this.label = label;
    }

    /**
     * Getter for the catId field (catalogue number)
     * @return catId
     */
    public String getCatId () {
        return catId;
    }

    /**
     * Setter for the catId field (catalogue number)
     * @param catId
     */
    public void setCatId (final String catId) {
    	assert catId != null;
        this.catId = catId;
    }

    /**
     * Getter for the year field
     * @return releaseDate
     */
    public Date getDate () {
        if (releaseDate == null) {
            return null;
        }
    	// make defensive copy
        return new Date(releaseDate.getTime());
    }

    /**
     * Setter for the year field
     * @param releaseDate
     */
    public void setDate (final Date date) {
    	assert date != null;
    	// make defensive copy
        this.releaseDate = new Date(date.getTime());
    }

    public Set<Track> getTracks () {
        // intentionally mutable, just return reference
        return tracks;
    }

    public void setTracks (final Set<Track> tracks) {
    	assert tracks != null;
        // intentionally mutable, just pass reference
        this.tracks = tracks;
    }

    /**
     * Creates a two-way link between this album and a track object
     *
     * @param track
     */
    public void addToTrackListing (final Track track) {
    	assert track != null;
        tracks.add(track);
        track.getAlbums().add(this);
    }

    /**
     * Destroys a two-way link between this album and a track object
     *
     * @param track
     */
    public void removeFromTrackListing (final Track track) {
    	assert track != null;
        tracks.remove(track);
        track.getAlbums().remove(this);
    }
}
