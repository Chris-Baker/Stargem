/**
 * 
 */
package com.stargem.ai.tasks;

import com.stargem.ai.AIBrain;
import com.stargem.behaviour.BehaviourStrategy;
import com.stargem.entity.Entity;

/**
 * AbstractTask.java
 *
 * @author 	Chris B
 * @date	21 May 2013
 * @version	1.0
 */
public abstract class AbstractTask implements Task {

	public static final int MASK_ANIMATION 	= (1 << 0); // 0
	public static final int MASK_MOVEMENT 	= (1 << 1); // 1
	public static final int MASK_BEHAVIOUR 	= (1 << 2); // 2
	public static final int MASK_RESET 		= (1 << 3); // 4
	
	protected AIBrain brain;
	protected Entity entity;
	protected AbstractTask parent;
	protected BehaviourStrategy behaviour;
	protected int mask;
	protected boolean isBlocking;
	protected boolean isStarted;
	
	public AbstractTask(BehaviourStrategy behaviour, AIBrain brain, Entity entity, AbstractTask parent) {
		this.behaviour = behaviour;
		this.brain = brain;
		this.entity = entity;
		this.parent = parent;
		this.isStarted = false;
	}
	
	/**
	 * 
	 */
	@Override
	public abstract boolean update(float delta);
	
	/**
	 * 
	 */
	@Override
	public int getMask() {
		return mask;
	}
	
	/**
	 * 
	 */
	@Override
	public boolean isBlocking() {
		return isBlocking;
	}
	
	abstract public void free();
}