/**
 * 
 */
package com.stargem.controllers;

import com.stargem.entity.Entity;
import com.stargem.entity.components.Controller;

/**
 * NetworkController.java
 *
 * @author 	Chris B
 * @date	25 Apr 2014
 * @version	1.0
 */
public class NetworkController extends AbstractControllerStrategy {

	/**
	 * @param entity
	 * @param component
	 */
	public NetworkController(Entity entity, Controller component) {
		super(entity, component);
	}

	@Override
	public void update(float delta) {
		// empty the queue of updates for this entity
		// set updated component values
		// lookup what component type to update then do the updates with reflection
		// this is nice and easy because we can send the message like
		//  - component id as an integer
		//  - for each field 1 bit to flag whether the field has updated
		//  - if yes then the length in bytes follows as an integer
		//  - then the data
		//  - if not then move onto the next flag bit
		// this scheme is based on quake 3 delta compression of messages
	}
	
}