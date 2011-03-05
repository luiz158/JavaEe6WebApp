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

import javax.validation.constraints.AssertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.me.doitto.webapp.dao.AbstractEntity;
import uk.me.doitto.webapp.dao.SimpleEntity;

/**
 * @author ian
 *
 */
public class EntityTest {
	
	// UUTY
	private SimpleEntity entity;
	
	private static final String NAME = "entityname";

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
		entity = new SimpleEntity(NAME);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructors () {
		SimpleEntity entity1 = new SimpleEntity();
		SimpleEntity entity2 = new SimpleEntity(entity1);
		assertNotSame("Same object!", entity2.getCreated(), entity1.getCreated());
		assertNotSame("Same object!", entity2.getAccessed(), entity1.getAccessed());
		assertNotSame("Same object!", entity2.getModified(), entity1.getModified());
	}

	@Test
	public void testGetName() {
		assertEquals("", NAME, entity.getName());
	}

	@Test
	public void testSetName() {
		String newName = "new-entity";
		assertEquals("", NAME, entity.getName());
		entity.setName(newName);
		assertEquals("", newName, entity.getName());
	}

	@Test
	public void testGetId() {
		assertNull("", entity.getId());
	}

	@Test
	public void testGetVersion() {
		assertEquals("", 0, entity.getVersion());
	}

	@Test
	public void testEntity () {
		SimpleEntity track = new SimpleEntity();
		assertNotSame("", this.entity, track);
		assertNull("", track.getName());
	}

	@Test
	public void testEntityString () {
		String name = "entity";
		SimpleEntity entity = new SimpleEntity(name);
		assertNotSame("", this.entity, entity);
		assertEquals("", name, entity.getName());
	}

	@Test
	public void testCompareToObject() {
		SimpleEntity track1 = new SimpleEntity("uapa");
		SimpleEntity track2 = new SimpleEntity("ubpa");
		SimpleEntity track3 = new SimpleEntity("uapb");
		SimpleEntity track4 = new SimpleEntity("ubpb");
		SimpleEntity track5 = new SimpleEntity("uapa");
		assertEquals("", 0, track1.compareTo(track1));
		assertEquals("", 0, track1.compareTo(track5));
		assertEquals("", 1, track3.compareTo(track1));
		assertEquals("", -1, track1.compareTo(track2));
		assertEquals("", -1, track1.compareTo(track3));
		assertEquals("", -1, track1.compareTo(track4));
	}

	@Test
	public void testEqualsObject() {
		SimpleEntity track1 = new SimpleEntity("uapa");
		SimpleEntity track2 = new SimpleEntity("ubpa");
		SimpleEntity track3 = new SimpleEntity("uapb");
		SimpleEntity track4 = new SimpleEntity("ubpb");
		SimpleEntity track5 = new SimpleEntity("uapa");
		assertEquals("", track1, track5);
		assertFalse("", track1.equals(track2));
		assertFalse("", track1.equals(track3));
		assertFalse("", track1.equals(track4));
		assertFalse("", track1.equals(null));
		assertFalse("", track1.equals(new Object()));
	}

	@Test
	public void testHashCode() {
		entity.hashCode();
	}
	
	@Test
	public void testToString() {
		entity.toString();
	}
	
	@Test
	public void testDeepCopy () {
		AbstractEntity entity = null;
		try {
			entity = this.entity.deepCopy();
		} catch (IOException e) {
			fail("Should not reach here!");
		} catch (ClassNotFoundException e) {
			fail("Should not reach here!");
		}
		assert entity != null;
		assertNotSame("Same object!", this.entity, entity);
		assertEquals("Wrong class!", entity.getClass(), this.entity.getClass());
	}
}
