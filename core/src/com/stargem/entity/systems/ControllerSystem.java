/**
 * 
 */
package com.stargem.entity.systems;

import com.stargem.entity.Entity;
import com.stargem.entity.components.Controller;
import com.stargem.scripting.ScriptManager;

/**
 * Run the controller script for the entity
 * 
 * Controller.java
 *
 * @author 	24233
 * @date	12 Oct 2013
 * @version	1.0
 */
public class ControllerSystem extends AbstractSystem {

	public ControllerSystem() {
		super();		
	}

	@Override
	public void process(float delta) {
		super.entities = em.getAllEntitiesPossessingComponent(Controller.class);
		super.process(delta);
	}

	@Override
	public void process(float delta, Entity entity) {
		Controller controller = em.getComponent(entity, Controller.class);
		ScriptManager.getInstance().execute("controllers", controller.controller, entity, delta);
	}
		
}