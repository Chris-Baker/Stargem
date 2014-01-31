/**
 * 
 */
package com.stargem.models;

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

	private final PhysicsManager physicsManager;
	private final EntityManager entityManager;
	
	private final PhysicsSystem physicsSystem;
	
	public Simulation() {
		
		// get a copy of each manager
		physicsManager 	= PhysicsManager.getInstance();
		entityManager 	= EntityManager.getInstance();
				
		// create all systems
		physicsSystem = new PhysicsSystem();
		
	}
	
	/* (non-Javadoc)
	 * @see com.stargem.models.Model#update(float)
	 */
	@Override
	public void update(float delta) {
		
		// update physics		
		physicsManager.stepSimulation(delta);
		
		// get list of entities in the local player's zone of control
		
		// update all systems
		physicsSystem.process(delta);
	}
	
}