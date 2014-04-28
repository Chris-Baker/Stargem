/**
 * 
 */
package com.stargem.controllers;

import com.stargem.entity.Entity;
import com.stargem.entity.components.Controller;

/**
 * AbstractControllerStrategy.java
 *
 * @author 	Chris B
 * @date	22 Apr 2014
 * @version	1.0
 */
public abstract class AbstractControllerStrategy implements ControllerStrategy {

	protected final Entity entity;
	protected final Controller component;
	
	/**
	 * @param entity
	 * @param component
	 */
	public AbstractControllerStrategy(Entity entity, Controller component) {
		super();
		this.entity = entity;
		this.component = component;
	}
	
	@Override
	public void update(float delta) {
		// this can be overridden if the controller needs to be updated
	}
}