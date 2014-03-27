/**
 * 
 */
package com.stargem.entity.systems;

import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.stargem.entity.Entity;
import com.stargem.entity.components.Physics;
import com.stargem.entity.components.RenderablePointLight;
import com.stargem.graphics.EnvironmentManager;
import com.stargem.physics.PhysicsManager;


/**
 * PhysicsSystem.java
 *
 * @author 	Chris B
 * @date	17 Nov 2013
 * @version	1.0
 */
public class LightMovingSystem extends AbstractSystem {
	
	private final EnvironmentManager environmentManager = EnvironmentManager.getInstance();
	private final PhysicsManager physicsManager = PhysicsManager.getInstance();
	private final Matrix4 transform = new Matrix4();
	
	public LightMovingSystem() {
		super();
	}

	@Override
	public void process(float deltaTime) {
		super.entities = em.getAllEntitiesPossessingComponent(RenderablePointLight.class);
		super.process(deltaTime);
	}

	@Override
	public void process(float deltaTime, Entity entity) {
		Physics physicsComponent = em.getComponent(entity, Physics.class);	
		if(physicsComponent == null) {
			return;
		}
		
		btRigidBody body = physicsManager.getRigidBody(physicsComponent.bodyIndex);
		RenderablePointLight pointLightComponent = em.getComponent(entity, RenderablePointLight.class);	
		PointLight pointLight = environmentManager.getPointLight(pointLightComponent.lightIndex);
		
		// get the world transform of the body
		body.getWorldTransform(transform);
		
		// get the new position
		float x = transform.val[12];
		float y = transform.val[13];
		float z = transform.val[14];
		
		// update the position
		pointLightComponent.x = x;
		pointLightComponent.y = y;
		pointLightComponent.z = z;
					
		pointLight.position.set(x, y, z);
	}
	
}