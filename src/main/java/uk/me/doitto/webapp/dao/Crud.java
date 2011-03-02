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

package uk.me.doitto.webapp.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import javax.validation.constraints.NotNull;


/**
 * This Class is implemented as a non-generic class with generic methods, type is passed as a parameter, so clients may access more than one Dao simultaneously
 *
 * @param <T> Type parameter representing the entity type associated with this DAO
 * @author ian
 */
@SuppressWarnings("serial")
public abstract class Crud <T extends AbstractEntity> implements ICrud<T, Long> {
	
    public static final Logger LOGGER = Logger.getLogger("uk.me.doitto.jpadao");
    
    @NotNull
    @PersistenceContext
    private EntityManager em;
    
    @NotNull
    private final Class<T> type;

    protected Crud (final Class<T> type) {
        this.type = type;
    }

    /**
     * set the EntityManager for testing
     */
    protected Crud (final Class<T> type, final EntityManager em) {
        this(type);
        this.em = em;
    }

    /**
     * nb. package private
	 * @return the EntityManager for testing
	 */
	EntityManager getEm() {
		return em;
	}

	@Override
    public void create (@NotNull final T t) {
		LOGGER.log(Level.FINE, "create(" + t + ") " + type);
        em.persist(t);
    }

    @Override
    public void delete (@NotNull final Long id) {
    	LOGGER.log(Level.FINE, "delete(" + id + ") " + type);
        em.remove(find(id));
    }

    @Override
    public T update (@NotNull final T t) {
    	LOGGER.log(Level.FINE, "update(" + t + ") " + type);
        Date date = new Date();
        t.setModified(date);
        t.setAccessed(date);
        return em.merge(t);
    }

    @Override
    public T find (@NotNull final Long id) {
    	LOGGER.log(Level.FINE, "find(" + id + ") " + type);
        T t = em.find(type, id);
        if (t != null) {
            t.setAccessed(new Date());
        }
       return t;
    }

    @Override
    public List<T> findAll () {
    	LOGGER.log(Level.FINE, "findAll() " + type);
        CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(type);
        cq.select(cq.from(type));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<T> findByNamedQuery (@NotNull final String queryName, @NotNull final Map<String, Object> parameters, @NotNull int first, @NotNull int max) {
    	LOGGER.log(Level.FINE, "findByNamedQuery(" + queryName + ") " + type);
        TypedQuery<T> query = em.createNamedQuery(queryName, type);
        if (first > 0) {
            query.setFirstResult(first);
        }
        if (max > 0) {
            query.setMaxResults(max);
        }
        if (parameters != null) {
            for (Entry<String, Object> entry : parameters.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return query.getResultList();
    }

    @Override
	public List<T> before (final SingularAttribute<T, Date> attribute, final Date date) {
    	CriteriaBuilder builder = em.getCriteriaBuilder();
    	CriteriaQuery<T> query = builder.createQuery(type);
    	Root<T> root = query.from(type);
    	query.select(root).where(builder.lessThan(root.get(attribute), date));
    	return em.createQuery(query).getResultList();
    }
    
    @Override
	public List<T> since (final SingularAttribute<T, Date> attribute, final Date date) {
    	CriteriaBuilder builder = em.getCriteriaBuilder();
    	CriteriaQuery<T> query = builder.createQuery(type);
    	Root<T> root = query.from(type);
    	query.select(root).where(builder.greaterThan(root.get(attribute), date));
    	return em.createQuery(query).getResultList();
    }
    
    @Override
	public List<T> between (final SingularAttribute<T, Date> attribute, final Date date1, final Date date2) {
    	CriteriaBuilder builder = em.getCriteriaBuilder();
    	CriteriaQuery<T> query = builder.createQuery(type);
    	Root<T> root = query.from(type);
    	query.select(root).where(builder.between(root.get(attribute), date1, date2));
    	return em.createQuery(query).getResultList();
    }
    
	@Override
	public List<T> outside (final SingularAttribute<T, Date> attribute, final Date date1, final Date date2) {
    	CriteriaBuilder builder = em.getCriteriaBuilder();
    	CriteriaQuery<T> query = builder.createQuery(type);
    	Root<T> root = query.from(type);
    	query.select(root).where(builder.between(root.get(attribute), date1, date2).not());
    	return em.createQuery(query).getResultList();
    }

    @Override
	public List<T> search (final SingularAttribute<T, String> attribute, final String queryString) {
    	LOGGER.log(Level.FINE, "search() " + type);
    	CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);
        query.select(root).where(builder.like(root.get(attribute), "%" + queryString + "%"));
        return em.createQuery(query).getResultList();
    }
}
