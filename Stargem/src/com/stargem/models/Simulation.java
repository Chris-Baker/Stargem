/**
 * 
 */
package com.stargem.models;

import com.stargem.GameManager;
import com.stargem.PlayersManager;
import com.stargem.controllers.KeyboardMouseController;
import com.stargem.entity.EntityManager;
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
	
	// the player controller
	private final KeyboardMouseController playerController;
		
	public Simulation() {
		
		// get a copy of each manager
		gameManager 	= GameManager.getInstance();
		physicsManager 	= PhysicsManager.getInstance();
		entityManager 	= EntityManager.getInstance();
		playersManager	= PlayersManager.getInstance();
		
		// create all systems
		physicsSystem = new PhysicsSystem();
		
		// attach the controller to the local player
		playerController = new KeyboardMouseController(playersManager.getLocalPlayer());
	}
	
	/* (non-Javadoc)
	 * @see com.stargem.models.Model#update(float)
	 */
	@Override
	public void update(float delta) {
		
		// update physics simulation	
		physicsManager.stepSimulation(delta);
		
		// get list of entities in the local player's zone of control
		
		// update all systems
		physicsSystem.process(delta);
	}
	
}