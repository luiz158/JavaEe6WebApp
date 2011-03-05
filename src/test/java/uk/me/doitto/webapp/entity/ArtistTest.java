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

import static org.junit.Assert.*;

import java.io.IOException;

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
public class ArtistTest {
	
	// UUTY
	private Artist artist;
	
	private static final String NAME = "artistname";

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
		artist = new Artist(NAME);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructors () {
		Artist artist1 = new Artist();
		Artist artist2 = new Artist(artist1);
		assertNotSame("Same object!", artist2.getCreated(), artist1.getCreated());
		assertNotSame("Same object!", artist2.getAccessed(), artist1.getAccessed());
		assertNotSame("Same object!", artist2.getModified(), artist1.getModified());
	}

	@Test
	public void testGetName() {
		assertEquals("", NAME, artist.getName());
	}

	@Test
	public void testSetName() {
		String newName = "new-artist";
		assertEquals("", NAME, artist.getName());
		artist.setName(newName);
		assertEquals("", newName, artist.getName());
	}

	@Test
	public void testArtist () {
		Artist artist = new Artist();
		assertNotSame("", this.artist, artist);
		assertNull("", artist.getName());
	}

	@Test
	public void testArtistString () {
		String name = "artist";
		Artist artist = new Artist(name);
		assertNotSame("", this.artist, artist);
		assertEquals("", name, artist.getName());
	}

	@Test
	public void testEqualsObject() {
		Artist artist1 = new Artist("uapa");
		Artist artist2 = new Artist("ubpa");
		Artist artist3 = new Artist("uapb");
		Artist artist4 = new Artist("ubpb");
		Artist artist5 = new Artist("uapa");
		assertEquals("", artist1, artist5);
		assertFalse("", artist1.equals(artist2));
		assertFalse("", artist1.equals(artist3));
		assertFalse("", artist1.equals(artist4));
	}

	@Ignore
	@Test
	public void testHashCode() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testDeepCopy () {
		AbstractEntity artist = null;
		try {
			artist = this.artist.deepCopy();
		} catch (IOException e) {
			fail("Should not reach here!");
		} catch (ClassNotFoundException e) {
			fail("Should not reach here!");
		}
		assert artist != null;
		assertNotSame("Same object!", this.artist, artist);
		assertEquals("Wrong class!", artist.getClass(), this.artist.getClass());
	}
}
