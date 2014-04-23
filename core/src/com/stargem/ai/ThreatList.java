package com.stargem.ai;

import com.badlogic.gdx.utils.IdentityMap;
import com.stargem.entity.Entity;

/**
 * The threat list keeps track of the perceived danger other entities
 * pose to an AI brain.
 *
 * @author 	Chris B
 * @date	22 Apr 2014
 * @version	1.0
 */
public class ThreatList {

	private final IdentityMap<Entity, Integer> threats;
	private final IdentityMap<Entity, Integer> removed;
	
	/**
	 * The threat list keeps track of the perceived danger other entities
	 * pose to an AI brain.
	 * 
	 * @param entities
	 * @param threats
	 */
	public ThreatList() {
		super();
		this.threats = new IdentityMap<Entity, Integer>();
		this.removed = new IdentityMap<Entity, Integer>();
	}

	/**
	 * Increase the threat for the given entity by the given amount
	 * 
	 * @param entity
	 * @param amount
	 */
	public void increaseThreat(Entity entity, int amount) {
		
		// check for a remembered threat
		if(removed.containsKey(entity)) {
			threats.put(entity, removed.get(entity));
			removed.remove(entity);
		}
		
		if(!threats.containsKey(entity)) {			
			threats.put(entity, amount);
		}
		else {
			threats.put(entity, amount + threats.get(entity));
		}
	}

	/**
	 * Decrease the threat for the given entity by the amount given.
	 * The threat level cannot go below 0.
	 * 
	 * @param entity
	 * @param amount
	 */
	public void decreaseThreat(Entity entity, int amount) {
		if(!threats.containsKey(entity)) {
			return;
		}
		else {
			int threat = (threats.get(entity) - amount > 0) ? threats.get(entity) : 0; 
			threats.put(entity, threat);
		}
	}

	/**
	 * Remove the threat and forget it. Useful if the threat is dead
	 * 
	 * @param entity
	 */
	public void removeThreat(Entity entity) {
		if(threats.containsKey(entity)) {
			threats.remove(entity);
		}
	}
	
	/**
	 * Remove the threat from the threat list but remember the entity
	 * So that if it is re-added the previous threat amount will be restored.
	 * This is useful if the threat is lost but not dead
	 * 
	 * @param entity
	 */
	public void removeAndRememberThreat(Entity entity) {
		if(threats.containsKey(entity)) {
			removed.put(entity, threats.get(entity));
			threats.remove(entity);
		}
	}
}