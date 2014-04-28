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

/**
 * GetInRangeTask.java
 *
 * @author 	Chris B
 * @date	24 Apr 2014
 * @version	1.0
 */
public class GetInRangeTask extends AbstractTask {
	
	//private final KinematicCharacter targetBody;
	//private final Matrix4 targetTransform = new Matrix4();
	private final Vector3 targetPosition;
	
	private final KinematicCharacter characterBody;
	private final Matrix4 characterTransform = new Matrix4();
	private final Vector3 characterPosition = new Vector3();
	
	private final Vector3 attackLocation = new Vector3();
	
	/**
	 * @param brain
	 * @param entity
	 * @param parent
	 */
	public GetInRangeTask(BehaviourStrategy behaviour, AIBrain brain, Entity entity, AbstractTask parent, Vector3 target) {
		super(behaviour, brain, entity, parent);
		super.mask = AbstractTask.MASK_MOVEMENT | AbstractTask.MASK_BEHAVIOUR;
		super.isBlocking = true;
		this.targetPosition = target;
		
		Physics characterPhysics = EntityManager.getInstance().getComponent(entity, Physics.class);
		this.characterBody = PhysicsManager.getInstance().getCharacter(characterPhysics.bodyIndex);
		
//		Physics targetPhysics = EntityManager.getInstance().getComponent(target, Physics.class);
//		this.targetBody = PhysicsManager.getInstance().getCharacter(targetPhysics.bodyIndex);
		
	}

	/* (non-Javadoc)
	 * @see com.stargem.ai.tasks.AbstractTask#update(float)
	 */
	@Override
	public boolean update(float delta) {
		
		// get the position of the characterBody
		characterBody.getMotionState().getWorldTransform(characterTransform);
		characterTransform.getTranslation(characterPosition);
		
		// get the position of the target
		//targetBody.getMotionState().getWorldTransform(targetTransform);
		//targetTransform.getTranslation(targetPosition);
		
		// TODO get weapon component and weapon to get its range
		float range2 = 1;
		
		// get the distance between the two positions
		float distance2 = characterPosition.dst2(targetPosition);
		
		// if it is less than the range then we need to move to a spot that is in range
		// for now that can be the nearest point on a straight line to the target
		if(distance2 > range2) {
			// find the point range away from target
			attackLocation.set(targetPosition).sub(characterPosition).nor().scl(range2).add(targetPosition);			
			super.brain.addTask(new MoveToTask(behaviour, brain, entity, parent, attackLocation));
		}
		
		return true;
	}

}
