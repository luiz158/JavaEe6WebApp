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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;

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
public class TrackTest {
	
	// UUTY
	private Track track;
	
	private static final String NAME = "trackname";

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
		track = new Track(NAME);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructors () {
		Track track1 = new Track();
		Track track2 = new Track(track1);
		assertNotSame("Same object!", track2.getCreated(), track1.getCreated());
		assertNotSame("Same object!", track2.getAccessed(), track1.getAccessed());
		assertNotSame("Same object!", track2.getModified(), track1.getModified());
	}

	@Test
	public void testGetName() {
		assertEquals("", NAME, track.getName());
	}

	@Test
	public void testSetName() {
		String newName = "new-track";
		assertEquals("", NAME, track.getName());
		track.setName(newName);
		assertEquals("", newName, track.getName());
	}

	@Test
	public void testtrack () {
		Track track = new Track();
		assertNotSame("", this.track, track);
		assertNull("", track.getName());
	}

	@Test
	public void testtrackString () {
		String name = "track";
		Track track = new Track(name);
		assertNotSame("", this.track, track);
		assertEquals("", name, track.getName());
	}

	@Test
	public void testEqualsObject() {
		Track track1 = new Track("uapa");
		Track track2 = new Track("ubpa");
		Track track3 = new Track("uapb");
		Track track4 = new Track("ubpb");
		Track track5 = new Track("uapa");
		assertEquals("", track1, track5);
		assertFalse("", track1.equals(track2));
		assertFalse("", track1.equals(track3));
		assertFalse("", track1.equals(track4));
	}

	@Ignore
	@Test
	public void testHashCode() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testDeepCopy () {
		AbstractEntity track = null;
		try {
			track = this.track.deepCopy();
		} catch (IOException e) {
			fail("Should not reach here!");
		} catch (ClassNotFoundException e) {
			fail("Should not reach here!");
		}
		assert track != null;
		assertNotSame("Same object!", this.track, track);
		assertEquals("Wrong class!", track.getClass(), this.track.getClass());
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testMetaModel () {
		SingularAttribute<Track, Integer> duration = Track_.duration;
		SingularAttribute<Track, String> url = Track_.url;
	}
}
