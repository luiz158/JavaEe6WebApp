package uk.me.doitto.webapp.dao

import java.util.Date
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Version
import javax.persistence.MappedSuperclass
import javax.xml.bind.annotation.XmlElement

//@SuppressWarnings("serial")
@MappedSuperclass
abstract class BaseEntity () {
	/**
	 * For use by persistence provider
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlElement
	var id : Long

	/**
	 * For use by persistence provider
	 */
	@Version
	@XmlElement
	var version : Int
	
	@XmlElement
	var name : String
	
	@XmlElement
	val created = new Date()
	
	@XmlElement
	var modified = created
	
	@XmlElement
	var accessed = created
	
	def this (name : String) = {
        this()
        this.name = name
    }

}