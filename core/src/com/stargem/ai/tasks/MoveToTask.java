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
 * MoveToTask.java
 *
 * @author 	Chris B
 * @date	24 Apr 2014
 * @version	1.0
 */
public class MoveToTask extends AbstractTask {

	private final Vector3 location;
		
	/**
	 * @param brain
	 * @param entity
	 * @param parent
	 */
	public MoveToTask(BehaviourStrategy behaviour, AIBrain brain, Entity entity, AbstractTask parent, Vector3 location) {
		super(behaviour, brain, entity, parent);
		super.mask = AbstractTask.MASK_MOVEMENT;
		super.isBlocking = true;
		this.location = location;
	}

	/* (non-Javadoc)
	 * @see com.stargem.ai.tasks.AbstractTask#update(float)
	 */
	@Override
	public boolean update(float delta) {
		
		// get the position and forward direction of the character
		Physics characterPhysics = EntityManager.getInstance().getComponent(entity, Physics.class);
		KinematicCharacter characterBody = PhysicsManager.getInstance().getCharacter(characterPhysics.bodyIndex);
		Matrix4 characterTransform = new Matrix4();
		Vector3 characterPosition = new Vector3();
		Vector3 characterForward = new Vector3();
		characterBody.getMotionState().getWorldTransform(characterTransform);
		characterTransform.getTranslation(characterPosition);
		characterForward.set(characterTransform.val[8], characterTransform.val[9], characterTransform.val[10]);
		
		
		// are we facing the location if not push a turn to face
		if(VectorUtils.isFacing(characterPosition, characterForward, location, 0.01f)) {
			super.brain.addTask(new TurnToFaceTask(behaviour, brain, entity, this, location));
		}
		
		// move forward
		this.onMoveForward();
		
		// TODO are we at location, if so return true		
		return false;
	}

	private void onMoveForward() {
		behaviour.onMoveForward();
	}

}