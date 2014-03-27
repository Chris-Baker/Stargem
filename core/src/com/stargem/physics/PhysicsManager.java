/**
 * 
 */
package com.stargem.physics;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btCapsuleShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btCylinderShape;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btGhostPairCallback;
import com.badlogic.gdx.physics.bullet.collision.btSphereShape;
import com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBodyConstructionInfo;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.utils.Array;
import com.stargem.Config;
import com.stargem.entity.Entity;
import com.stargem.entity.EntityManager;
import com.stargem.entity.components.Physics;
import com.stargem.graphics.PhysicsDebugDraw;
import com.stargem.graphics.RepresentationManager;
import com.stargem.terrain.TerrainSphere;
import com.stargem.utils.Log;

/**
 * PhysicsManager.java
 *
 * @author 	Chris B
 * @date	14 Nov 2013
 * @version	1.0
 */
public class PhysicsManager {
		
	public static final int SHAPE_SPHERE	= 0;
	public static final int SHAPE_BOX 		= 1;
	public static final int SHAPE_CYLINDER 	= 2;
	public static final int SHAPE_CAPSULE 	= 4;	
	
	public static final int RIGID_BODY		= 0;
	public static final int CHARACTER		= 1;
	
	public static final Vector3 WORLD_ORIGIN = new Vector3(0, 0, 0);
	
	private final btCollisionConfiguration collisionConfiguration;
	private final btCollisionDispatcher dispatcher;
	private final btBroadphaseInterface broadphase;
	private final btConstraintSolver solver;
	private final btDynamicsWorld dynamicsWorld;
	private final btGhostPairCallback ghostPairCallback;
	private final Vector3 gravity = new Vector3(0, Config.GRAVITY, 0);

	private final Array<MotionState> motionStates = new Array<MotionState>();
	private final Array<btRigidBodyConstructionInfo> bodyInfos = new Array<btRigidBodyConstructionInfo>();
	private final Array<btCollisionShape> shapes = new Array<btCollisionShape>();
	private final Array<btRigidBody> bodies = new Array<btRigidBody>();	
	private TerrainPhysicsBody terrain;
	
	private final Vector3 tempVector = new Vector3(0, 0, 0);
	
	// Used to calculate the gravity direction of each body
	private final Vector3 acceleration = new Vector3();
	private final Vector3 position = new Vector3(0, 0, 0);
	
	// the collision callback contactListener
	StargemContactListener contactListener;
	private boolean debug;
	
	// Singleton instance
	private static PhysicsManager instance;
	public static PhysicsManager getInstance() {
		if(instance == null) {
			instance = new PhysicsManager();
		}
		return instance;
	}
	
	/**
	 * Creates a dynamics world
	 */
	private PhysicsManager() {
		// Load the bullet library
		Bullet.init();
				
		// Create the bullet world
		collisionConfiguration = new btDefaultCollisionConfiguration();
		dispatcher = new btCollisionDispatcher(collisionConfiguration);
		broadphase = new btDbvtBroadphase();
		solver = new btSequentialImpulseConstraintSolver();
		dynamicsWorld = new btDiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
		dynamicsWorld.setGravity(gravity);
		
		// This makes ghost objects work
		ghostPairCallback = new btGhostPairCallback();
		dynamicsWorld.getPairCache().setInternalGhostPairCallback(ghostPairCallback);
		
		// Collision callback, this is set active upon instantiation automagically by the Bullet wrapper
		contactListener = new StargemContactListener();
		
	}
	
	/**
	 * 
	 * @param gravity
	 */
	public void setGravity(Vector3 gravity) {
		this.gravity.set(gravity);
		this.dynamicsWorld.setGravity(gravity);
	}

	/**
	 * @param motionStateIndex
	 * @return
	 */
	public MotionState getMotionState(int index) {
		return this.motionStates.get(index);
	}

	/**
	 * @param motionStateIndex
	 * @return
	 */
	public btRigidBody getRigidBody(int index) {
		return this.bodies.get(index);
	}
	
	/**
	 * Add a body to the physics simulation.
	 * 
	 * @param info
	 * @param shape
	 * @param body
	 * @param motionState
	 * @param group
	 * @param collidesWith
	 * @return the index at which the body is stored
	 */
	private int addRigidBody(btRigidBodyConstructionInfo info, btCollisionShape shape, btRigidBody body, MotionState motionState, short group, short collidesWith) {
				
		this.bodyInfos.add(info);
		this.shapes.add(shape);
		this.bodies.add(body);
		this.motionStates.add(motionState);
		
		this.dynamicsWorld.addRigidBody(body, group, collidesWith);
		//this.dynamicsWorld.addRigidBody(body);
		
		// set the index as the body user value, it is used for collision callbacks
		body.setUserValue(this.bodies.size - 1);
		
		return this.bodies.size - 1;
	}
	
