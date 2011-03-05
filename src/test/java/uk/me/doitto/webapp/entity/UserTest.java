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
import org.junit.Test;

import uk.me.doitto.webapp.dao.AbstractEntity;

/**
 * @author ian
 *
 */
public class UserTest {
	
	// UUTY
	private AppUser user;
	
	private static final String USERNAME = "username";

	private static final String PASSWORD = "password";

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
		user = new AppUser(USERNAME, PASSWORD);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructors () {
		AppUser artist1 = new AppUser();
		AppUser artist2 = new AppUser(artist1);
		assertNotSame("Same object!", artist2.getCreated(), artist1.getCreated());
		assertNotSame("Same object!", artist2.getAccessed(), artist1.getAccessed());
		assertNotSame("Same object!", artist2.getModified(), artist1.getModified());
	}

	/**
	 * Test method for {@link uk.me.doitto.webapp.entity.AppUser#getName()}.
	 */
	@Test
	public void testgetName() {
		assertEquals("", USERNAME, user.getName());
	}

	/**
	 * Test method for {@link uk.me.doitto.webapp.entity.AppUser#setName(java.lang.String)}.
	 */
	@Test
	public void testsetName() {
		String newUserName = "new-user";
		assertEquals("", USERNAME, user.getName());
		user.setName(newUserName);
		assertEquals("", newUserName, user.getName());
	}

	/**
	 * Test method for {@link uk.me.doitto.webapp.entity.AppUser#getPassword()}.
	 */
//	@Test
	public void testGetPassword() {
		assertEquals("", PASSWORD, user.getPassword());
	}

	/**
	 * Test method for {@link uk.me.doitto.webapp.entity.AppUser#setPassword(java.lang.String)}.
	 */
//	@Test
	public void testSetPassword() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link uk.me.doitto.webapp.entity.AppUser#AppUser()}.
	 */
	@Test
	public void testUser() {
		AppUser appUser = new AppUser();
		assertNotSame("", user, appUser);
		assertNull("", appUser.getName());
		assertNull("", appUser.getPassword());
	}

	/**
	 * Test method for {@link uk.me.doitto.webapp.entity.AppUser#AppUser(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testUserStringString() {
		String username = "user";
		String password = "pass";
		AppUser appUser = new AppUser(username, password);
		assertNotSame("", user, appUser);
		assertEquals("", username, appUser.getName());
		assertEquals("", password, appUser.getPassword());
	}

	/**
	 * Test method for {@link uk.me.doitto.webapp.entity.AppUser#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		AppUser user1 = new AppUser("ua", "pa");
		AppUser user2 = new AppUser("ub", "pa");
		AppUser user3 = new AppUser("ua", "pb");
		AppUser user4 = new AppUser("ub", "pb");
		AppUser user5 = new AppUser("ua", "pa");
		assertEquals("", user1, user3);
		assertEquals("", user1, user5);
		assertFalse("", user1.equals(user2));
		assertFalse("", user1.equals(user4));
	}

	/**
	 * Test method for {@link uk.me.doitto.webapp.entity.AppUser#hashCode()}.
	 */
//	@Test
	public void testHashCode() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testDeepCopy () {
		AbstractEntity appUser = null;
		try {
			appUser = user.deepCopy();
		} catch (IOException e) {
			fail("Should not reach here!");
		} catch (ClassNotFoundException e) {
			fail("Should not reach here!");
		}
		assert appUser != null;
		assertNotSame("Same object!", this.user, appUser);
		assertEquals("Wrong class!", appUser.getClass(), this.user.getClass());
	}
}
