/**
 * 
 */
package com.stargem.entity;



/**
 * Entity.java
 * 
 * An entity contains only an ID. The ID must be set though the
 * Entity manager.
 * 
 * @author 	Chris B
 * @date	25 Apr 2013
 * @version	1.0
 */
public class Entity {

	protected int id;
	
	/**
	 * An entity contains only an ID. The ID must be set though the
	 * Entity manager.
	 */
	public Entity() {
	}
	
	/**
	 * Get the id of this entity
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}
}