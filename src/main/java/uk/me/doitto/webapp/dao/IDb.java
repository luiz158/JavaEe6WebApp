package uk.me.doitto.webapp.dao;

import java.io.Serializable;

/**
 * Marker interface to ensure that PersistentEntity and ICrud have serializable keys
 * 
 * It also forces both type heirarchies to implement serializable (persistent entities and EJB services)
 * 
 * @author Ian Smith
 *
 * @param <PK> the type of the database primary keys
 */
public interface IDb<PK extends Serializable> extends Serializable {
}
