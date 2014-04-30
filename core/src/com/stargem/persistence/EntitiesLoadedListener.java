package com.stargem.persistence;

/**
 * EntitiesLoadedListener.java
 *
 * @author 	Chris B
 * @date	30 Apr 2014
 * @version	1.0
 */
public interface EntitiesLoadedListener {

	/**
	 * This is called once all entities have finished loading
	 */
	public void finishedLoading();
	
}