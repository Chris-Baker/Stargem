/**
 * 
 */
package com.stargem.ai.tasks;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.stargem.ai.AIBrain;
import com.stargem.behaviour.BehaviourStrategy;
import com.stargem.entity.Entity;
import com.stargem.entity.EntityManager;
import com.stargem.entity.components.Physics;
import com.stargem.physics.KinematicCharacter;
import com.stargem.physics.PhysicsManager;
import com.stargem.utils.VectorUtils;

/**
 * TurnToFaceTask.java
 *
 * @author 	Chris B
 * @date	23 Apr 2014
 * @version	1.0
 */
public class TurnToFaceTask extends AbstractTask {

	//private final KinematicCharacter targetBody;
	//private final Matrix4 targetTransform = new Matrix4();
	// = new Vector3();
	
	private final KinematicCharacter characterBody;
	private final Matrix4 characterTransform = new Matrix4();
	private final Vector3 characterForward = new Vector3();
	private final Vector3 characterUp = new Vector3();
	private final Vector3 characterPosition = new Vector3();
	
	private final Vector3 targetPosition;
	private final Vector3 projectedTarget = new Vector3();
	
	
	/**
	 * Turn to face a point. The brain will swivel its body
	 * on the local Y axis in order to face a given target. 
	 * This requires the entity to have a physics component.  
	 * 
	 * @param brain
	 * @param entity
	 * @param parent
	 * @param target
	 */
	public TurnToFaceTask(BehaviourStrategy behaviour, AIBrain brain, Entity entity, AbstractTask parent, Vector3 target) {
		super(behaviour, brain, entity, parent);
		super.mask = AbstractTask.MASK_MOVEMENT | AbstractTask.MASK_BEHAVIOUR;
		super.isBlocking = true;
		this.targetPosition = target;
		Physics characterPhysics = EntityManager.getInstance().getComponent(entity, Physics.class);
		this.characterBody = PhysicsManager.getInstance().getCharacter(characterPhysics.bodyIndex);
		
		//Physics targetPhysics = EntityManager.getInstance().getComponent(target, Physics.class);
		//this.targetBody = PhysicsManager.getInstance().getCharacter(targetPhysics.bodyIndex);
	}

	/**
	 * Update the entity's direction on the Y axis until it faces the target
	 * 
	 * @param delta
	 * @return true if the entity now faces the target within an epsilon, false otherwise
	 */
	@Override
	public boolean update(float delta) {
		
		// get the position of the characterBody
		characterBody.getMotionState().getWorldTransform(characterTransform);
		characterForward.set(characterTransform.val[8], characterTransform.val[9], characterTransform.val[10]);
		characterUp.set(characterTransform.val[4], characterTransform.val[5], characterTransform.val[6]);
		characterTransform.getTranslation(characterPosition);
				
		// get the position of the target
		//targetBody.getMotionState().getWorldTransform(targetTransform);
		//targetTransform.getTranslation(targetPosition);
		
		// get the direction vector to the target
		
		// project target position onto the plane of the character
		// the plane's normal is the character up vector which is
		// the characters normalised position
		VectorUtils.projectPointOnPlane(characterUp, characterPosition, targetPosition, projectedTarget);
		
		//projectedTarget.set(targetPosition);
		
		Vector3 adjustment = new Vector3();
		adjustment.set(projectedTarget).sub(characterPosition).nor();
		
//		characterTransform.setToLookAt(characterPosition, adjustment, characterUp);
//		characterTransform.trn(characterPosition);
//		characterBody.getMotionState().setWorldTransform(characterTransform);
						
//		float targetAngle = MathUtils.atan2(adjustment.z, adjustment.x);		
//		float currentAngle = MathUtils.atan2(characterForward.z, characterForward.x);		
//		
//		System.out.println("Target" + targetAngle);
//		System.out.println("Current" + currentAngle);
//		
//		if (currentAngle < targetAngle - 0.05 || currentAngle > targetAngle + 0.05) {
//
//			// Stop the body from turning the wrong direction by correcting the angle
//			if (currentAngle - targetAngle < -Math.PI) {
//				currentAngle += 2 * Math.PI;
//			}
//			if (currentAngle - targetAngle > Math.PI) {
//				currentAngle -= 2 * Math.PI;
//			}
//
//			// interpolate to the correct angle
//			characterTransform.rotateRad(Vector3.Y, (0.2f * (currentAngle - targetAngle)));
//			characterBody.getMotionState().setWorldTransform(characterTransform);
//			return true;
//		}			
		return true;
	}
}