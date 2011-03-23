/**
 * 
 */
package uk.me.doitto.webapp.dao;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;

/**
 * Maps each string date attribute name from an incoming web request to its corresponding JPA2 metamodel field
 * @author ian
 */
public enum TimeStamp {
	created(AbstractEntity_.created),
	modified(AbstractEntity_.modified),
	accessed(AbstractEntity_.accessed);
	
	private final SingularAttribute<AbstractEntity, Date> attribute;

	TimeStamp (final SingularAttribute<AbstractEntity, Date> attribute) {
		this.attribute = attribute;
	}
	
	/**
	 * @return the metamodel field for this enum instance
	 */
	public SingularAttribute<AbstractEntity, Date> getValue () {
		return attribute;
	}
}
