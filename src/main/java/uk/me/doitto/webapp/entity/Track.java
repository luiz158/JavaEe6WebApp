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

import javax.persistence.Entity;
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

    private int duration = 0;
    
    private String url = "http://doitto.me.uk/";

    // for hibernate
    public Track () {
    }

    // for searching
    public Track (final String name) {
        super(name);
    }

    // Copy constructor
    public Track (final Track track) {
    	super(track);
    	duration = track.duration;
        url = track.url;
    }

    public int getDuration () {
		return duration;
	}

	public void setDuration (int duration) {
		this.duration = duration;
	}

	public String getUrl () {
        return url;
    }

    public void setUrl (final String url) {
        this.url = url;
    }
}
