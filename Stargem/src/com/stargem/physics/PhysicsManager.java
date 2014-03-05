/**
 * 
 */
package com.stargem.physics;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.btBoxShape;
import com.badlogic.gdx.physics.bullet.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.btCapsuleShape;
import com.badlogic.gdx.physics.bullet.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.btCollisionShape;
import com.badlogic.gdx.physics.bullet.btConstraintSolver;
import com.badlogic.gdx.physics.bullet.btCylinderShape;
import com.badlogic.gdx.physics.bullet.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.btGhostPairCallback;
import com.badlogic.gdx.physics.bullet.btRigidBody;
import com.badlogic.gdx.physics.bullet.btRigidBodyConstructionInfo;
import com.badlogic.gdx.physics.bullet.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.physics.bullet.btSphereShape;
import com.badlogic.gdx.utils.Array;
import com.stargem.Config;
import com.stargem.entity.Entity;
import com.stargem.entity.EntityManager;
import com.stargem.entity.components.Physics;
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
	
	// the collision callback listener
	ContactProcessedListener listener;
	
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
		
		// Collision callback, this is set active upon instantiation
		//listener = new ContactProcessedListener();
		//listener.enableOnAdded
		
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
		
		//body.setContactCallbackFilter(CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK);
		//body.setContactCallbackFlag(body.getContactCallbackFlag() | CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK);
		
		// add the body to the simulation
		return this.addRigidBody(info, shape, body, motionState, (short) component.collisionGroup, (short) component.collidesWith);
		
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
		
		// dispose the bullet objects
		this.bodies.get(index).dispose();
		this.shapes.get(index).dispose();
		this.bodyInfos.get(index).dispose();
		this.motionStates.get(index).dispose();
		
		// remove the stored references to the objects
		this.bodyInfos.removeIndex(index);
		this.shapes.removeIndex(index);
		this.bodies.removeIndex(index);
		this.motionStates.removeIndex(index);
		
		// update the body copied over the deleted body
		Entity entity = (Entity) this.bodies.get(index).userData;
		Physics component = EntityManager.getInstance().getComponent(entity, Physics.class);
		component.bodyIndex = index;
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
	 * Dispose of all physics objects
	 */
	public void dispose() {
		this.listener.dispose();
		this.collisionConfiguration.dispose();
		this.dispatcher.dispose();
		this.broadphase.dispose();
		this.solver.dispose();
		this.ghostPairCallback.dispose();
		this.dynamicsWorld.dispose();
		
		for(int i = 0, n = bodies.size; i < n; i += 1) {
			this.bodies.get(i).dispose();
			this.shapes.get(i).dispose();
			this.bodyInfos.get(i).dispose();
			this.motionStates.get(i).dispose();
		}
		
		if(this.terrain != null) {
			this.terrain.dispose();
		}
				
		instance = null;
	}

	public void setGravity() {
		
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
			
		}
		
	}
	
	public void updateCharacters(float delta) {
		
		for(btRigidBody body : bodies) {
		
			if(body.getClass().equals(KinematicCharacter.class)) {
				KinematicCharacter character = (KinematicCharacter) body;
				character.updateAction(this.dynamicsWorld, delta);
			}
			
		}		
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
	
}