	/**
	 * Create a physics body from a physics component. The body is then added to the simulation.
	 * The supplied entity is added to the body as userdata.
	 * 
	 * @param entity
	 * @param component
	 * @return the index at which the body is stored
	 */
	public int createBodyFromComponent(Entity entity, Physics component) {
				
		// shape
		btCollisionShape shape = this.getShape(entity.getId(), component.shape, component.width, component.height, component.depth);
		shapes.add(shape);
		shape.calculateLocalInertia(component.mass, tempVector.set(Vector3.Zero));
		
		// motion state
		MotionState motionState;
		
		// See if there is a model attached to this entity. 
		// If so then we grab the transform matrix from it for the motion state,
		// otherwise we use a new matrix.		
		Matrix4 transform = RepresentationManager.getInstance().getTransformMatrix(entity);		
		motionState = (transform == null) ? new MotionState(new Matrix4()) : new MotionState(transform);
		
		motionState.transform.val[0]  = component.m00;
		motionState.transform.val[1]  = component.m01;
		motionState.transform.val[2]  = component.m02;
		motionState.transform.val[3]  = component.m03;
		
		motionState.transform.val[4]  = component.m04;
		motionState.transform.val[5]  = component.m05;
		motionState.transform.val[6]  = component.m06;
		motionState.transform.val[7]  = component.m07;
		
		motionState.transform.val[8]  = component.m08;
		motionState.transform.val[9]  = component.m09;
		motionState.transform.val[10] = component.m10;
		motionState.transform.val[11] = component.m11;
		
		motionState.transform.val[12] = component.m12;
		motionState.transform.val[13] = component.m13;
		motionState.transform.val[14] = component.m14;
		motionState.transform.val[15] = component.m15;
		
		// info
		btRigidBodyConstructionInfo info = new btRigidBodyConstructionInfo(component.mass, null, shape, tempVector.set(1, 1, 1));
				
		info.setMotionState(motionState);
				
		// create either a character or a rigid body
		btRigidBody body;
		
		if(component.type == RIGID_BODY) {
			Log.info(Config.PHYSICS_ERR, "Creating rigid body for entity " + entity.getId());
			body = new btRigidBody(info);
		}
		else if (component.type == CHARACTER) {
			Log.info(Config.PHYSICS_ERR, "Creating character for entity " + entity.getId());
			body = new KinematicCharacter(this.dynamicsWorld, info, (short) component.collisionGroup, (short) component.collidesWith, motionState, WORLD_ORIGIN);
		}
		else {
			String message = "Cannot create unknown physics body type for entity ID: " + entity.getId();
			Log.error(Config.PHYSICS_ERR, message);
			throw new Error(message);
		}
		
		// set the motionstate
		body.setMotionState(motionState);
		
		// store the entity in the body
		body.userData = entity;
		
		// set the properties of the body stored in the component
		body.setAngularVelocity(new Vector3(component.angluarVelocityX, component.angluarVelocityY, component.angluarVelocityZ));
		body.setLinearVelocity(new Vector3(component.linearVelocityX, component.linearVelocityY, component.linearVelocityZ));
		body.setGravity(new Vector3(component.gravityX, component.gravityY, component.gravityZ));
		body.setRestitution(component.restitution);
		body.setActivationState(component.activationState);
		
		body.setContactCallbackFilter(component.contactWith);
		body.setContactCallbackFlag(component.contactGroup);
		
		int index = this.addRigidBody(info, shape, body, motionState, (short) component.collisionGroup, (short) component.collidesWith);
		
		// if debug mode is set then we add a model to the debug draw
		if(this.debug) {
			PhysicsDebugDraw.getInstance().createDebugInstance(index, motionState.transform, component.shape, component.width, component.height, component.depth);
		}
		
		// add the body to the simulation returning the index it is added at
		return index;
		
	}
		
