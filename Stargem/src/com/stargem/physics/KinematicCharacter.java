/**
 * 
 */
package com.stargem.physics;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.ClosestConvexResultCallback;
import com.badlogic.gdx.physics.bullet.ClosestRayResultCallback;
import com.badlogic.gdx.physics.bullet.LocalConvexResult;
import com.badlogic.gdx.physics.bullet.LocalRayResult;
import com.badlogic.gdx.physics.bullet.btBroadphasePair;
import com.badlogic.gdx.physics.bullet.btBroadphasePairArray;
import com.badlogic.gdx.physics.bullet.btBroadphaseProxy;
import com.badlogic.gdx.physics.bullet.btCollisionObject;
import com.badlogic.gdx.physics.bullet.btCollisionWorld;
import com.badlogic.gdx.physics.bullet.btConvexShape;
import com.badlogic.gdx.physics.bullet.btDispatcher;
import com.badlogic.gdx.physics.bullet.btDispatcherInfo;
import com.badlogic.gdx.physics.bullet.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.btHashedOverlappingPairCache;
import com.badlogic.gdx.physics.bullet.btManifoldArray;
import com.badlogic.gdx.physics.bullet.btManifoldPoint;
import com.badlogic.gdx.physics.bullet.btPairCachingGhostObject;
import com.badlogic.gdx.physics.bullet.btPersistentManifold;
import com.badlogic.gdx.physics.bullet.btRigidBody;
import com.badlogic.gdx.physics.bullet.btRigidBodyConstructionInfo;
import com.badlogic.gdx.physics.bullet.gdxBullet;
import com.stargem.Config;

/**
 * KinematicCharacter.java
 * 
 * @author Chris B
 * @date 15 Aug 2013
 * @version 1.0
 */
public class KinematicCharacter extends btRigidBody {

	//private static final float SIMD_EPSILON = 1.19209290e-07f;

	// contains the transform for the rigid body and the model instance
	private final MotionState motionState;

	// the rotation speed of the player
	private float rotationSpeed = 100;

	// step height and step offset. the offset is the current up direction scaled by the step height
	private final float stepHeight = 1f;
	private final Vector3 stepOffset = new Vector3();

	// Euler angle representation of the change in current rotation in degrees around the up axis
	private float deltaForward;
	
	// used by the move function to calculate the direction of movement
	private final Vector3 direction = new Vector3();

	// the maximum slope traversable by the player
	private float maxSlopeRadians;
	private float maxSlopeCosine;

	// the amount added to the position each tick to move the player
	private final Vector3 horizontalOffset = new Vector3();
	private final Vector3 horizontalOffsetNormalised = new Vector3();

	// the current position of the player
	private final Vector3 position = new Vector3();
	
	// the default up and forward directions 
	private final Vector3 defaultUp = new Vector3(Vector3.Y);
	
	// the current up, right and out directions
	private final Vector3 up = new Vector3();
	private final Vector3 right = new Vector3();
	private final Vector3 out = new Vector3();
	
	// the current verticalOffset vector based on the players position from the origin
	// the offset is in world coordinates
	private final Vector3 verticalOffset = new Vector3();
	private float verticalVelocity;
	private final float terminalVelocity = -55.0f;
	private boolean isOnGround = false;
	private boolean isJumping = false;
	private final float jumpSpeed;

	// used to project movement and detect collisions
	private final btPairCachingGhostObject ghost;
	private final btBroadphaseProxy broadphaseProxy;

	// references to the ghost objects collision objects
	private final btHashedOverlappingPairCache ghostOverlappingPairCache;
	private final btDispatcherInfo worldDispatcherInfo;
	private final btDispatcher worldDispatcher;

	// stored in the ghost but this stops upcast
	private final btConvexShape convexShape;

	// stuff for collision detection and movement
	private final btManifoldArray manifoldArray = new btManifoldArray();
	private btBroadphasePairArray pairArray;
	private final Vector3 deltaPosition = new Vector3();
	private final Vector3 normalWorldOnB = new Vector3();
	private final Matrix4 start = new Matrix4();
	private final Matrix4 end = new Matrix4();

	//private KinematicClosestNotMeConvexResultCallback convexCB;
	//private KinematicClosestNotMeRayResultCallback raycastCB;

