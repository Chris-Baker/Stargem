package com.stargem.ai;

import com.badlogic.gdx.utils.IdentityMap;
import com.stargem.entity.Entity;
import com.stargem.entity.EntityManager;
import com.stargem.entity.EntityRecycleListener;

/**
 * The threat list keeps track of the perceived danger other entities
 * pose to an AI brain.
 *
 * @author 	Chris B
 * @date	22 Apr 2014
 * @version	1.0
 */
public class ThreatList implements EntityRecycleListener {
	
	//private final IntIntMap threats;
	//private final IntIntMap removed;
	private final IdentityMap<Integer, Integer> threats;
	private final IdentityMap<Integer, Integer> removed;
	
	
	/**
	 * The threat list keeps track of the perceived danger other entities
	 * pose to an AI brain.
	 * 
	 * @param entities
	 * @param threats
	 */
	public ThreatList() {
		super();
		this.threats = new IdentityMap<Integer, Integer>();
		this.removed = new IdentityMap<Integer, Integer>();
		EntityManager.getInstance().registerEntityRecycleListener(this);
	}

	/**
	 * Increase the threat for the given entity by the given amount
	 * 
	 * @param entity
	 * @param amount
	 */
	public void increaseThreat(int entity, int amount) {
		
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
	public void decreaseThreat(int entity, int amount) {
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
	public void removeThreat(int entity) {
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
	public void removeAndRememberThreat(int entity) {
		if(threats.containsKey(entity)) {
			removed.put(entity, threats.get(entity));
			threats.remove(entity);
		}
	}

	public void removeRemembered(int entity) {
		if(removed.containsKey(entity)) {
			removed.remove(entity);
		}
	}
	
	/**
	 * The size of the threat list
	 * 
	 * @return the size of the threat list
	 */
	public int size() {
		return threats.size;
	}

	/**
	 * Get the biggest threat on the threat list
	 * 
	 * @return the biggest threat on the threat list
	 */
	public Entity getBiggestThreat() {
		int id = -1;
		int biggestThreat = Integer.MIN_VALUE;
		int amount = Integer.MIN_VALUE;
		
		for(int e : threats.keys()) {
			amount = threats.get(e);			
			if(amount > biggestThreat) {
				id = e;
			}
		}
		Entity entity = EntityManager.getInstance().getEntityByID(id);
		return entity;
	}

	/* (non-Javadoc)
	 * @see com.stargem.entity.EntityRecycleListener#recycle(int)
	 */
	@Override
	public void recycle(int entityId) {
		this.removeThreat(entityId);
		this.removeRemembered(entityId);
	}
}