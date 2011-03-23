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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlRootElement;

import uk.me.doitto.webapp.dao.AbstractEntity;

/**
 * @author ian
 *
 */
@Entity
@NamedQueries({
    @NamedQuery(name = AppUser.FIND_ALL, query = "SELECT a FROM AppUser a")
})
@XmlRootElement
public class AppUser extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "AppUser.findAll";

    private static final JAXBContext jaxbContext = initJaxbContext(AppUser.class);

	private String password;

    private String realName;
    
    private String comments;

    // for hibernate
    public AppUser () {
    }

    // Copy constructor
    public AppUser (final AppUser appUser) {
		super(appUser);
		comments = appUser.comments;
		password = appUser.password;
		realName = appUser.realName;
	}

    @Override
	public JAXBContext getJaxbContext () {
		return jaxbContext;
	}

	public String getPassword () {
        return password;
    }

    public void setPassword (final String password) {
    	assert password != null;
        this.password = password;
    }

    public String getComments () {
        return comments;
    }

    public void setComments (final String comments) {
    	assert comments != null;
        this.comments = comments;
    }

    public String getRealName () {
        return realName;
    }

    public void setRealName (final String realName) {
    	assert realName != null;
        this.realName = realName;
    }
}
