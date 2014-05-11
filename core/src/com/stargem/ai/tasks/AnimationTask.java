/**
 * 
 */
package com.stargem.ai.tasks;

import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.utils.Array;
import com.stargem.ai.AIBrain;
import com.stargem.behaviour.BehaviourStrategy;
import com.stargem.entity.Entity;
import com.stargem.entity.EntityManager;
import com.stargem.entity.components.RenderableSkinned;
import com.stargem.graphics.RepresentationManager;

/**
 * AnimationTask.java
 *
 * @author 	Chris B
 * @date	23 Apr 2014
 * @version	1.0
 */
public class AnimationTask extends AbstractTask {

	private String animationName;
	private AnimationController animation;
	
	private static final Array<AnimationTask> pool = new Array<AnimationTask>();
	
	public static AnimationTask newInstance(BehaviourStrategy behaviour, AIBrain brain, Entity entity, AbstractTask parent, String animationName, boolean isBlocking, int mask) {
		if(pool.size == 0) {
			return new AnimationTask(behaviour, brain, entity, parent, animationName, isBlocking, mask);
		}
		else {
			AnimationTask task = pool.pop();
			task.behaviour = behaviour;
			task.brain = brain;
			task.entity = entity;
			task.parent = parent;
			task.animationName = animationName;
			task.isBlocking = isBlocking;
			task.mask = mask;
			
			RenderableSkinned skinned = EntityManager.getInstance().getComponent(entity, RenderableSkinned.class);
			task.animation = RepresentationManager.getInstance().getAnimationController(skinned.modelIndex);
			
			return task;
		}
	}
	
	/**
	 * @param brain
	 * @param entity
	 * @param parent
	 */
	private AnimationTask(BehaviourStrategy behaviour, AIBrain brain, Entity entity, AbstractTask parent, String animationName, boolean isBlocking, int mask) {
		super(behaviour, brain, entity, parent);
		super.mask = mask;
		super.isBlocking = isBlocking;
		this.animationName = animationName;
		
		// grab the animation controller
		RenderableSkinned skinned = EntityManager.getInstance().getComponent(entity, RenderableSkinned.class);
		animation = RepresentationManager.getInstance().getAnimationController(skinned.modelIndex);
		
	}

	/* (non-Javadoc)
	 * @see com.stargem.ai.tasks.AbstractTask#update(float)
	 */
	@Override
	public boolean update(float delta) {
		if(!animation.inAction && !isStarted) {
			// we need to start
			animation.setAnimation(animationName, 1);
			animation.paused = false;
			isStarted = true;
		}
		else if (!animation.inAction && isStarted) {
			// we are done
			return true;
		}		
		
		return false;
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
		this.animationName = null;
		this.isBlocking = false;
		this.mask = 0;
		this.animation = null;
		pool.add(this);
	}
	
}