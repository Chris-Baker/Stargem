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
 * AttackTask.java
 *
 * @author 	Chris B
 * @date	24 Apr 2014
 * @version	1.0
 */
public class AttackTask extends AbstractTask {

	/**
	 * @param brain
	 * @param entity
	 * @param parent
	 */
	public AttackTask(BehaviourStrategy behaviour, AIBrain brain, Entity entity, AbstractTask parent) {
		super(behaviour, brain, entity, parent);
		super.mask = AbstractTask.MASK_BEHAVIOUR;
		super.isBlocking = true;
	}

	/* (non-Javadoc)
	 * @see com.stargem.ai.tasks.AbstractTask#update(float)
	 */
	@Override
	public boolean update(float delta) {
		
		// attack the most dangerous target
		Entity target = brain.getBiggestThreat();
		
		// we are done if there are no threats to attack
		if(target == null) {
			return true;
		}
		
		// get the target's position
		Physics targetPhysics = EntityManager.getInstance().getComponent(target, Physics.class);
		KinematicCharacter targetBody = PhysicsManager.getInstance().getCharacter(targetPhysics.bodyIndex);
		Matrix4 targetTransform = new Matrix4();
		Vector3 targetPosition = new Vector3();
		targetBody.getMotionState().getWorldTransform(targetTransform);
		targetTransform.getTranslation(targetPosition);
		
		// get the brain's forward direction
		Physics characterPhysics = EntityManager.getInstance().getComponent(entity, Physics.class);
		KinematicCharacter characterBody = PhysicsManager.getInstance().getCharacter(characterPhysics.bodyIndex);
		Matrix4 characterTransform = new Matrix4();
		Vector3 characterPosition = new Vector3();
		Vector3 characterForward = new Vector3();
		characterBody.getMotionState().getWorldTransform(characterTransform);
		characterTransform.getTranslation(characterPosition);
		characterForward.set(characterTransform.val[8], characterTransform.val[9], characterTransform.val[10]);
		
		// if we are not facing the target then turn to face the target
		if(VectorUtils.isFacing(characterPosition, characterForward, targetPosition, 0.01f)) {
			super.brain.addTask(new TurnToFaceTask(behaviour, brain, entity, this, targetPosition));
		}
		
		// if are not in range get in range
		float range2 = 1 * 1;
		float distance2 = characterPosition.dst2(targetPosition);
		if(distance2 > range2) {		
			super.brain.addTask(new GetInRangeTask(behaviour, brain, entity, this, targetPosition));
		}
		else {
			// else fire the equipped weapon if one is available
			behaviour.onStopMoving();
			behaviour.onAttack();
		}
		
		return false;
	}

	
	
}