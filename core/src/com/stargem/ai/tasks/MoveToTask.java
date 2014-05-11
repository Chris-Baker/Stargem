/**
 * 
 */
package com.stargem.ai.tasks;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.stargem.ai.AIBrain;
import com.stargem.behaviour.BehaviourStrategy;
import com.stargem.entity.Entity;
import com.stargem.physics.KinematicCharacter;
import com.stargem.physics.PhysicsManager;
import com.stargem.utils.VectorUtils;

/**
 * MoveToTask.java
 *
 * @author 	Chris B
 * @date	24 Apr 2014
 * @version	1.0
 */
public class MoveToTask extends AbstractTask {

	private final Vector3 location = new Vector3();
	private final Vector3 characterPosition = new Vector3();
	private final Vector3 characterForward = new Vector3();
	
	private static final Array<MoveToTask> pool = new Array<MoveToTask>();
	
	public static MoveToTask newInstance(BehaviourStrategy behaviour, AIBrain brain, Entity entity, AbstractTask parent, Vector3 location) {
		if(pool.size == 0) {
			return new MoveToTask(behaviour, brain, entity, parent, location);
		}
		else {
			MoveToTask task = pool.pop();
			task.behaviour = behaviour;
			task.brain = brain;
			task.entity = entity;
			task.parent = parent;
			task.location.set(location);
			return task;
		}
	}
	
	/**
	 * @param brain
	 * @param entity
	 * @param parent
	 */
	private MoveToTask(BehaviourStrategy behaviour, AIBrain brain, Entity entity, AbstractTask parent, Vector3 location) {
		super(behaviour, brain, entity, parent);
		super.mask = AbstractTask.MASK_MOVEMENT;
		super.isBlocking = true;
		this.location.set(location);
	}

	/* (non-Javadoc)
	 * @see com.stargem.ai.tasks.AbstractTask#update(float)
	 */
	@Override
	public boolean update(float delta) {
				
		// get the position and forward direction of the character
		KinematicCharacter characterBody = PhysicsManager.getInstance().getCharacter(entity.getId());
		
		if(characterBody == null) {
			return true;
		}
		
		characterBody.getTranslation(characterPosition);
		characterBody.getForward(characterForward);
		
		// are we facing the location if not push a turn to face
		if(VectorUtils.isFacing(characterPosition, characterForward, location, 0.01f)) {
			super.brain.addTask(TurnToFaceTask.newInstance(behaviour, brain, entity, this, location));
		}
		
		// move forward
		this.onMoveForward();
		
		// TODO are we at location, if so return true		
		return false;
	}

	private void onMoveForward() {
		behaviour.onMoveForward();
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
		this.characterForward.set(0, 0, 0);
		this.characterPosition.set(0, 0, 0);
		this.location.set(0, 0, 0);		
		pool.add(this);
	}

}