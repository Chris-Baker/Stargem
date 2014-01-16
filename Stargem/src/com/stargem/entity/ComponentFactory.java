/**
 * 
 */
package com.stargem.entity;

import com.stargem.entity.components.Expires;
import com.stargem.entity.components.Physics;
import com.stargem.entity.components.RenderableStatic;
import com.stargem.entity.components.Trigger;
import com.stargem.physics.PhysicsManager;



/**
 * The component factory handles creation and initialisation of components.
 * It presents a usable interface to the scripting API 
 * ComponentFactory.java
 *
 * @author 	Chris B
 * @date	6 Nov 2013
 * @version	1.0
 */
public class ComponentFactory {
	
	public static Trigger trigger(Entity e, String script) {		
		Trigger c = ComponentManager.getInstance().newComponentOfType(Trigger.class);
		c.name = script;
		return c;
	}
	
	public static Expires expires(Entity e, float lifespan) {
		Expires c = ComponentManager.getInstance().newComponentOfType(Expires.class);
		c.lifespan = lifespan;
		return c;
	}
	
	/* @formatter:off */
	public static Physics physics(Entity e, int index,
			float m00, float m01, float m02, float m03,
			float m04, float m05, float m06, float m07,
			float m08, float m09, float m10, float m11,
			float m12, float m13, float m14, float m15,
			int type, float width, float height, float depth,
			float angluarVelocityX, float angluarVelocityY, float angluarVelocityZ,
			float linearVelocityX, float linearVelocityY, float linearVelocityZ,
			float gravityX, float gravityY, float gravityZ,
			float mass, float restitution, int activationState) {
		
		Physics c = ComponentManager.getInstance().newComponentOfType(Physics.class);
		
		// the modelIndex we get from the physics manager
		//c.modelIndex = modelIndex;
		
		c.m00 = m00;
		c.m01 = m01;
		c.m02 = m02;
		c.m03 = m03;
		c.m04 = m04;
		c.m05 = m05;
		c.m06 = m06;
		c.m07 = m07;
		c.m08 = m08;
		c.m09 = m09;
		c.m10 = m10;
		c.m11 = m11;
		c.m12 = m12;
		c.m13 = m13;
		c.m14 = m14;
		c.m15 = m15;
		
		c.type = type;
		c.width = width;
		c.height = height;
		c.depth = depth;
		
		c.angluarVelocityX = angluarVelocityX;
		c.angluarVelocityY = angluarVelocityY;
		c.angluarVelocityZ = angluarVelocityZ;
		
		c.linearVelocityX = linearVelocityX;
		c.linearVelocityY = linearVelocityY;
		c.linearVelocityZ = linearVelocityZ;
		
		c.gravityX = gravityX;
		c.gravityY = gravityY;
		c.gravityZ = gravityZ;
		
		c.mass = mass;
		c.restitution = restitution;
		c.activationState = activationState;
		
		// create the actual physics body from the newly constructed component
		c.bodyIndex = PhysicsManager.getInstance().createBodyFromComponent(e, c);
		
		return c;
	}
	/* @formatter:on */
	
	public static RenderableStatic renderableStatic(Entity entity, int index, String modelName) {
		RenderableStatic c = ComponentManager.getInstance().newComponentOfType(RenderableStatic.class);
		c.modelName = modelName;
		return c;
	}
}