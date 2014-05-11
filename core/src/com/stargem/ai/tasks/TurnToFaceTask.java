/**
 * 
 */
package com.stargem.ai.tasks;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.stargem.ai.AIBrain;
import com.stargem.behaviour.BehaviourStrategy;
import com.stargem.entity.Entity;
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
	
	private KinematicCharacter characterBody;
	private final Matrix4 characterTransform = new Matrix4();
	private final Vector3 characterForward = new Vector3();
	private final Vector3 characterUp = new Vector3();
	private final Vector3 characterPosition = new Vector3();
	
	private final Vector3 targetPosition = new Vector3();
	private final Vector3 projectedTarget = new Vector3();
	private final Vector3 adjustment = new Vector3();
	
	private static final Array<TurnToFaceTask> pool = new Array<TurnToFaceTask>();
	
	public static TurnToFaceTask newInstance(BehaviourStrategy behaviour, AIBrain brain, Entity entity, AbstractTask parent, Vector3 target) {
		if(pool.size == 0) {
			return new TurnToFaceTask(behaviour, brain, entity, parent, target);
		}
		else {
			TurnToFaceTask task = pool.pop();
			task.behaviour = behaviour;
			task.brain = brain;
			task.entity = entity;
			task.parent = parent;
			task.targetPosition.set(target);
			task.characterBody = PhysicsManager.getInstance().getCharacter(entity.getId());
			return task;
		}
	}
	
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
	private TurnToFaceTask(BehaviourStrategy behaviour, AIBrain brain, Entity entity, AbstractTask parent, Vector3 target) {
		super(behaviour, brain, entity, parent);
		super.mask = AbstractTask.MASK_MOVEMENT | AbstractTask.MASK_BEHAVIOUR;
		super.isBlocking = true;
		this.targetPosition.set(target);
		this.characterBody = PhysicsManager.getInstance().getCharacter(entity.getId());
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
		
		projectedTarget.set(targetPosition);
		
		adjustment.set(projectedTarget).sub(characterPosition).nor();
		
//		characterTransform.setToLookAt(characterPosition, adjustment, characterUp);
//		characterTransform.trn(characterPosition);
//		characterBody.getMotionState().setWorldTransform(characterTransform);
		
		float targetAngle = MathUtils.atan2(adjustment.z, adjustment.x);		
		float currentAngle = MathUtils.atan2(characterForward.z, characterForward.x);		
		
		//System.out.println("Target" + targetAngle);
		//System.out.println("Current" + currentAngle);
		
		if (currentAngle < targetAngle - 0.05 || currentAngle > targetAngle + 0.05) {

			// Stop the body from turning the wrong direction by correcting the angle
			if (currentAngle - targetAngle < -Math.PI) {
				currentAngle += 2 * Math.PI;
			}
			if (currentAngle - targetAngle > Math.PI) {
				currentAngle -= 2 * Math.PI;
			}

			// interpolate to the correct angle
			characterTransform.rotateRad(Vector3.Y, (0.2f * (currentAngle - targetAngle)));
			characterBody.getMotionState().setWorldTransform(characterTransform);
			return true;
		}			
		return true;
	}

	/* (non-Javadoc)
	 * @see com.stargem.ai.tasks.AbstractTask#free()
	 */
	@Override
	public void free() {
		super.behaviour = null;
		super.brain = null;
		super.entity = null;
		super.parent = null;
		this.targetPosition.set(0, 0, 0);
		this.characterBody = null;
		pool.add(this);
	}
}