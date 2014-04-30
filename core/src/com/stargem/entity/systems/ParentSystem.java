package com.stargem.entity.systems;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.stargem.entity.Entity;
import com.stargem.entity.components.Parent;
import com.stargem.entity.components.Physics;
import com.stargem.physics.PhysicsManager;

/**
 * ParentSystem.java
 *
 * @author 	Chris B
 * @date	26 Apr 2014
 * @version	1.0
 */
public class ParentSystem extends AbstractSystem {

	private final PhysicsManager physicsManager = PhysicsManager.getInstance();
	private final Matrix4 transform = new Matrix4();
	
	public ParentSystem() {
		super();
	}

	@Override
	public void process(float deltaTime) {
		super.entities = em.getAllEntitiesPossessingComponent(Parent.class);
		super.process(deltaTime);
	}

	@Override
	public void process(float deltaTime, Entity entity) {
		
		Parent parent = em.getComponent(entity, Parent.class);
		Entity parentEntity = em.getEntityByID(parent.parentId);
				
		// if the parent is null then we need to remove this entity also
		if(parentEntity == null) {
			em.recycle(entity);
			return;
		}		
		
		Physics parentPhysics = em.getComponent(parentEntity, Physics.class);		
		Physics physics = em.getComponent(entity, Physics.class);
		
		if(physics == null || parentPhysics == null) {
			return;
		}
		
		btRigidBody from = physicsManager.getRigidBody(parentPhysics.bodyIndex);
		btRigidBody to   = physicsManager.getRigidBody(physics.bodyIndex);
		
		// get the world transform of the body
		from.getMotionState().getWorldTransform(transform);
		to.getMotionState().setWorldTransform(transform);
		to.setWorldTransform(transform);
	}
	
}