/**
 * 
 */
package com.stargem.controllers;

import com.stargem.ai.AIBrain;
import com.stargem.ai.AIManager;
import com.stargem.entity.Entity;
import com.stargem.entity.components.Controller;
import com.stargem.scripting.ScriptManager;

/**
 * AIController.java
 *
 * @author 	Chris B
 * @date	21 Mar 2014
 * @version	1.0
 */
public class AIController extends AbstractControllerStrategy {

	private final AIBrain brain;
	
	/**
	 * @param entity
	 * @param component
	 */
	public AIController(Entity entity, Controller component) {
		super(entity, component);
		
		// create a new action list
		brain = new AIBrain();
		
		// add the brain to the ai manager's list of brains
		AIManager.getInstance().addBrain(entity.getId(), brain);
		
		// populate the brain based on the behaviour script referenced in the component
		ScriptManager.getInstance().execute("behaviour", component.behaviour, entity, brain);
	}
	
	@Override
	public void update(float delta) {
		this.brain.update(delta);
	}
	
}