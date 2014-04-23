/**
 * 
 */
package com.stargem.entity.systems;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.stargem.entity.Entity;
import com.stargem.entity.components.AISphericalSensor;
import com.stargem.entity.components.Physics;
import com.stargem.physics.PhysicsManager;


/**
 * PhysicsSystem.java
 *
 * @author 	Chris B
 * @date	17 Nov 2013
 * @version	1.0
 */
public class AISensorMovingSystem extends AbstractSystem {
	
	private final PhysicsManager physicsManager = PhysicsManager.getInstance();
	private final Matrix4 transform = new Matrix4();
	
	public AISensorMovingSystem() {
		super();
	}

	@Override
	public void process(float deltaTime) {
		super.entities = em.getAllEntitiesPossessingComponent(AISphericalSensor.class);
		super.process(deltaTime);
	}

	@Override
	public void process(float deltaTime, Entity entity) {
		
		AISphericalSensor sensor = em.getComponent(entity, AISphericalSensor.class);
		Physics physicsComponent = em.getComponent(entity, Physics.class);	
		if(physicsComponent == null) {
			return;
		}
		
		btRigidBody from = physicsManager.getRigidBody(physicsComponent.bodyIndex);
		btRigidBody to   = physicsManager.getRigidBody(sensor.bodyIndex);
		
		// get the world transform of the body
		from.getMotionState().getWorldTransform(transform);
		to.getMotionState().setWorldTransform(transform);
		to.setWorldTransform(transform);
	}
	
}