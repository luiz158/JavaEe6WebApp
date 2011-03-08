package uk.me.doitto.webapp.ws;

import uk.me.doitto.webapp.dao.AbstractEntity;

/**
 * Specializes IRestCrud for Long PKs, specifies an overlay method to selectively allow editing over REST
 * @author super
 *
 * @param <T> the entity type
 */
@SuppressWarnings("serial")
public abstract class RestCrudBase<T extends AbstractEntity> implements IRestCrud<T, Long> {
	
	protected abstract T overlay (final T incoming, final T existing);
}
