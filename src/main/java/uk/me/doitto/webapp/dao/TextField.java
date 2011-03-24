/**
 * 
 */
package uk.me.doitto.webapp.dao;

import javax.persistence.metamodel.SingularAttribute;

/**
 * Maps each string date attribute name from an incoming web request to its corresponding JPA2 metamodel field
 * @author ian
 */
public enum TextField {
	name(AbstractEntity_.name);
	
	private final SingularAttribute<AbstractEntity, String> attribute;

	TextField (final SingularAttribute<AbstractEntity, String> attribute) {
		this.attribute = attribute;
	}
	
	/**
	 * @return the metamodel field for this enum instance
	 */
	public SingularAttribute<AbstractEntity, String> getValue () {
		return attribute;
	}
}
