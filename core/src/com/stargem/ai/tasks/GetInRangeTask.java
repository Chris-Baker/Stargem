/**
 * 
 */
package com.stargem.ai.tasks;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.stargem.ai.AIBrain;
import com.stargem.behaviour.BehaviourStrategy;
import com.stargem.entity.Entity;
import com.stargem.physics.KinematicCharacter;
import com.stargem.physics.PhysicsManager;

/**
 * GetInRangeTask.java
 *
 * @author 	Chris B
 * @date	24 Apr 2014
 * @version	1.0
 */
public class GetInRangeTask extends AbstractTask {
	
	private final Vector3 targetPosition = new Vector3();	
	private KinematicCharacter characterBody;
	private final Matrix4 characterTransform = new Matrix4();
	private final Vector3 characterPosition = new Vector3();	
	private final Vector3 attackLocation = new Vector3();
	
	private static final Array<GetInRangeTask> pool = new Array<GetInRangeTask>();
	
	public static GetInRangeTask newInstance(BehaviourStrategy behaviour, AIBrain brain, Entity entity, AbstractTask parent, Vector3 target) {
		if(pool.size == 0) {
			return new GetInRangeTask(behaviour, brain, entity, parent, target);
		}
		else {
			GetInRangeTask task = pool.pop();
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
	 * @param brain
	 * @param entity
	 * @param parent
	 */
	private GetInRangeTask(BehaviourStrategy behaviour, AIBrain brain, Entity entity, AbstractTask parent, Vector3 target) {
		super(behaviour, brain, entity, parent);
		super.mask = AbstractTask.MASK_MOVEMENT | AbstractTask.MASK_BEHAVIOUR;
		super.isBlocking = true;
		this.targetPosition.set(target);
		this.characterBody = PhysicsManager.getInstance().getCharacter(entity.getId());		
	}

	/* (non-Javadoc)
	 * @see com.stargem.ai.tasks.AbstractTask#update(float)
	 */
	@Override
	public boolean update(float delta) {
		
		// get the position of the characterBody
		characterBody.getMotionState().getWorldTransform(characterTransform);
		characterTransform.getTranslation(characterPosition);
		
		// TODO get weapon component and weapon to get its range
		float range2 = 1;
		
		// get the distance between the two positions
		float distance2 = characterPosition.dst2(targetPosition);
		
		// if it is less than the range then we need to move to a spot that is in range
		// for now that can be the nearest point on a straight line to the target
		if(distance2 > range2) {
			// find the point range away from target
			attackLocation.set(targetPosition).sub(characterPosition).nor().scl(range2).add(targetPosition);			
			super.brain.addTask(MoveToTask.newInstance(behaviour, brain, entity, parent, attackLocation));
		}
		
		return true;
	}

	/* (non-Javadoc)
	 * @see com.stargem.ai.tasks.AbstractTask#free()
	 */
	@Override
	public void free() {
		this.behaviour = null;
		this.brain = null;
		this.entity = null;
		this.parent = null;
		this.targetPosition.set(0, 0, 0);
		this.characterBody = null;
		pool.add(this);
	}

}
