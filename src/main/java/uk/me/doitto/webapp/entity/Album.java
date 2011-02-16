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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.validation.constraints.Past;
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

    private String label;

    private String catId;

    @Temporal(javax.persistence.TemporalType.DATE)
    @Past
    private Date releaseDate;
    
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Track> tracks = new HashSet<Track>();

    // for hibernate
    public Album () {
    }

    // for searching
    public Album (final String name) {
        super(name);
    }

    // Copy constructor
    public Album (final Album album) {
        this(album.name);
        id = album.id;
        version = album.version;
        label = album.label;
        catId = album.catId;
        if (album.releaseDate != null) {
            releaseDate = new Date(album.releaseDate.getTime());
        }
        tracks = new HashSet<Track>(album.tracks);
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
        return new Date(releaseDate.getTime());
    }

    /**
     * Setter for the year field
     * @param releaseDate
     */
    public void setDate (final Date date) {
        this.releaseDate = new Date(date.getTime());
    }

    public Set<Track> getTracks () {
        return tracks;
    }

    public void setTracks (final Set<Track> tracks) {
        this.tracks = tracks;
    }

    public Map<String, Long> getTrackIdMap () {
        Map<String, Long> trackIdMap = new TreeMap<String, Long>();
        for (Track track : tracks) {
            trackIdMap.put(track.getName(), track.getId());
        }
        return trackIdMap;
    }

    /**
     * Creates a two-way link between this album and a track object
     *
     * @param track
     */
    public void addToTrackListing (final Track track) {
        tracks.add(track);
    }

    /**
     * Destroys a two-way link between this album and a track object
     *
     * @param track
     */
    public void removeFromTrackListing (final Track track) {
        tracks.remove(track);
    }
}
