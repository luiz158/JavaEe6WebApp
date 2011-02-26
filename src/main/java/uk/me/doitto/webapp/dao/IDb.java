package uk.me.doitto.webapp.dao;

import java.io.Serializable;

/**
 * Marker interface to ensure that PersistentEntity and ICrud have serializable keys
 * @author super
 *
 * @param <PK> the type of the database primary keys
 */
public interface IDb<PK extends Serializable> {
}
