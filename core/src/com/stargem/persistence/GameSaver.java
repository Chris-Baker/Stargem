/**
 * 
 */
package com.stargem.persistence;

/**
 * GameSaver.java
 *
 * @author 	Chris B
 * @date	27 Feb 2014
 * @version	1.0
 */
public class GameSaver implements Runnable {
	
	/**
	 * Save the game to disk not on the ui thread so that the player
	 * experiences no long pause.
	 */
	public GameSaver() {
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		PersistenceManager.getInstance().getEntityPersistence().save();
	}

}