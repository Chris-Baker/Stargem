/**
 * 
 */
package com.stargem.entity.systems;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.stargem.entity.Entity;
import com.stargem.entity.components.Physics;
import com.stargem.physics.MotionState;
import com.stargem.physics.PhysicsManager;


/**
 * PhysicsSystem.java
 *
 * @author 	Chris B
 * @date	17 Nov 2013
 * @version	1.0
 */
public class PhysicsSystem extends AbstractSystem {
	
	private final PhysicsManager physicsManager = PhysicsManager.getInstance();
	private final Matrix4 transform = new Matrix4();
	
	public PhysicsSystem() {
		super();
	}

	@Override
	public void process(float deltaTime) {
		super.entities = em.getAllEntitiesPossessingComponent(Physics.class);
		super.process(deltaTime);
	}

	@Override
	public void process(float deltaTime, Entity entity) {
		Physics physicsComponent = em.getComponent(entity, Physics.class);
		MotionState motionState = physicsManager.getMotionState(physicsComponent.bodyIndex);		
		btRigidBody body = physicsManager.getRigidBody(physicsComponent.bodyIndex);
		
		// the activation state can change without the motion state being updated
		physicsComponent.activationState = body.getActivationState();
		
		// update the component's fields if the physics body has been updated.
		if(motionState.isUpdated() || physicsComponent.type == PhysicsManager.CHARACTER) {	
			// get the world transform of the body
			motionState.getWorldTransform(transform);
			
			// update all fields which could have changed through some motion			
			physicsComponent.angluarVelocityX = body.getAngularVelocity().x;
			physicsComponent.angluarVelocityY = body.getAngularVelocity().y;
			physicsComponent.angluarVelocityZ = body.getAngularVelocity().z;
			
			physicsComponent.linearVelocityX = body.getLinearVelocity().x;
			physicsComponent.linearVelocityY = body.getLinearVelocity().y;
			physicsComponent.linearVelocityZ = body.getLinearVelocity().z;
			
			physicsComponent.gravityX = body.getGravity().x;
			physicsComponent.gravityX = body.getGravity().y;
			physicsComponent.gravityX = body.getGravity().z;
			
			physicsComponent.m00 = transform.val[0];
			physicsComponent.m01 = transform.val[1];
			physicsComponent.m02 = transform.val[2];
			physicsComponent.m03 = transform.val[3];
			
			physicsComponent.m04 = transform.val[4];
			physicsComponent.m05 = transform.val[5];
			physicsComponent.m06 = transform.val[6];
			physicsComponent.m07 = transform.val[7];
			
			physicsComponent.m08 = transform.val[8];
			physicsComponent.m09 = transform.val[9];
			physicsComponent.m10 = transform.val[10];
			physicsComponent.m11 = transform.val[11];
			
			physicsComponent.m12 = transform.val[12];
			physicsComponent.m13 = transform.val[13];
			physicsComponent.m14 = transform.val[14];
			physicsComponent.m15 = transform.val[15];
						
			// reset the motionstate update flag
			motionState.setUpdated(false);
			
		}
		
	}
	
}