/**
 * 
 */
package com.stargem.ai.tasks;

import com.stargem.ai.AIBrain;
import com.stargem.behaviour.BehaviourStrategy;
import com.stargem.entity.Entity;

/**
 * The idle task awaits a threat before waking up
 * and going into combat mode.
 * 
 * This task acts like a state machine
 * 
 * IdleTask.java
 *
 * @author 	Chris B
 * @date	23 Apr 2014
 * @version	1.0
 */
public class IdleTask extends AbstractTask {
	
	// is this brain awake
	private boolean isAwake;
	
	/**
	 * @param entity
	 * @param parent
	 */
	public IdleTask(BehaviourStrategy behaviour, AIBrain brain, Entity entity, AbstractTask parent) {
		super(behaviour, brain, entity, parent);
		super.isBlocking = true;
		super.mask = AbstractTask.MASK_BEHAVIOUR;
		super.isStarted = true;
		this.isAwake = false;
	}

	/* (non-Javadoc)
	 * @see com.stargem.ai.tasks.AbstractTask#update(float)
	 */
	@Override
	public boolean update(float delta) {
		
		// wait for an entity to enter the threat list
		if(brain.inCombat()) {
			
			// if the brain was not previously in combat then wake it up
			if(!this.isAwake) {
				this.isAwake = true;
				this.onWakeUp();
			}
			else {
				this.onCombat();
			}
		}		
		else {
			if(this.isAwake) {
				this.isAwake = false;
				this.onSleep();
			}
			else {
				// not in combat so just idle away
				// push idle animation, play idle sounds etc.
				this.onIdle();
			}
		}		
		
		// never remove the idle task from the list
		return false;
	}

	/**
	 * Call the injected behaviour's onIdle method
	 */
	public void onIdle() {
		behaviour.onIdle();
	}

	/**
	 * Call the injected behaviour's onSleep method
	 */
	public void onSleep() {
		behaviour.onSleep();
	}

	/**
	 * Call the injected behaviour's onWakeUp method
	 */
	public void onWakeUp() {
		behaviour.onWakeUp();
	}

	/**
	 * Call the injected behaviour's onCombat method
	 */
	public void onCombat() {
		behaviour.onCombatStarted();
	}
	
}