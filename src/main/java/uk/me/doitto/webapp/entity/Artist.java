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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import uk.me.doitto.jpadao.AbstractEntity;

/**
 * Class representing an entire band, a band member, a solo artist, a composer, or a performer
 * 
 * @author Ian Smith
 *
 */
@Entity
@XmlRootElement
public class Artist extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Album> albums = new HashSet<Album>();

    // for hibernate
    public Artist () {
    }

    // for searching
    public Artist (final String name) {
        super(name);
    }

    // Copy constructor
    public Artist (final Artist artist) {
        this(artist.name);
        id = artist.id;
        version = artist.version;
        albums = new HashSet<Album>(artist.albums);
    }

    public Set<Album> getAlbums () {
        return albums;
    }

    public void setAlbums (final Set<Album> albums) {
        this.albums = albums;
    }

    public boolean isLinked () {
        return (!getAlbums().isEmpty());
    }

    public Map<String, Long> getAlbumIdMap () {
        Map<String, Long> trackIdMap = new TreeMap<String, Long>();
        for (Album album : albums) {
            trackIdMap.put(album.getName(), album.getId());
        }
        return trackIdMap;
    }

    /**
     * Creates a two-way link between this artist and an album object
     *
     * @param album
     */
    public void addAlbum (final Album album) {
        albums.add(album);
    }

    /**
     * Destroys a two-way link between this artist and an album object
     *
     * @param album
     */
    public void removeAlbum (final Album album) {
        albums.remove(album);
    }
}
