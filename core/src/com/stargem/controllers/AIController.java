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
public class AIController extends AbstractControllerStrategy implements ControllerStrategy {

	private final AIBrain brain;
	
	/**
	 * @param entity
	 * @param component
	 */
	public AIController(Entity entity, Controller component) {
		super(entity, component);
		
		// create a new action list
		brain = new AIBrain();
		
		// populate the action list based on the script in the component
		// except send the name to the ai table in lua with the action list instance
		// that way all ai is data driven
		// the actual actions should be written with lua
		ScriptManager.getInstance().execute("ai", component.script, entity, brain);
		AIManager.getInstance().addBrain(entity, brain);
	}
}