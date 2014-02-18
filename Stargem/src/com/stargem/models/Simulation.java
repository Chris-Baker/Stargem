/**
 * 
 */
package com.stargem.models;

import com.stargem.GameManager;
import com.stargem.PlayersManager;
import com.stargem.entity.EntityManager;
import com.stargem.entity.systems.KeyboardMouseSystem;
import com.stargem.entity.systems.PhysicsSystem;
import com.stargem.physics.PhysicsManager;

/**
 * Simulation.java
 *
 * @author 	Chris B
 * @date	19 Nov 2013
 * @version	1.0
 */
public class Simulation implements Model {

	private final GameManager gameManager;
	private final PhysicsManager physicsManager;
	private final EntityManager entityManager;
	private final PlayersManager playersManager;
	
	private final PhysicsSystem physicsSystem;
	private final KeyboardMouseSystem keyboardMouseSystem;
	
	public Simulation() {
		
		// get a copy of each manager
		gameManager 	= GameManager.getInstance();
		physicsManager 	= PhysicsManager.getInstance();
		entityManager 	= EntityManager.getInstance();
		playersManager	= PlayersManager.getInstance();
		
		// create all systems
		physicsSystem = new PhysicsSystem();
		keyboardMouseSystem = new KeyboardMouseSystem();
	}
	
	/* (non-Javadoc)
	 * @see com.stargem.models.Model#update(float)
	 */
	@Override
	public void update(float delta) {
				
		// get list of entities in the local player's zone of control
				
		// get player input
		keyboardMouseSystem.process(delta);
		
		// update ai
		
		// get network updates
				
		// update physics simulation	
		physicsManager.stepSimulation(delta);
		
		// save the physics tick to the physics components
		physicsSystem.process(delta);
		
		// store the state of the world
	}
	
}