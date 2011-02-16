/**
 * 
 */
package uk.me.doitto.webapp.dao;

import java.util.Date
import java.util.List

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root
import javax.persistence.metamodel.SingularAttribute
import javax.validation.constraints.NotNull

/**
 * @author super
 *
 */
abstract class Dao [T <: BaseEntity] (typeToken : Class[T]) {
	
    @NotNull
    @PersistenceContext
    var em : EntityManager

    /**
     * set the EntityManager for testing
     */
    def this (typeToken : Class[T], em : EntityManager) = {
        this(typeToken)
        this.em = em
    }


    def create (t : T) : Unit = {
    	em.persist(t)
    }
    
    def delete (id : Long) : Unit = {
    	em.remove(find(id))
    }
    
    def update (t : T) : T = {
        val date = new Date();
        t.modified = date
        t.accessed = date
    	em.merge(t)
    }
    
    def find (id : Long) : T = {
        val t = em.find(typeToken, id);
        if (t != null) {
            t.accessed = new Date()
        }
        return t
    }
    
    def findAll () : List[T] = {
        val cq = em.getCriteriaBuilder().createQuery(typeToken);
        cq.select(cq.from(typeToken));
        em.createQuery(cq).getResultList();
    }
    
//    def before (attribute : SingularAttribute[T, Date], date : Date) : List[T] = {
//    	val builder = em.getCriteriaBuilder()
//    	val query : CriteriaQuery[T] = builder.createQuery(typeToken)
//    	val root : Root[T] = query.from(typeToken)
//    	query.select(root).where(builder.lessThan(root.get(attribute), date))
//    	em.createQuery(query).getResultList()
//    }
}