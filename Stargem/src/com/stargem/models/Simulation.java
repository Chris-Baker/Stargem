/**
 * 
 */
package com.stargem.models;

import com.stargem.Config;
import com.stargem.entity.systems.AutoSaveSystem;
import com.stargem.entity.systems.KeyboardMouseSystem;
import com.stargem.entity.systems.PhysicsSystem;
import com.stargem.entity.systems.TimerSystem;
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
	
	private final PhysicsSystem physicsSystem;
	private final KeyboardMouseSystem keyboardMouseSystem;
	private final AutoSaveSystem autoSaveSystem;
	private final TimerSystem timerSystem;
	
	public Simulation() {
		
		// get a copy of the physics manager so we can step the simulation each tick
		physicsManager 	= PhysicsManager.getInstance();
		
		// create all systems
		physicsSystem = new PhysicsSystem();
		keyboardMouseSystem = new KeyboardMouseSystem();
		autoSaveSystem = new AutoSaveSystem(Config.AUTO_SAVE_FREQUENCY);
		timerSystem = new TimerSystem();
	}
	
	/* (non-Javadoc)
	 * @see com.stargem.models.Model#update(float)
	 */
	@Override
	public void update(float delta) {
		
		// update the auto save system
		//autoSaveSystem.process(delta);
		
		// update all timer components
		timerSystem.process(delta);
		
		// get list of entities in the local player's zone of control
		
		// get player input
		keyboardMouseSystem.process(delta);
		
		// get network updates
		
		// update ai
				
		// update physics simulation	
		physicsManager.stepSimulation(delta);
		
		// save the physics tick to the physics components
		physicsSystem.process(delta);
		
		// store the state of the world
	}
	
}