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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.me.doitto.webapp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author ian
 */
public class EmTest {

    private static EntityManagerFactory emf;

    private static EntityManager em;

    private EntityTransaction tx;
    
    // UUT
    private static SimpleDao dao;
    
    private SimpleEntity entity;
    
    private static final String TEST_STRING_1 = "Hello World!";

    private static final String TEST_STRING_2 = "Goodbye World!";

    @BeforeClass
    public static void setUpClass() throws Exception {
        emf = Persistence.createEntityManagerFactory("testPU");
        em = emf.createEntityManager();
        dao = new SimpleDao(em);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        em.close();
        emf.close();
    }

    @Before
    public void setUp() {
        tx = em.getTransaction();
        entity = new SimpleEntity();
        entity.setName(TEST_STRING_1);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class Dao.
     */
    @Test
    public void testEm () {
        assertTrue("Should be new!", entity.isNew());

        tx.begin();
        em.persist(entity);
        tx.commit();
        assertFalse("Should NOT be new!", entity.isNew());

        tx.begin();
        SimpleEntity s = em.find(SimpleEntity.class, entity.getId());
        tx.commit();
        assertEquals("Field should be equal", TEST_STRING_1, s.getName());
        assertEquals("Instance should be equal", entity, s);

        s.setName(TEST_STRING_2);
        tx.begin();
        em.merge(s);
        tx.commit();
        tx.begin();
        s = em.find(SimpleEntity.class, entity.getId());
        tx.commit();
        assertEquals(TEST_STRING_2, s.getName());

        tx.begin();
        em.remove(entity);
        tx.commit();
        tx.begin();
        SimpleEntity u = em.find(SimpleEntity.class, entity.getId());
        tx.commit();
        assertNull("Should be null!", u);
     }
    
    @Test
    public void testCrud () {
        assertTrue("", entity.isNew());

        tx.begin();
        dao.create(entity);
        tx.commit();
        assertFalse("", entity.isNew());

        tx.begin();
        SimpleEntity s = dao.find(entity.getId());
        tx.commit();
        assertNotNull("Should NOT be null!", s);
        assertEquals(TEST_STRING_1, s.getName());

        s.setName(TEST_STRING_2);
        tx.begin();
        dao.update(s);
        tx.commit();
        tx.begin();
        s = dao.find(entity.getId());
        tx.commit();
        assertNotNull("Should NOT be null!", s);
        assertEquals(TEST_STRING_2, s.getName());

        tx.begin();
        List<SimpleEntity> list1 = dao.findAll();
        tx.commit();
        assertEquals(1, list1.size());
        assertEquals(true, list1.contains(s));

        tx.begin();
        List<SimpleEntity> list2 = dao.findByNamedQuery(SimpleEntity.FIND_ALL, null, 0, 0);
        tx.commit();
        assertEquals(1, list2.size());
        assertEquals(true, list2.contains(s));

        tx.begin();
        dao.delete(s.getId());
        tx.commit();
        tx.begin();
        SimpleEntity u = dao.find(entity.getId());
        tx.commit();
        assertNull("Should be null!", u);
        tx.begin();
        List<SimpleEntity> list3 = dao.findAll();
        tx.commit();
        assertEquals(0, list3.size());
        assertEquals(false, list3.contains(s));
     }
    
    @Test
    public void testCriteriaQueries () {
        assertTrue("Should be new!", entity.isNew());

        tx.begin();
        em.persist(entity);
        tx.commit();
        assertFalse("Should NOT be new!", entity.isNew());

    	// Criteria builder
    	CriteriaBuilder builder = em.getCriteriaBuilder();
    	CriteriaQuery<SimpleEntity> query;
    	Root<SimpleEntity> root;

    	query = builder.createQuery(SimpleEntity.class);
    	root = query.from(SimpleEntity.class);
    	query.select(root).where(builder.equal(root.get(AbstractEntity_.name), TEST_STRING_1));
    	assertEquals(1, em.createQuery(query).getResultList().size());
    }

}
