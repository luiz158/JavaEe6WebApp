package uk.me.doitto.webapp.ws;

import uk.me.doitto.webapp.dao.AbstractEntity;

@SuppressWarnings("serial")
public abstract class RestCrudBase<T extends AbstractEntity> implements IRestCrud<T, Long> {
	
	protected abstract T overlay (final T incoming, final T existing);
}