	private final Vector3 minAabb = new Vector3();
	private final Vector3 maxAabb = new Vector3();

	private boolean rotateClockwise;
	private boolean rotateCounterClockwise;
	
	// used to calculate the up vector of the character
	private final Vector3 origin;
	private final boolean useVariableUp;
	
	/**
	 * Create a new Kinematic Character with the world origin set to 0, 0, 0 and add it to the world
	 * 
	 * @param dynamicsWorld
	 * @param constructionInfo
	 * @param group
	 * @param collidesWith
	 * @param motionState
	 */
	public KinematicCharacter(btDynamicsWorld dynamicsWorld, btRigidBodyConstructionInfo constructionInfo, short group, short collidesWith, MotionState motionState) {
		this(dynamicsWorld, constructionInfo, group, collidesWith, motionState, new Vector3());
	}
	
	/**
	 * Create a new Kinematic Character and add it to the world
	 * 
	 * @param dynamicsWorld
	 * @param constructionInfo
	 * @param group
	 * @param collidesWith
	 * @param motionState
	 * @param worldOrigin
	 */
	public KinematicCharacter(btDynamicsWorld dynamicsWorld, btRigidBodyConstructionInfo constructionInfo, short group, short collidesWith, MotionState motionState, Vector3 worldOrigin) {
		super(constructionInfo);

		// create a motion state to link to the renderer
		this.motionState = motionState;
		super.setMotionState(this.motionState);

		// set the current transform to match that of the transform passed in
		super.setWorldTransform(motionState.transform);

		// make sure this object isn't deactivated so that it will always collide with dynamic objects
		super.setActivationState(gdxBullet.DISABLE_DEACTIVATION);

		// store the shape as a convex shape here to avoid up-casting each time raycast is done
		this.convexShape = (btConvexShape) constructionInfo.getCollisionShape();

		// the ghost object used to check collisions with static objects
		this.broadphaseProxy = new btBroadphaseProxy();
		this.ghost = new btPairCachingGhostObject();
		this.ghost.setCollisionShape(constructionInfo.getCollisionShape());
		this.ghost.setWorldTransform(motionState.transform);
		this.ghost.setBroadphaseHandle(broadphaseProxy);

		this.ghostOverlappingPairCache = this.ghost.getOverlappingPairCache();
		this.worldDispatcherInfo = dynamicsWorld.getDispatchInfo();
		this.worldDispatcher = dynamicsWorld.getDispatcher();

		// add the ghost object and the kinematic object to the world
		dynamicsWorld.addCollisionObject(this.ghost, group, CollisionFilterGroups.STATIC_GROUP);
		//dynamicsWorld.addRigidBody(this, (short) (group | CollisionFilterGroups.KINEMATIC_GROUP), collidesWith);
		
		// default settings for the player
		this.setMaxSlope(70);
		this.jumpSpeed = 8;

		this.origin = new Vector3(worldOrigin);
		this.useVariableUp = true;
		
		//this.isOnGround = true;
		
		// callbacks
		//this.convexCB = new KinematicClosestNotMeConvexResultCallback(ghost, up, -1.0f);
		//this.raycastCB = new KinematicClosestNotMeRayResultCallback(ghost);
		
		// apply rotation to the character so that it sits on the surface of the planet
		this.motionState.transform.getTranslation(up);
		up.sub(this.origin).nor();
		motionState.transform.getTranslation(position);
		motionState.transform.setToRotation(defaultUp, up);
		motionState.transform.trn(position);
		
	}
		
	/**
	 * If the character controller is on the ground then attempt to jump
	 * 
	 * @return true if the character could jump, false otherwise
	 */
	public boolean jump() {
		
		boolean success = false;
		
		if(this.isOnGround) {
			this.isJumping = true;
			this.isOnGround = false;
			verticalVelocity = jumpSpeed;
			success = true;
		}
		
		return success;
	}
	
	/**
	 * Set the character to rotate around the up axis either clockwise
	 * or counter clockwise. If both are set to true, one will simply 
	 * cancel the other out. Rotation speed 
	 * 
	 * @param rotateClockwise
	 * @param rotateCounterClockwise
	 * @param rotationSpeed
	 */
	public void rotate(boolean rotateClockwise, boolean rotateCounterClockwise, float rotationSpeed) {
		this.rotateClockwise = rotateClockwise;
		this.rotateCounterClockwise = rotateCounterClockwise;
		this.rotationSpeed = Math.abs(rotationSpeed);
	}
	
