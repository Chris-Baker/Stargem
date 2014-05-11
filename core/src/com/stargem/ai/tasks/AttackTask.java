/**
 * 
 */
package com.stargem.ai.tasks;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
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

	private final Vector3 targetPosition = new Vector3();
	private final Vector3 characterPosition = new Vector3();
	private final Vector3 characterForward = new Vector3();
	
	private static final Array<AttackTask> pool = new Array<AttackTask>();
	
	public static AttackTask newInstance(BehaviourStrategy behaviour, AIBrain brain, Entity entity, AbstractTask parent) {
		if(pool.size == 0) {
			return new AttackTask(behaviour, brain, entity, parent);
		}
		else {
			AttackTask task = pool.pop();
			task.behaviour = behaviour;
			task.brain = brain;
			task.entity = entity;
			task.parent = parent;
			return task;
		}
	}
	
	/**
	 * @param brain
	 * @param entity
	 * @param parent
	 */
	private AttackTask(BehaviourStrategy behaviour, AIBrain brain, Entity entity, AbstractTask parent) {
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
		targetBody.getTranslation(targetPosition);
		
		// get the brain's forward direction
		Physics characterPhysics = EntityManager.getInstance().getComponent(entity, Physics.class);
		KinematicCharacter characterBody = PhysicsManager.getInstance().getCharacter(characterPhysics.bodyIndex);
		characterBody.getTranslation(characterPosition);
		characterBody.getForward(characterForward);
		
		// if we are not facing the target then turn to face the target
		if(VectorUtils.isFacing(characterPosition, characterForward, targetPosition, 0.01f)) {
			super.brain.addTask(TurnToFaceTask.newInstance(behaviour, brain, entity, this, targetPosition));
		}
		
		// if are not in range get in range
		float range2 = 1 * 1;
		float distance2 = characterPosition.dst2(targetPosition);
		if(distance2 > range2) {		
			super.brain.addTask(GetInRangeTask.newInstance(behaviour, brain, entity, this, targetPosition));
		}
		else {
			// else fire the equipped weapon if one is available
			behaviour.onAttack();
		}
		
		return false;
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
		this.characterForward.set(0, 0, 0);
		this.characterPosition.set(0, 0, 0);		
		pool.add(this);
	}
	
}