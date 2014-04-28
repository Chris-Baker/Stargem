/**
 * 
 */
package com.stargem.behaviour;

import com.badlogic.gdx.utils.IntMap;

/**
 * BehaviourManager.java
 *
 * @author 	Chris B
 * @date	25 Apr 2014
 * @version	1.0
 */
public class BehaviourManager {

	private final IntMap<BehaviourStrategy> behaviours;
	
	private final static BehaviourManager instance = new BehaviourManager();
	public static BehaviourManager getInstance() {
		return instance;
	}
	private BehaviourManager() {
		this.behaviours = new IntMap<BehaviourStrategy>();
	}
	
	/**
	 * 
	 * @param entityID
	 * @param behaviour
	 */
	public void addBehaviour(int entityID, BehaviourStrategy behaviour) {
		this.behaviours.put(entityID, behaviour);
	}
	
	/**
	 * 
	 * @param entityID
	 */
	public void removeBehaviour(int entityID) {
		this.behaviours.remove(entityID);
	}
	
	/**
	 * @param targetEntity
	 * @return
	 */
	public BehaviourStrategy getBehaviour(int entityID) {				
		return (this.behaviours.containsKey(entityID)) ? this.behaviours.get(entityID) : NullBehaviour.getInstance();
	}
	
}