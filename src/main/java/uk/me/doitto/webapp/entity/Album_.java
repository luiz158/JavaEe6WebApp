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

package uk.me.doitto.webapp.entity;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import org.eclipse.persistence.internal.jpa.metamodel.CollectionAttributeImpl;

import uk.me.doitto.webapp.dao.AbstractEntity;

@Generated("EclipseLink-2.0.1.v20100213-r6600 @ Sat Nov 27 13:00:49 GMT 2010")
@StaticMetamodel(Album.class)
public class Album_ extends AbstractEntity {

	public static volatile SingularAttribute<Album, Date> releaseDate;
	public static volatile SingularAttribute<Album, String> label;
	public static volatile SingularAttribute<Album, String> catId;
	public static volatile CollectionAttributeImpl<Album, Track> tracks;

}