	/**
	 * Set the character to rotate around the up axis either clockwise
	 * or counter clockwise. A positive rotation speed indicates a clockwise
	 * rotation and a negative rotation speed indicates a counter clockwise rotation.
	 * 
	 * @param rotationSpeed
	 */
	public void rotate(float rotationSpeed) {
		this.rotateClockwise = rotationSpeed > 0;
		this.rotateCounterClockwise = rotationSpeed < 0;
		this.rotationSpeed = Math.abs(rotationSpeed);
	}

	/**
	 * Translate the player in the direction of the vector given.
	 * The vector is normalised and scaled by the player's run speed.
	 * Typical usage involves setting the direction.z to 1 or -1, This
	 * moves the player forward and backward respectively.
	 * 
	 * @param direction the direction to move the player
	 */
	public void move(int runSpeed, boolean moveForward, boolean moveBackward, boolean moveLeft, boolean moveRight) {
		
		// if we are on the ground then we can move along and change direction
		// when we aren;t on the ground we can't change direction but will continue to move
		// in the direction we were going for a time
		if(this.isOnGround) {
		
			this.direction.x = 0;
			this.direction.z = 0;
	
			if (moveForward) {
				this.direction.z += 1;
			}
			if (moveBackward) {
				this.direction.z -= 1;
			}
			if (moveLeft) {
				this.direction.x += 1;
			}
			if (moveRight) {
				this.direction.x -= 1;
			}
	
			horizontalOffsetNormalised.set(direction).nor();
			horizontalOffset.set(horizontalOffsetNormalised).scl(runSpeed);
		}
		else {
			// else gradually lose horizontal momentum if we are not jumping
			// this might feel a bit wrong when we walk off ledges
			// maybe we should be falling for a while before we degrade the horizontal movement
			if(!this.isJumping) {
				horizontalOffset.scl(0.95f);
			}
		}
	}

	/**
	 * 
	 * @param collisionWorld
	 * @param deltaTime
	 */
	public void updateAction(btCollisionWorld collisionWorld, float deltaTime) {
		
		// stop the player from penetrating the floor etc...
		this.preStep(collisionWorld);

		// move the player
		this.playerStep(collisionWorld, deltaTime);
	}

	/**
	 * Interpolate the player ghost out of any static collisions for a set
	 * number of iterations then update the players transform with the ghost
	 * position.
	 * 
	 * @param collisionWorld
	 */
	private void preStep(btCollisionWorld collisionWorld) {
		int numPenetrationLoops = 0;
		while (this.recoverFromPenetration(collisionWorld) && numPenetrationLoops < 4) {
			numPenetrationLoops++;
		}
	}

	/**
	 * 
	 * @param collisionWorld
	 * @param deltaTime
	 */
	private void playerStep(btCollisionWorld collisionWorld, float deltaTime) {
		
		// apply rotation
		this.applyRotation(deltaTime);

		// move horizontally
		this.stepSlide(collisionWorld, deltaTime);
		
		// apply gravity
		this.applyGravity(collisionWorld, deltaTime);
		
		// update the kinematic object to match the ghost
		super.setWorldTransform(this.motionState.transform);
	}

	/**
	 * @param deltaTime
	 */
	private void applyRotation(float deltaTime) {
		
		// reset delta forward
		deltaForward = 0;
		
		if(rotateCounterClockwise) {
			deltaForward += rotationSpeed * deltaTime;
		}
		if(rotateClockwise) {
			deltaForward -= rotationSpeed * deltaTime;
		}
				
		// if we are using variable up direction i.e. if we are on a sphere surface
		// then rotations are a whole lot more complicated
		if(this.useVariableUp) {
			
			// calculate the new up vector based on the new position
			motionState.transform.getTranslation(position);
			up.set(position).sub(this.origin).nor();
			
			// the right vector stays as it is until we have computed the new out / forward
			// vector it does not change because we are moving in a straight line over the 
			// surface of the sphere.
			right.set(motionState.transform.val[0], motionState.transform.val[1], motionState.transform.val[2]);
			
			// the new forward direction is the cross product of the right and new up vectors
			out.set(right).crs(up);
			
			// update the right vector in case the character has moved on the x axis
			// without this update the matrix warps when strafing.
			right.set(up).crs(out);
			
			// correct the right vector
			motionState.transform.val[0]  = right.x;
			motionState.transform.val[1]  = right.y;
			motionState.transform.val[2]  = right.z;
			
			// correct the up vector
			motionState.transform.val[4]  = up.x;
			motionState.transform.val[5]  = up.y;
			motionState.transform.val[6]  = up.z;
			
			// correct the forward vector
			motionState.transform.val[8]  = out.x;
			motionState.transform.val[9]  = out.y;
			motionState.transform.val[10] = out.z;
		}
				
		// apply rotation around the character's local up vector
		motionState.transform.rotate(defaultUp, deltaForward);
		
	}