	/**
	 * @param shape
	 * @return
	 */
	private btCollisionShape getShape(int entityId, int shapeType, float width, float height, float depth) {
		
		btCollisionShape shape;
		
		switch(shapeType) {
		
		case SHAPE_SPHERE:
			shape = new btSphereShape(width);
			break;
			
		case SHAPE_BOX:
			shape = new btBoxShape(tempVector.set(width, height, depth));
			break;
			
		case SHAPE_CYLINDER:
			shape = new btCylinderShape(tempVector.set(width, height, depth));
			break;
			
		case SHAPE_CAPSULE:
			shape  = new btCapsuleShape(width, height);
			break;
		
		default:
			String message = "Cannot create unknown physics shape for entity ID: " + entityId;
			Log.error(Config.PHYSICS_ERR, message);
			throw new Error(message);
		}
		
		
		return shape;
	}

	/**
	 * Remove the rigid body from the manager and simulation.
	 * The rigid body at the end of the array is copied over the one to be removed.
	 * The moved body has the associated physics component updated with its new modelIndex.
	 * 
	 * @param modelIndex the modelIndex of the physics body to remove
	 */
	public void removeRigidBody(int index) {
		
		// remove the body
		this.dynamicsWorld.removeRigidBody(this.bodies.get(index));
				
		// remove the stored references to the objects and dispose the bullet objects
		this.bodies.removeIndex(index).dispose();
		this.bodyInfos.removeIndex(index).dispose();
		this.shapes.removeIndex(index); // disposing this crashes Bullet
		this.motionStates.removeIndex(index).dispose();
		
		// update the body copied over the deleted body unless the body was last in the array
		// then the body is null doesn't need updating
		if(index < this.bodies.size) {
			btRigidBody body = this.bodies.get(index);
			Entity entity = (Entity) body.userData;
			Physics component = EntityManager.getInstance().getComponent(entity, Physics.class);
			component.bodyIndex = index;
			body.setUserValue(index);
		}
	}
	
	/**
	 * Create the rigid bodies to simulate the terrain of the world.
	 * 
	 * @param terrain
	 */
	public void createBodyFromTerrain(TerrainSphere terrain) {		
		this.terrain = new TerrainPhysicsBody(terrain);
		this.terrain.addToWorld(this.dynamicsWorld);				
	}
		
	/**
	 * Step the physics simulation
	 * 
	 * @param delta
	 */
	public void stepSimulation(float delta) {
		
		// set the gravity for each body based on its position relative to the center of the world
		for(btRigidBody body : bodies) {
						
			if(body.isActive() && !body.isStaticOrKinematicObject() && !body.getClass().equals(KinematicCharacter.class)) {
			
				// get the position of the body as a vector
				body.getWorldTransform().getTranslation(position);
				
				// get the vector direction from the body to the origin and normalise
				acceleration.set(position).sub(WORLD_ORIGIN).nor();
				
				// scale by the gravity force
				acceleration.scl(Config.GRAVITY);
				
				// set the gravity on the body
				body.setGravity(acceleration);
			}
			
			// if the body is a character we need to update it manually
			if(body.getClass().equals(KinematicCharacter.class)) {
				KinematicCharacter character = (KinematicCharacter) body;
				character.updateAction(this.dynamicsWorld, delta);
			}
			
		}
		
		this.dynamicsWorld.stepSimulation(delta, Config.NUM_SUBSTEPS);
	}

	/**
	 * Given an entity returns the related transform matrix or null if no physics component is attached.
	 * 
	 * @param entity
	 * @return
	 */
	public Matrix4 getTransformMatrix(Entity entity) {
		
		Matrix4 transform = null;
		
		Physics p = EntityManager.getInstance().getComponent(entity, Physics.class);
		if(p != null) {
			MotionState s = this.motionStates.get(p.bodyIndex);
			transform = s.transform;
		}
		
		return transform;		
	}

	/**
	 * Return whether or not the debug draw is switched on
	 * 
	 * @return whether or not the debug draw is switched on
	 */
	public boolean debug() {
		return this.debug;
	}

	/**
	 * Set whether or not debug draw is required. Only physics bodies added after this
	 * is switched on will be added to the debug renderer.
	 * 
	 * @param debug whether or not debug draw is required.
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	
	/**
	 * Dispose of all physics objects
	 */
	public void dispose() {
		this.collisionConfiguration.dispose();
		this.dispatcher.dispose();
		this.broadphase.dispose();
		this.solver.dispose();
		this.ghostPairCallback.dispose();
		this.dynamicsWorld.dispose();
		
		for(int i = 0, n = bodies.size; i < n; i += 1) {
			this.bodies.removeIndex(i).dispose();
			//this.shapes.removeIndex(i).dispose(); // disposing this crashes bullet
			this.bodyInfos.removeIndex(i).dispose();
			this.motionStates.removeIndex(i).dispose();
		}
		
		if(this.terrain != null) {
			this.terrain.dispose();
		}
	}
	
}