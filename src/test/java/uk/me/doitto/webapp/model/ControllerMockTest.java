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
package uk.me.doitto.webapp.model;

import java.util.ArrayList;

import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.me.doitto.webapp.beans.EntityService;
import uk.me.doitto.webapp.dao.SimpleEntity;

/**
 * @author ian
 *
 */
public class ControllerMockTest extends EasyMockSupport {
	
	// UUT
	EntityController controller;
	
	EntityService mockService;

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
		mockService = createStrictMock(EntityService.class);
		controller = new EntityController();
		controller.setService(mockService);
		controller.setItem(new SimpleEntity());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link uk.me.doitto.webapp.model.EntityController#jsfList()}.
	 */
	@Test
	public final void testList() {
		controller.jsfList();
	}

	/**
	 * Test method for {@link uk.me.doitto.webapp.model.EntityController#jsfSave()}.
	 */
	@Test
	public final void testSave() {
		controller.jsfSave();
	}

	/**
	 * Test method for {@link uk.me.doitto.webapp.model.EntityController#jsfCreate()}.
	 */
	@Test
	public final void testCreate() {
		controller.jsfCreate();
	}

	/**
	 * Test method for {@link uk.me.doitto.webapp.model.EntityController#jsfEdit(java.lang.Long)}.
	 */
	@Test
	public final void testEdit() {
		controller.jsfEdit(0L);
	}

	/**
	 * Test method for {@link uk.me.doitto.webapp.model.EntityController#jsfDelete(java.lang.Long)}.
	 */
	@Test
	public final void testDelete() {
//		org.easymock.EasyMock.expect(mockService.find(EasyMock.isA(Long.class)));
//		mockService.delete(EasyMock.isA(Long.class));
//		replayAll();
		controller.jsfDelete(0L);
//		verifyAll();
	}

	/**
	 * Test method for {@link uk.me.doitto.webapp.model.ControllerBase#getItem()}.
	 */
	@Test
	public final void testGetItem() {
		controller.getItem();
	}

	/**
	 * Test method for {@link uk.me.doitto.webapp.model.ControllerBase#setItem(uk.me.doitto.webapp.dao.AbstractEntity)}.
	 */
	@Test
	public final void testSetItem() {
		controller.setItem(new SimpleEntity());
	}

	/**
	 * Test method for {@link uk.me.doitto.webapp.model.ControllerBase#getItems()}.
	 */
	@Test
	public final void testGetItems() {
		controller.getItems();
	}

	/**
	 * Test method for {@link uk.me.doitto.webapp.model.ControllerBase#setItems(java.util.List)}.
	 */
	@Test
	public final void testSetItems() {
		ArrayList<SimpleEntity> items = new ArrayList<SimpleEntity>();
		items.add(new SimpleEntity());
		controller.setItems(items);
	}

}
