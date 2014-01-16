/**
 * 
 */
package com.stargem.entity;

import com.stargem.entity.components.Component;


/**
 * Entity.java
 *
 * @author 	Chris B
 * @date	25 Apr 2013
 * @version	1.0
 */
public class Entity {

	private final EntityManager em = EntityManager.getInstance();
	protected int id;
	
	public Entity() {
	}
	
	public <T extends Component> T getComponent(Class<T> componentType) {
		return em.getComponent(this, componentType);
	}
	
	public void free() {
		this.em.recycle(this);
	}

	public void setId(int id) {
		this.id = id;
		this.em.setEntityId(this, id);
	}
	
	public int getId() {
		return id;
	}
}