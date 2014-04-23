/**
 * 
 */
package com.stargem.ai;

import com.stargem.entity.Entity;

/**
 * An AI Brain keeps track of an action list and a threat list.
 * The action list keeps track of behaviours and the threat list keeps track of
 * nearby enemies, storing them in an ordered list according to the threat they
 * pose to the brain.
 * 
 * AIBrain.java
 *
 * @author 	Chris B
 * @date	14 Apr 2014
 * @version	1.0
 */
public class AIBrain {

	private final ActionList actionList;
	private final ThreatList threatList;
	
	/**
	 * @param actionList
	 * @param threatList
	 */
	public AIBrain() {
		super();
		this.actionList = new ActionList();
		this.threatList = new ThreatList();
	}

	/**
	 * Update this brain, processing its action list
	 * @param delta
	 */
	public void update(float delta) {
		actionList.update(delta);		
	}
	
	/**
	 * Add a task to this brain
	 * 
	 * @param task
	 */
	public void addAction(Task task) {
		this.actionList.push(task);
	}
	
	/**
	 * Increase the threat level of the given entity.
	 * If no threat level previously existed then the entity
	 * is added to the list
	 * 
	 * @param entity
	 * @param amount
	 */
	public void increaseThreat(Entity entity, int amount) {
		this.threatList.increaseThreat(entity, amount);
	}
	
	/**
	 * Decrease the threat level of the given entity.
	 * If no threat level previously existed then the entity
	 * is not added to the list.
	 * 
	 * If the threat level drops to zero then the entity remains
	 * on the list until removed.
	 * 
	 * @param entity
	 * @param amount
	 */
	public void decreaseThreat(Entity entity, int amount) {
		this.threatList.decreaseThreat(entity, amount);
	}
	
	/**
	 * Remove the entity from the threat list altogether.s
	 * 
	 * @param entity
	 */
	public void removeThreat(Entity entity) {
		this.threatList.removeThreat(entity);
	}
}