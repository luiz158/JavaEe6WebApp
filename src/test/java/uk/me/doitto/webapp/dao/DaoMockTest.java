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
package uk.me.doitto.webapp.dao;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.me.doitto.webapp.entity.SimpleEntity;

/**
 * @author ian
 *
 */
public class DaoMockTest extends EasyMockSupport {
	
	// UUT
	private SimpleDao dao;
	
	private SimpleEntity entity;
	
	private EntityManager mockEm;
	
	private TypedQuery<SimpleEntity> mockTq;
	
	private CriteriaQuery<SimpleEntity> mockCq;
	
	private CriteriaBuilder mockCb;
	
	private Root<SimpleEntity> mockRoot;
	
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
		mockEm = createStrictMock(EntityManager.class);
		mockCb = createStrictMock(CriteriaBuilder.class);
		mockCq = createStrictMock(CriteriaQuery.class);
		mockRoot = createStrictMock(Root.class);
		mockTq = createStrictMock(TypedQuery.class);
		dao = new SimpleDao(mockEm);
		entity = new SimpleEntity();
		entity.id = 1L;
		entity.version = 1;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link uk.me.doitto.webapp.dao.Crud#Crud(java.lang.Class)}.
	 */
	@Test
	public final void testCrudClassOfT() {
		SimpleDao daoClass = new SimpleDao();
		assertNotSame("", this.dao, daoClass);
		assertNull("Injected field should be null here!", daoClass.getEm());
	}

	/**
	 * Test method for {@link uk.me.doitto.webapp.dao.Crud#Crud(java.lang.Class, javax.persistence.EntityManager)}.
	 */
	@Test
	public final void testCrudClassOfTEntityManager() {
		SimpleDao daoClass = new SimpleDao(mockEm);
		assertNotSame("", this.dao, daoClass);
		assertSame("Injected field should be overridden by this constructor!", mockEm, daoClass.getEm());
	}

	/**
	 * Test method for {@link uk.me.doitto.webapp.dao.Crud#create(uk.me.doitto.webapp.dao.AbstractEntity)}.
	 */
	@Test
	public final void testCreate() {
		mockEm.persist(EasyMock.isA(SimpleEntity.class));
		replayAll();
		dao.create(entity);
		verifyAll();
	}

	/**
	 * Test method for {@link uk.me.doitto.webapp.dao.Crud#delete(java.lang.Long)}.
	 */
	@Test
	public final void testDelete() {
		expect(mockEm.find(EasyMock.eq(SimpleEntity.class), EasyMock.isA(Long.class))).andReturn(entity);
		mockEm.remove(EasyMock.isA(SimpleEntity.class));
		replayAll();
		dao.delete(entity.getId());
		verifyAll();
	}

	/**
	 * Test method for {@link uk.me.doitto.webapp.dao.Crud#update(uk.me.doitto.webapp.dao.AbstractEntity)}.
	 */
	@Test
	public final void testUpdate() {
		expect(mockEm.merge(EasyMock.isA(SimpleEntity.class))).andReturn(entity);
		replayAll();
		dao.update(entity);
		verifyAll();
	}

	/**
	 * Test method for {@link uk.me.doitto.webapp.dao.Crud#find(java.lang.Long)}.
	 */
	@Test
	public final void testFind() {
		expect(mockEm.find(EasyMock.eq(SimpleEntity.class), EasyMock.isA(Long.class))).andReturn(entity);
		replayAll();
		dao.find(entity.getId());
		verifyAll();
	}

	/**
	 * Test method for {@link uk.me.doitto.webapp.dao.Crud#findByNamedQuery(java.lang.String, java.util.Map, int, int)}.
	 */
	@Test
	public final void testFindByNamedQuery() {
		int firstResult = 1;
		int noOfResults = 100;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("p1", "v1");
//		parameters.put("p2", "v2");
		expect(mockEm.createNamedQuery(EasyMock.isA(String.class), EasyMock.eq(SimpleEntity.class))).andReturn(mockTq);
		expect(mockTq.setFirstResult(firstResult)).andReturn(mockTq);
		expect(mockTq.setMaxResults(noOfResults)).andReturn(mockTq);
		expect(mockTq.setParameter("p1", "v1")).andReturn(mockTq);
//		expect(mockTq.setParameter("p2", "v2")).andReturn(mockTq);
		expect(mockTq.getResultList()).andReturn(new ArrayList<SimpleEntity>());
		replayAll();
		dao.findByNamedQuery("query string", parameters, firstResult, noOfResults);
		verifyAll();
	}

	/**
	 * Test method for {@link uk.me.doitto.webapp.dao.Crud#findAll()}.
	 */
	@Test
	public final void testFindAll() {
		expect(mockEm.getCriteriaBuilder()).andReturn(mockCb);
		expect(mockCb.createQuery(SimpleEntity.class)).andReturn(mockCq);
		expect(mockCq.from(SimpleEntity.class)).andReturn(mockRoot);
		expect(mockCq.select(mockRoot)).andReturn(mockCq);
		expect(mockEm.createQuery(EasyMock.isA(CriteriaQuery.class))).andReturn(mockTq);
		expect(mockTq.getResultList()).andReturn(new ArrayList<SimpleEntity>());
		replayAll();
		dao.findAll();
		verifyAll();
	}
}
