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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;

import org.eclipse.persistence.internal.jpa.metamodel.CollectionAttributeImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import uk.me.doitto.webapp.dao.AbstractEntity;

/**
 * @author ian
 *
 */
public class AlbumTest {
	
	// UUTY
	private Album album;
	
	private static final String NAME = "albumname";

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		album = new Album();
		album.setName(NAME);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructors () {
		Album album1 = new Album();
		Album album2 = new Album(album1);
		assertNotSame("Same object!", album2.getCreated(), album1.getCreated());
		assertNotSame("Same object!", album2.getAccessed(), album1.getAccessed());
		assertNotSame("Same object!", album2.getModified(), album1.getModified());
	}

	@Test
	public void testGetName() {
		assertEquals("", NAME, album.getName());
	}

	@Test
	public void testSetName() {
		String newName = "new-album";
		assertEquals("", NAME, album.getName());
		album.setName(newName);
		assertEquals("", newName, album.getName());
	}

	@Test
	public void testalbum () {
		Album album = new Album();
		assertNotSame("", this.album, album);
		assertNull("", album.getName());
	}

	@Ignore
	@Test
	public void testEqualsObject() {
//		Album album1 = new Album("uapa");
//		Album album2 = new Album("ubpa");
//		Album album3 = new Album("uapb");
//		Album album4 = new Album("ubpb");
//		Album album5 = new Album("uapa");
//		assertEquals("", album1, album5);
//		assertFalse("", album1.equals(album2));
//		assertFalse("", album1.equals(album3));
//		assertFalse("", album1.equals(album4));
	}

	@Ignore
	@Test
	public void testHashCode() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testDeepCopy () {
		AbstractEntity album = null;
		try {
			album = this.album.deepCopy();
		} catch (IOException e) {
			fail("Should not reach here!");
		} catch (ClassNotFoundException e) {
			fail("Should not reach here!");
		}
		assert album != null;
		assertNotSame("Same object!", this.album, album);
		assertEquals("Wrong class!", album.getClass(), this.album.getClass());
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testMetaModel () {
		SingularAttribute<Album, Date> releaseDate = Album_.releaseDate;
		SingularAttribute<Album, String> label = Album_.label;
		SingularAttribute<Album, String> catId = Album_.catId;
		CollectionAttributeImpl<Album, Track> tracks = Album_.tracks;
	}
}
