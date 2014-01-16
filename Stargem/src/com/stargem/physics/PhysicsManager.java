/**
 * 
 */
package com.stargem.physics;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
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
import com.stargem.Log;
import com.stargem.entity.Entity;
import com.stargem.entity.EntityManager;
import com.stargem.entity.components.Physics;
import com.stargem.entity.components.RenderableSkinned;
import com.stargem.entity.components.RenderableStatic;
import com.stargem.graphics.RepresentationManager;

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
	public static final int SHAPE_CAPSULE 	= 3;	
	
	private final btCollisionConfiguration collisionConfiguration;
	private final btCollisionDispatcher dispatcher;
	private final btBroadphaseInterface broadphase;
	private final btConstraintSolver solver;
	private final btDynamicsWorld dynamicsWorld;
	private final btGhostPairCallback ghostPairCallback;
	private final Vector3 gravity = new Vector3(0, 0, 0);

	private final Array<MotionState> motionStates = new Array<MotionState>();
	private final Array<btRigidBodyConstructionInfo> bodyInfos = new Array<btRigidBodyConstructionInfo>();
	private final Array<btCollisionShape> shapes = new Array<btCollisionShape>();
	private final Array<btRigidBody> bodies = new Array<btRigidBody>();
	
	private final Vector3 tempVector = new Vector3(0, 0, 0);
	
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
	 * Add a body to the physics simulation. Each argument is stored in an ordered array.
	 * They are all be added at the same modelIndex to allow easy removal of items. 
	 * 
	 * @param info
	 * @param shape
	 * @param body
	 * @param mostionState
	 * @return the modelIndex at which each 
	 */
	private int addRigidBody(btRigidBodyConstructionInfo info, btCollisionShape shape, btRigidBody body, MotionState motionState) {
				
		this.bodyInfos.add(info);
		this.shapes.add(shape);
		this.bodies.add(body);
		this.motionStates.add(motionState);
		
		this.dynamicsWorld.addRigidBody(body);
		
		return this.bodies.size - 1;
	}
	
	/**
	 * 
	 * 
	 * @param entity
	 * @param component
	 * @return
	 */
	public int createBodyFromComponent(Entity entity, Physics component) {
				
		// shape
		btCollisionShape shape;
		
		switch(component.type) {
			
		case SHAPE_SPHERE:
			shape = new btSphereShape(component.width);
			break;
			
		case SHAPE_BOX:
			shape = new btBoxShape(tempVector.set(component.width, component.height, component.depth));
			break;
			
		case SHAPE_CYLINDER:
			shape = new btCylinderShape(tempVector.set(component.width, component.height, component.depth));
			break;
			
		case SHAPE_CAPSULE:
			shape  = new btCapsuleShape(component.width, component.height);
			break;
		
		default:
			String message = "Cannot create unknown physics shape for entity ID: " + entity.getId();
			Log.error(Config.PHYSICS_ERR, message);
			throw new Error(message);
		}
		shapes.add(shape);
		shape.calculateLocalInertia(component.mass, tempVector.set(Vector3.Zero));
		
		// info
		btRigidBodyConstructionInfo info = new btRigidBodyConstructionInfo(component.mass, null, shape, tempVector);
		
		// motion state
		MotionState motionState;
		
		// See if there is a model attached to this entity. 
		// If so then we grab the transform matrix from it for the motion state,
		// otherwise we use a new matrix.
		RenderableSkinned renderableSkinned = EntityManager.getInstance().getComponent(entity, RenderableSkinned.class);
		if(renderableSkinned != null) {
			ModelInstance model = RepresentationManager.getInstance().getModelInstance(renderableSkinned.modelIndex);
			motionState = new MotionState(model.transform);
		}
		else {
			
			// look for a static model component
			RenderableStatic renderableStatic = EntityManager.getInstance().getComponent(entity, RenderableStatic.class);
			if(renderableStatic != null) {
				ModelInstance model = RepresentationManager.getInstance().getModelInstance(renderableStatic.modelIndex);
				motionState = new MotionState(model.transform);
			}
			else {
				// there is neither a static nor a skinned model so we create a fresh matrix
				motionState = new MotionState(new Matrix4());				
			}			
		}
		
		// body
		info.setMotionState(motionState);
		btRigidBody body = new btRigidBody(info);
		body.userData = entity;
		
		// add the body to the simulation
		return this.addRigidBody(info, shape, body, motionState);
		
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
			this.bodies.get(i).dispose();
			this.shapes.get(i).dispose();
			this.bodyInfos.get(i).dispose();
			this.motionStates.get(i).dispose();
		}
				
		instance = null;
	}
	
}