	/**
	 * Move the character controller in the forward direction by the horizontal offset
	 * calculated by the move method, the offset is first multiplied with delta time.
	 * 
	 * This method allows the character controller to slide along flat surfaces, smoothly 
	 * slide up and down slopes, and step over small steps. The step height is set by the 
	 * field stepHeight.
	 * 
	 * @param collisionWorld
	 * @param deltaTime
	 */
	private void stepSlide(btCollisionWorld collisionWorld, float deltaTime) {
		
		// return early here if no movement is required
		// this is skipped so that gravity is still applied to objects
		// when the character jumps into a wall, stopping the character
		// from sticking
//		if(horizontalOffset.len2() == 0) {
//			return;
//		}
				
		KinematicClosestNotMeConvexResultCallback verticalCB;
		KinematicClosestNotMeConvexResultCallback horizontalCB;		
		
		// we check if on ground here because the offset is only updated when on ground
		// we don't want to rotate and scale it if it hasn't changed i.e when jumping
		if(this.isOnGround) {
			
			// rotate the offset into world coordinates
			horizontalOffset.rot(this.motionState.transform);	
			
			// scale by the time delta for frame rate independence
			horizontalOffset.scl(deltaTime);
		}
				
		// set the start position and desired end position
		start.set(this.motionState.transform);
		end.set(this.motionState.transform);
		end.trn(horizontalOffset);
		
		// sweep horizontal
		horizontalCB = this.sweep(collisionWorld, start, end);
		
		// if there was a collision then we sweep downward to the desired end location to find the step height
		if(horizontalCB.hasHit()) {
						
			// we start to cast from the step height and cast downward towards the ground
			this.stepOffset.set(this.up).scl(this.stepHeight);
			start.set(end).trn(stepOffset);
			verticalCB = this.sweep(collisionWorld, start, end);
			
			// if there was a collision we may need to step up a little
			if(verticalCB.hasHit()) {
				
				// we hit the something so stop jumping if we were
				// this stops the player from launching into the air very quickly
				// when a collision occurs mid jump
				this.verticalVelocity = 0.0f;
				this.isJumping = false;
				
				// if the fraction is zero then the controller is penetrating a wall after 
				// the step up so we don't want to perform the step up.
				// TODO Also check here for slopes which are too steep.
				if(verticalCB.getClosestHitFraction() == 0.0f) {
					// step is too high so just walk into it and stop
					motionState.transform.trn(horizontalOffset.scl(horizontalCB.getClosestHitFraction()));
				}
				else {
					// there is small enough step to walk up
					motionState.transform.trn(horizontalOffset.add(stepOffset.scl(1 - verticalCB.getClosestHitFraction())));
				}
			}
			else {
				// there was no downward collision so we only move horizontal as far as the original
				// horizontal collision
				motionState.transform.trn(horizontalOffset.scl(horizontalCB.getClosestHitFraction()));
			}
		}
		else {
						
			// cast downward from the new position to see if the ground is within the step currentDistance
			// if it is then we can move straight down to the ground allowing smooth transitions
			// down ramps.
			this.stepOffset.set(this.up).scl(-this.stepHeight);
			
			start.set(end);
			end.trn(stepOffset);
			
			verticalCB = this.sweep(collisionWorld, start, end);
			
			float hitFraction = verticalCB.getClosestHitFraction();
			
			// The ratio here is the amount taken from measuring the downward fraction
			// when the controller is on a flat surface, used here as a qualifier for
			// downward movement so that the controller doesn't jitter on flat surfaces
			float hitFractionMinThreshold = stepHeight / 20.0f;			
			
			if(verticalCB.hasHit()) {				
				
				// do not snap to the floor if we are jumping
				if(hitFraction > hitFractionMinThreshold && !this.isJumping) {
					//System.out.println("down a fraction:" + downCB.getClosestHitFraction());
					motionState.transform.trn(stepOffset.scl(hitFraction));
				}
			}
			else {
				// if there was no downward collision then we are not on the ground
				this.isOnGround = false;
			}
			
			// move the whole way horizontally
			motionState.transform.trn(horizontalOffset);
			
		}		
		
		// update the ghost object's transform		
		this.ghost.setWorldTransform(this.motionState.transform);
		
		// don't rely on the GC to do this
		horizontalCB.dispose();
		verticalCB.dispose();		
	}
		
