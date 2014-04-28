/**
 * 
 */
package com.stargem.models;

import com.stargem.Config;
import com.stargem.controllers.ControllerManager;
import com.stargem.entity.systems.AutoSaveSystem;
import com.stargem.entity.systems.ControllerSystem;
import com.stargem.entity.systems.LightMovingSystem;
import com.stargem.entity.systems.ParentSystem;
import com.stargem.entity.systems.PhysicsSystem;
import com.stargem.entity.systems.TimerSystem;
import com.stargem.entity.systems.WeaponSystem;
import com.stargem.physics.PhysicsManager;

/**
 * Simulation.java
 *
 * @author 	Chris B
 * @date	19 Nov 2013
 * @version	1.0
 */
public class Simulation implements Model {

	private final PhysicsManager physicsManager;
	private final ControllerManager controllerManager;
	
	private final PhysicsSystem physicsSystem;
	private final ControllerSystem keyboardMouseSystem;
	private final AutoSaveSystem autoSaveSystem;
	private final TimerSystem timerSystem;
	private final LightMovingSystem lightMovingSystem;
	private final ParentSystem parentSystem;
	private final WeaponSystem weaponSystem;
	
	public Simulation() {
		
		// get a copy of the physics manager so we can step the simulation each tick
		physicsManager 	= PhysicsManager.getInstance();
		controllerManager = ControllerManager.getInstance();
		
		// create all systems
		physicsSystem = new PhysicsSystem();
		keyboardMouseSystem = new ControllerSystem();
		autoSaveSystem = new AutoSaveSystem(Config.AUTO_SAVE_FREQUENCY);
		timerSystem = new TimerSystem();
		lightMovingSystem = new LightMovingSystem();
		parentSystem = new ParentSystem();
		weaponSystem = new WeaponSystem();
	}
	
	/* (non-Javadoc)
	 * @see com.stargem.models.Model#update(float)
	 */
	@Override
	public void update(float delta) {
		
		// update weapon heat and ready status
		weaponSystem.process(delta);
		
		// move all child entities to their parents
		parentSystem.process(delta);
		
		// move all lights which are coupled with physics components
		lightMovingSystem.process(delta);
		
		// update all timer components
		timerSystem.process(delta);
		
		// get list of entities in the local player's zone of control
		
		// get player input
		keyboardMouseSystem.process(delta);
		
		// get network updates
		
		// update all controllers player, ai, network
		controllerManager.update(delta);
		
		// update physics simulation	
		physicsManager.stepSimulation(delta);
		
		// save the physics tick to the physics components
		physicsSystem.process(delta);
		
		// store the state of the world
		// this is a timed event that periodically happens
		//autoSaveSystem.process(delta);
	}
	
}