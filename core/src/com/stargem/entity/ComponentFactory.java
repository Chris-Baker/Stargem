/**
 * 
 */
package com.stargem.entity;

import com.stargem.controllers.ControllerManager;
import com.stargem.entity.components.AISphericalSensor;
import com.stargem.entity.components.Controller;
import com.stargem.entity.components.Health;
import com.stargem.entity.components.Physics;
import com.stargem.entity.components.PlayerStats;
import com.stargem.entity.components.RenderablePointLight;
import com.stargem.entity.components.RenderableSkinned;
import com.stargem.entity.components.RenderableStatic;
import com.stargem.entity.components.RunSpeed;
import com.stargem.entity.components.ThirdPersonCamera;
import com.stargem.entity.components.Timer;
import com.stargem.entity.components.Trigger;
import com.stargem.graphics.EnvironmentManager;
import com.stargem.graphics.RepresentationManager;
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
	
	/* @formatter:off */
	
	/**
	 * Create an AISphericalSensor Component
	 * 
	 * @param entity
	 * @param bodyIndex
	 * @param radius
	 * @param contactGroup
	 * @param contactWith
	 * @return
	 */
	public static AISphericalSensor aisphericalsensor(Entity entity, int bodyIndex,  
			float radius, 
			int contactGroup, int contactWith) {
		AISphericalSensor c = ComponentManager.getInstance().newComponentOfType(AISphericalSensor.class);
		c.bodyIndex = bodyIndex;
		c.radius = radius;
		c.contactGroup = contactGroup;
		c.contactWith = contactWith;
		
		c.bodyIndex = PhysicsManager.getInstance().createBodyFromComponent(entity, c);
		
		return c;
	}
	
	/**
	 * Create a Trigger component.
	 * 
	 * @param entity the entity who owns the component
	 * @param name the name of the script function to call when triggered
	 * @return
	 */
	public static Trigger trigger(Entity entity, String name) {		
		Trigger c = ComponentManager.getInstance().newComponentOfType(Trigger.class);
		c.name = name;
		return c;
	}
	
	/**
	 * Takes a row from the database and converts it into a physics component.
	 * The component is registered with the physics manager which creates all the
	 * related physics bodies.
	 * 
	 * @param entity the entity who owns the component
	 * @param index the index of the physics body created in the physics manager
	 * @param type the type of physics body created either rigid body or character body
	 * @param collisionGroup defines how the body is simulated kinematic etc...
	 * @param collidesWith defines what the body should collide with static, kinematic etc...
	 * @param m00 value in the 4x4 transformation matrix
	 * @param m01 value in the 4x4 transformation matrix
	 * @param m02 value in the 4x4 transformation matrix
	 * @param m03 value in the 4x4 transformation matrix
	 * @param m04 value in the 4x4 transformation matrix
	 * @param m05 value in the 4x4 transformation matrix
	 * @param m06 value in the 4x4 transformation matrix
	 * @param m07 value in the 4x4 transformation matrix
	 * @param m08 value in the 4x4 transformation matrix
	 * @param m09 value in the 4x4 transformation matrix
	 * @param m10 value in the 4x4 transformation matrix
	 * @param m11 value in the 4x4 transformation matrix
	 * @param m12 value in the 4x4 transformation matrix
	 * @param m13 value in the 4x4 transformation matrix
	 * @param m14 value in the 4x4 transformation matrix
	 * @param m15 value in the 4x4 transformation matrix
	 * @param shape the shape shape
	 * @param width the width of the shape
	 * @param height the height of the shape
	 * @param depth the depth of the shape
	 * @param angluarVelocityX
	 * @param angluarVelocityY
	 * @param angluarVelocityZ
	 * @param linearVelocityX
	 * @param linearVelocityY
	 * @param linearVelocityZ
	 * @param gravityX
	 * @param gravityY
	 * @param gravityZ
	 * @param mass
	 * @param restitution
	 * @param activationState
	 * @param contactGroup
	 * @param contactWith
	 * @return
	 */
	public static Physics physics(Entity entity, int index, int type, int collisionGroup, int collidesWith,
			float m00, float m01, float m02, float m03,
			float m04, float m05, float m06, float m07,
			float m08, float m09, float m10, float m11,
			float m12, float m13, float m14, float m15,
			int shape, float width, float height, float depth,
			float angluarVelocityX, float angluarVelocityY, float angluarVelocityZ,
			float linearVelocityX, float linearVelocityY, float linearVelocityZ,
			float gravityX, float gravityY, float gravityZ,
			float mass, float restitution, int activationState,
			int contactGroup, int contactWith) {
		
		Physics c = ComponentManager.getInstance().newComponentOfType(Physics.class);
		
		c.type = type;
		
		c.collisionGroup = collisionGroup;
		c.collidesWith = collidesWith;
		
		c.m00 = m00; c.m01 = m01; c.m02 = m02; c.m03 = m03;
		c.m04 = m04; c.m05 = m05; c.m06 = m06; c.m07 = m07;
		c.m08 = m08; c.m09 = m09; c.m10 = m10; c.m11 = m11;
		c.m12 = m12; c.m13 = m13; c.m14 = m14; c.m15 = m15;
		
		c.shape = shape;
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
		
		c.contactGroup = contactGroup;
		c.contactWith = contactWith;
		
		// create the actual physics body from the newly constructed component
		c.bodyIndex = PhysicsManager.getInstance().createBodyFromComponent(entity, c);
		
		return c;
	}
	/* @formatter:on */
	
	/**
	 * Create a RenderablePointLight component and the corresponding environment objects in the
	 * EnvironmentManager.
	 * 
	 * @param entity the entity who owns the component
	 * @param lightIndex
	 * @param colour
	 * @param intensity
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static RenderablePointLight renderablepointlight(Entity entity, int lightIndex, int colour, float intensity, float x, float y, float z) {
		RenderablePointLight c = ComponentManager.getInstance().newComponentOfType(RenderablePointLight.class);
		c.lightIndex = entity.id;
		c.colour = colour;
		c.intensity = intensity;
		c.x = x;
		c.y = y;
		c.z = z;		
		EnvironmentManager.getInstance().createLightFromComponent(entity.id, c);		
		return c;
	}
	
	/**
	 * Create a component and the actual ModelInstance in the RepresentationManager.
	 * 
	 * @param entity the entity who owns the component
	 * @param index
	 * @param modelPath
	 * @return
	 */
	public static RenderableStatic renderablestatic(Entity entity, int index, String modelPath) {
		RenderableStatic c = ComponentManager.getInstance().newComponentOfType(RenderableStatic.class);
		c.modelPath = modelPath;		
		RepresentationManager.getInstance().createInstanceFromComponent(entity, c);		
		return c;
	}
	
	/**
	 * Create a component and the actual ModelInstance in the RepresentationManager.
	 * 
	 * @param entity the entity who owns the component
	 * @param index
	 * @param modelPath
	 * @param currentAnimation
	 * @return
	 */
	public static RenderableSkinned renderableskinned(Entity entity, int index, String modelPath, String currentAnimation) {
		RenderableSkinned c = ComponentManager.getInstance().newComponentOfType(RenderableSkinned.class);
		c.modelPath = modelPath;
		c.currentAnimationName = currentAnimation;
		RepresentationManager.getInstance().createInstanceFromComponent(entity, c);
		return c;
	}
	
	/**
	 * 
	 * @param entity
	 * @param strategyIndex
	 * @param strategyType
	 * @param script
	 * @param moveForward
	 * @param moveBackward
	 * @param moveLeft
	 * @param moveRight
	 * @param isJumping
	 * @return
	 */
	public static Controller controller(Entity entity, int strategyIndex, int strategyType, String script, boolean moveForward, boolean moveBackward, boolean moveLeft, boolean moveRight, boolean isJumping) {
		Controller c = ComponentManager.getInstance().newComponentOfType(Controller.class);
		c.strategyIndex = strategyIndex;
		c.strategyType = strategyType;
		c.script = script;
		c.moveForward = moveForward;
		c.moveBackward = moveBackward;
		c.moveLeft = moveLeft;
		c.moveRight = moveRight;
		
		ControllerManager.getInstance().createControllerFromComponent(entity, c);
		
		return c;		
	}
	
	/**
	 * 
	 * @param entity
	 * @param speed
	 * @return
	 */
	public static RunSpeed runspeed(Entity entity, int speed) {
		RunSpeed c = ComponentManager.getInstance().newComponentOfType(RunSpeed.class);
		c.speed = speed;
		return c;		
	}
	
	/**
	 * 
	 * @param entity
	 * @param hasFocus
	 * @param minDistance
	 * @param maxDistance
	 * @param currentDistance
	 * @param heightOffset
	 * @param pitch
	 * @param deltaPitch
	 * @return
	 */
	public static ThirdPersonCamera thirdpersoncamera(Entity entity, boolean hasFocus, float minDistance, float maxDistance, float currentDistance, float heightOffset, float pitch, float deltaPitch, float yaw, float deltaYaw) {
		ThirdPersonCamera c = ComponentManager.getInstance().newComponentOfType(ThirdPersonCamera.class);
		c.hasFocus = hasFocus;
		c.minDistance = maxDistance;
		c.maxDistance = minDistance;
		c.currentDistance = currentDistance;
		c.heightOffset = heightOffset;
		c.pitch = pitch;
		c.deltaPitch = deltaPitch;
		c.yaw = yaw;
		c.deltaYaw = deltaYaw;
		return c;		
	}
	
	/**
	 * 
	 * @param entity
	 * @param timeLeft
	 * @return
	 */
	public static Timer timer(Entity entity, float timeLeft) {
		Timer c = ComponentManager.getInstance().newComponentOfType(Timer.class);
		c.timeLeft = timeLeft;
		return c;
	}
	
	/**
	 * 
	 * @param entity
	 * @param maxHealth
	 * @param currentHealth
	 * @return
	 */
	public static Health health(Entity entity, int maxHealth, int currentHealth) {
		Health c = ComponentManager.getInstance().newComponentOfType(Health.class);
		c.maxHealth = maxHealth;
		c.currentHealth = currentHealth;
		return c;
	}
	
	/**
	 * 
	 * @param entity
	 * @param cores
	 * @param specials
	 * @param gems
	 * @param damageIncrease
	 * @param healthIncrease
	 * @param speedIncrease
	 * @return
	 */
	public static PlayerStats playerstats(Entity entity, int cores, int specials, int gems, int damageIncrease, int healthIncrease, int speedIncrease) {
		PlayerStats c = ComponentManager.getInstance().newComponentOfType(PlayerStats.class);
		c.cores = cores;
		c.specials = specials;
		c.gems = gems;
		c.damageIncrease = damageIncrease;
		c.healthIncrease = healthIncrease;
		c.speedIncrease = speedIncrease;
		return c;
	}
}