	/**
	 * 
	 * 
	 * @param collisionWorld
	 * @param deltaTime
	 */
	private void applyGravity(btCollisionWorld collisionWorld, float deltaTime) {

		// don't apply gravity if we are on the ground
		if(this.isOnGround) {
			return;
		}
		
		// update vertical velocity and offset, limit it to terminal velocity
		// is player on ground?
		verticalVelocity += Config.GRAVITY * deltaTime;
		if (verticalVelocity < terminalVelocity) {
			verticalVelocity = terminalVelocity;
		}
		verticalOffset.set(this.up).scl(verticalVelocity * deltaTime);
		
		//System.out.println("Gravity: " + verticalOffset);
		
		// set the start and end matrices for the sweep test
		this.start.set(this.motionState.transform);
		this.end.set(this.motionState.transform);
		this.end.trn(this.verticalOffset);

		// TODO is there a way to do this without allocating memory every frame?
		KinematicClosestNotMeConvexResultCallback convexCB = this.sweep(collisionWorld, start, end);
		float hitFraction = convexCB.getClosestHitFraction();

		if (convexCB.hasHit()) {
						
			this.motionState.transform.trn(this.verticalOffset.scl(hitFraction));
			
			this.verticalVelocity = 0.0f;
			this.isOnGround = true;
			this.isJumping = false;
		}
		else {
			this.motionState.transform.set(end);
			this.isOnGround = false;
		}
		
		// update the ghost
		this.ghost.setWorldTransform(this.motionState.transform);

		// don't rely on the GC to do this
		convexCB.dispose();
	}

	/**
	 * 
	 * 
	 * @param collisionWorld
	 * @return
	 */
	private boolean recoverFromPenetration(btCollisionWorld collisionWorld) {

		// Here we must refresh the overlapping paircache as the penetrating movement itself or the
		// previous recovery iteration might have used setWorldTransform and pushed us into an object
		// that is not in the previous cache contents from the last timestep, as will happen if we
		// are pushed into a new AABB overlap. Unhandled this means the next convex sweep gets stuck.
		//
		// Do this by calling the broadphase's setAabb with the moved AABB, this will update the broadphase
		// paircache and the ghostobject's internal paircache at the same time.    /BW
		convexShape.getAabb(ghost.getWorldTransform(), minAabb, maxAabb);
		collisionWorld.getBroadphase().setAabb(ghost.getBroadphaseHandle(), minAabb, maxAabb, collisionWorld.getDispatcher());

		boolean isPenetrating = false;

		// find overlapping pairs of objects
		worldDispatcher.dispatchAllCollisionPairs(ghostOverlappingPairCache, worldDispatcherInfo, worldDispatcher);

		pairArray = this.ghost.getOverlappingPairCache().getOverlappingPairArray();

		// reset the delta position
		deltaPosition.set(0, 0, 0);

		// iterate over the objects overlapping the ghost object and...
		for (int i = 0, n = pairArray.size(); i < n; i++) {

			// clear the manifold array because it is currently populated by the previous contacts
			manifoldArray.clear();

			// get the next overlapping object
			btBroadphasePair collisionPair = pairArray.at(i);

			// get the contact manifolds from this collision object
			if (collisionPair.getAlgorithm() != null) {
				collisionPair.getAlgorithm().getAllContactManifolds(manifoldArray);
			}

			// loop over manifolds and update the target position
			for (int j = 0, m = manifoldArray.size(); j < m; j++) {

				btPersistentManifold manifold = manifoldArray.at(j);
				float directionSign = (manifold.getBody0().equals(ghost)) ? -1.0f : 1.0f;

				for (int k = 0, p = manifold.getNumContacts(); k < p; k++) {
					btManifoldPoint pt = manifold.getContactPoint(k);
					float dist = pt.getDistance();
					if (dist < 0.0f) {
						normalWorldOnB.set(pt.getNormalWorldOnB().getX(), pt.getNormalWorldOnB().getY(), pt.getNormalWorldOnB().getZ());
						deltaPosition.add(normalWorldOnB.scl(directionSign * dist * 0.2f));
						isPenetrating = true;
					}
				}
			}
		}
				
		// update the motionstate and ghost object
		this.motionState.transform.trn(deltaPosition);
		this.ghost.setWorldTransform(this.motionState.transform);

		return isPenetrating;
	}
		
