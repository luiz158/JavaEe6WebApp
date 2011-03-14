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
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
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
	/**
	 * Persistence Units - refer to src/test/resources/META-INF/persistence.xml for members
	 * @author ian
	 */
	private enum TestPU {
		memoryPU, filesystemPU;
	}

    private static EntityManagerFactory emf;

    private static EntityManager em;

    private EntityTransaction tx;
    
    // UUT
    private static SimpleDao dao;
    
    private SimpleEntity entity;
    
    private int entityCount;
    
    private static final String TEST_STRING_1 = "Hello World!";

    private static final String TEST_STRING_2 = "Goodbye World!";
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        emf = Persistence.createEntityManagerFactory(TestPU.memoryPU.toString());
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
        entityCount = dao.count();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testEmStarted () {
    }
    
    /**
     * Test of create method, of class Dao.
     */
    @Test
    public void testEmSanity () {
        assertTrue("Should be new!", entity.isNew());

        tx.begin();
        em.persist(entity);
        tx.commit();
        assertFalse("Should NOT be new!", entity.isNew());
        assertTrue("EM should contain " + entity, em.contains(entity));

        tx.begin();
        SimpleEntity simpleEntity = em.find(SimpleEntity.class, entity.getId());
        tx.commit();
        assertEquals("Field should be equal", TEST_STRING_1, simpleEntity.getName());
        assertEquals("Instance should be equal", entity, simpleEntity);

        simpleEntity.setName(TEST_STRING_2);
        tx.begin();
        em.merge(simpleEntity);
        tx.commit();
        tx.begin();
        simpleEntity = em.find(SimpleEntity.class, entity.getId());
        tx.commit();
        assertEquals(TEST_STRING_2, simpleEntity.getName());

        tx.begin();
        em.remove(entity);
        tx.commit();
        tx.begin();
        SimpleEntity u = em.find(SimpleEntity.class, entity.getId());
        tx.commit();
        assertNull("Should be null!", u);
        assertFalse("EM should NOT contain " + entity, em.contains(entity));
     }
    
    @Test
    public void testDeepCopy () {
        assertTrue("Should be new!", entity.isNew());

        tx.begin();
        em.persist(entity);
        tx.commit();
        assertFalse("Should NOT be new!", entity.isNew());
        assertTrue("EM should contain " + entity, em.contains(entity));
        
		AbstractEntity newEntity = null;
		try {
			newEntity = entity.deepCopy();
		} catch (IOException e) {
			fail("Should not reach here!");
		} catch (ClassNotFoundException e) {
			fail("Should not reach here!");
		}
		assert newEntity != null;
		assertEquals("Wrong class!", newEntity.getClass(), entity.getClass());
		assertNotSame("Same object!", entity, newEntity);
		assertNotSame("Same object!", entity.getId(), newEntity.getId());
		assertNotSame("Same object!", entity.getCreated(), newEntity.getCreated());
		assertNotSame("Same object!", entity.getModified(), newEntity.getModified());
		assertNotSame("Same object!", entity.getAccessed(), newEntity.getAccessed());
    }

    @Test
    public void testCrud () {
        assertTrue("", entity.isNew());

        tx.begin();
        dao.create(entity);
        tx.commit();
        assertFalse("Should be new", entity.isNew());
        assertTrue("EM should contain " + entity, em.contains(entity));

        tx.begin();
        SimpleEntity simpleEntity = dao.find(entity.getId());
        tx.commit();
        assertNotNull("Should NOT be null!", simpleEntity);
        assertEquals(TEST_STRING_1, simpleEntity.getName());

        simpleEntity.setName(TEST_STRING_2);
        tx.begin();
        dao.update(simpleEntity);
        tx.commit();
        tx.begin();
        simpleEntity = dao.find(entity.getId());
        tx.commit();
        assertNotNull("Should NOT be null!", simpleEntity);
        assertEquals(TEST_STRING_2, simpleEntity.getName());

        tx.begin();
        List<SimpleEntity> list1 = dao.findAll();
        tx.commit();
    	assertEquals("Should be " + (entityCount + 1) + " instances", entityCount + 1, list1.size());
        assertEquals(true, list1.contains(simpleEntity));

        tx.begin();
        List<SimpleEntity> list2 = dao.findByNamedQuery(SimpleEntity.FIND_ALL, new HashMap<String, Object>());
        tx.commit();
    	assertEquals("Should be " + (entityCount + 1) + " instances", entityCount + 1, list2.size());
        assertEquals(true, list2.contains(simpleEntity));

        tx.begin();
        dao.delete(simpleEntity.getId());
        tx.commit();
        tx.begin();
        SimpleEntity u = dao.find(entity.getId());
        tx.commit();
        assertNull("Should be null!", u);
        assertFalse("EM should NOT contain " + entity, em.contains(entity));
        tx.begin();
        List<SimpleEntity> list3 = dao.findAll();
        tx.commit();
    	assertEquals("Should be " + entityCount + " instances", entityCount, list3.size());
        assertEquals(false, list3.contains(simpleEntity));
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
//    	assertEquals("", entityCount + 1, em.createQuery(query).getResultList().size());
    	assertEquals("Should be " + 2 + " instances", 2, em.createQuery(query).getResultList().size());
    }

    @Test
    public void testCount () {
    	assertEquals("Should be " + entityCount + " instances", entityCount, dao.count());
    	int number = 7;
        tx.begin();
    	for (int i = 0; i < number; i++) {
    		SimpleEntity entity = new SimpleEntity();
    		entity.setName("TestCount" + i);
            em.persist(entity);
    	}
        tx.commit();
        int newCount = entityCount + number;
    	assertEquals("Should be " + newCount + " instances", newCount, dao.count());
    }

    @Test
    public void testFind () {
    	assertEquals("Should be " + entityCount + " instances", entityCount, dao.count());
    	final int number = 11;
        tx.begin();
    	for (int i = 0; i < number; i++) {
    		SimpleEntity entity = new SimpleEntity();
    		entity.setName("TestFindRange" + i);
            em.persist(entity);
    	}
        tx.commit();
        final int newCount = entityCount + number;
    	assertEquals("Should be " + newCount + " instances", newCount, dao.count());
    	// find all
    	assertEquals("Should be " + newCount + " instances", newCount, dao.findAll().size());
    	assertEquals("Should be " + newCount + " instances", newCount, dao.findAll(0, dao.count()).size());
    	assertEquals("Should be " + newCount + " instances", newCount, dao.findByNamedQuery(SimpleEntity.FIND_ALL, new HashMap<String, Object>(), 0, 0).size());
    	// find first number
    	assertEquals("Should be " + number + " instances", number, dao.findAll(0, number).size());
    	assertEquals("Should be " + number + " instances", number, dao.findByNamedQuery(SimpleEntity.FIND_ALL, new HashMap<String, Object>(), 0, number).size());
    	// find last number
    	assertEquals("Should be " + number + " instances", number, dao.findAll(dao.count() - number, number).size());
    	assertEquals("Should be " + number + " instances", number, dao.findByNamedQuery(SimpleEntity.FIND_ALL, new HashMap<String, Object>(), dao.count() - number, number).size());
    }
    
    @Test
    public void testDates () throws InterruptedException {
        SimpleEntity entityA = new SimpleEntity();
        entityA.setName("Entity A");
        tx.begin();
        em.persist(entityA);
        tx.commit();
        
        Thread.sleep(100);
        Date date1 = new Date();
        Thread.sleep(100);
        
        SimpleEntity entityB = new SimpleEntity();
        entityB.setName("Entity B");
        tx.begin();
        em.persist(entityB);
        tx.commit();
        
        Thread.sleep(100);        
        Date date2 = new Date();
        Thread.sleep(100);
        
        SimpleEntity entityC = new SimpleEntity();
        entityC.setName("Entity C");
        tx.begin();
        em.persist(entityC);
        tx.commit();
        
        // Date 1
        assertTrue("Wrong contents! ", dao.before(AbstractEntity_.created, date1).contains(entityA));
        assertFalse("Wrong contents! ", dao.before(AbstractEntity_.accessed, date1).contains(entityB) || dao.before(AbstractEntity_.modified, date1).contains(entityC));
        assertTrue("Wrong contents! ", dao.since(AbstractEntity_.created, date1).contains(entityB) && dao.since(AbstractEntity_.modified, date1).contains(entityC));
        assertFalse("Wrong contents! ", dao.since(AbstractEntity_.accessed, date1).contains(entityA));
        // Date 2
        assertTrue("Wrong contents! ", dao.before(AbstractEntity_.created, date2).contains(entityA) && dao.before(AbstractEntity_.modified, date2).contains(entityB));
        assertFalse("Wrong contents! ", dao.before(AbstractEntity_.accessed, date2).contains(entityC));
        assertTrue("Wrong contents! ", dao.since(AbstractEntity_.created, date2).contains(entityC));
        assertFalse("Wrong contents! ", dao.since(AbstractEntity_.accessed, date2).contains(entityA) || dao.since(AbstractEntity_.modified, date2).contains(entityB));
        // Both dates
        assertTrue("Wrong contents! ", dao.during(AbstractEntity_.created, date1, date2).contains(entityB));
        assertFalse("Wrong contents! ", dao.during(AbstractEntity_.accessed, date1, date2).contains(entityA) || dao.during(AbstractEntity_.modified, date1, date2).contains(entityC));
        assertTrue("Wrong contents! ", dao.notDuring(AbstractEntity_.created, date1, date2).contains(entityA) && dao.notDuring(AbstractEntity_.modified, date1, date2).contains(entityC));
        assertFalse("Wrong contents! ", dao.notDuring(AbstractEntity_.accessed, date1, date2).contains(entityB));
    }
    
    @Test
    public void testSearch () {
    	assertEquals("Should be " + 18 + " instances", 18, dao.search(AbstractEntity_.name, "Test%").size());
    	assertEquals("Should be " + 7 + " instances", 7, dao.search(AbstractEntity_.name, "%Count").size());
    	assertEquals("Should be " + 11 + " instances", 11, dao.search(AbstractEntity_.name, "%FindRange").size());
    }
}
