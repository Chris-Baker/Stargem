/**
 * 
 */
package com.stargem.ai;

import com.badlogic.gdx.utils.IntMap;


/**
 * AIManager.java
 *
 * @author 	Chris B
 * @date	22 Mar 2014
 * @version	1.0
 */
public class AIManager {

	private final IntMap<AIBrain> brains;
	
	private final static AIManager instance = new AIManager();
	public static AIManager getInstance() {
		return instance;
	}
	private AIManager() {
		brains = new IntMap<AIBrain>();
	}
	
	/**
	 * map the brain to the entity. Brains are controlled and updated
	 * indirectly by the controller manager. This allows the entity
	 * owning the brain to be switched to network or player control easily.
	 * 
	 * @param entity
	 * @param brain
	 */
	public void addBrain(int entityID, AIBrain brain) {
		brains.put(entityID, brain);
	}
	
	/**
	 * Remove the brain from the brain map
	 * 
	 * @param entityID
	 */
	public void removeBrain(int entityID) {
		brains.remove(entityID);
	}
	
	/**
	 * Return the brain mapped to the given entity
	 * 
	 * @param entity
	 * @return the brain mapped to the given entity
	 */
	public AIBrain getBrain(int entityID) {
		return brains.get(entityID);
	}
	
}