	/**
	 * Perform a sweep test using the controller's ghost object
	 * 
	 * @param collisionWorld
	 * @param start
	 * @param end
	 * @return
	 */
	private KinematicClosestNotMeConvexResultCallback sweep(btCollisionWorld collisionWorld, Matrix4 start, Matrix4 end) {
		KinematicClosestNotMeConvexResultCallback convexCB = new KinematicClosestNotMeConvexResultCallback(ghost, up, -1.0f);
		convexCB.setCollisionFilterGroup(ghost.getBroadphaseHandle().getCollisionFilterGroup());
		convexCB.setCollisionFilterMask(ghost.getBroadphaseHandle().getCollisionFilterMask());
		ghost.convexSweepTest(this.convexShape, this.start, this.end, convexCB, collisionWorld.getDispatchInfo().getAllowedCcdPenetration());
		return convexCB;
	}
	
	/**
	 * Set the maximum slope
	 * 
	 * @param slopeRadians
	 */
	public void setMaxSlope(float slopeDegrees) {
		this.maxSlopeRadians = slopeDegrees * MathUtils.degreesToRadians;
		this.maxSlopeCosine = MathUtils.cos(maxSlopeRadians);
	}

	////////////////////////////////////////////////////////////////////////////

	protected static class KinematicClosestNotMeRayResultCallback extends ClosestRayResultCallback {
		protected btCollisionObject me;

		public KinematicClosestNotMeRayResultCallback(btCollisionObject me) {
			super(Vector3.Zero, Vector3.Z);
			this.me = me;
		}

		@Override
		public float addSingleResult(LocalRayResult rayResult, boolean normalInWorldSpace) {
			if (rayResult.getCollisionObject().equals(me)) {
				return 1.0f;
			}

			return super.addSingleResult(rayResult, normalInWorldSpace);
		}
	}

	////////////////////////////////////////////////////////////////////////////

	protected static class KinematicClosestNotMeConvexResultCallback extends ClosestConvexResultCallback {
		protected btCollisionObject me;
		protected btCollisionObject hitCollisionObject;
		protected final Vector3 up;
		protected float minSlopeDot;
		private final Vector3 hitNormalWorld = new Vector3();
		private final Matrix4 tmp = new Matrix4();

		public KinematicClosestNotMeConvexResultCallback(btCollisionObject me, final Vector3 up, float minSlopeDot) {
			super(Vector3.Zero, Vector3.Z);
			this.me = me;
			this.up = up;
			this.minSlopeDot = minSlopeDot;
		}

		@Override
		public float addSingleResult(LocalConvexResult convexResult, boolean normalInWorldSpace) {

			hitCollisionObject = convexResult.getHitCollisionObject();

			if (hitCollisionObject.equals(me)) {
				return 1.0f;
			}

			if (normalInWorldSpace) {
				hitNormalWorld.set(convexResult.getHitNormalLocal().getX(), convexResult.getHitNormalLocal().getY(), convexResult.getHitNormalLocal().getZ());
			}
			else {
				System.out.println("Normal not in world space, converting... this may not work as intended please check");
				// TODO test this
				//need to transform normal into worldspace
				tmp.set(hitCollisionObject.getWorldTransform());
				tmp.toNormalMatrix();
				hitNormalWorld.mul(tmp);
			}

			float dotUp = up.dot(hitNormalWorld);
			if (dotUp < minSlopeDot) {
				return 1.0f;
			}

			return super.addSingleResult(convexResult, normalInWorldSpace);
		}
	}

}