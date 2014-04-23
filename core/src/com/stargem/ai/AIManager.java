/**
 * 
 */
package com.stargem.ai;

import com.badlogic.gdx.utils.IdentityMap;
import com.stargem.entity.Entity;


/**
 * AIManager.java
 *
 * @author 	Chris B
 * @date	22 Mar 2014
 * @version	1.0
 */
public class AIManager {

	private final IdentityMap<Entity, AIBrain> brains;
	
	private final static AIManager instance = new AIManager();
	public static AIManager getInstance() {
		return instance;
	}
	private AIManager() {
		brains = new IdentityMap<Entity, AIBrain>();
	}
	
	/**
	 * Manage the given AI Brain
	 * 
	 * @param entity
	 * @param brain
	 */
	public void addBrain(Entity entity, AIBrain brain) {
		brains.put(entity, brain);
	}
	
	/**
	 * Return the brain mapped to the given entity
	 * 
	 * @param entity
	 * @return the brain mapped to the given entity
	 */
	public AIBrain getBrain(Entity entity) {
		return brains.get(entity);
	}	
	
	/**
	 * Update all ai brains
	 * 
	 * @param delta
	 */
	public void update(float delta) {
		for(AIBrain brain : brains.values()) {
			brain.update(delta);
		}
	}
	
}