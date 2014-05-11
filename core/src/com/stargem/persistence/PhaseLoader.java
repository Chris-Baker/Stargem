/**
 * 
 */
package com.stargem.persistence;

/**
 * 
 * PhaseLoader.java
 *
 * @author 	Chris B
 * @date	30 Apr 2014
 * @version	1.0
 */
public class PhaseLoader implements Runnable {
	
	private int phase;
	private EntitiesLoadedListener listener;

	/**
	 * Load the given phase from disk into memory
	 * 
	 * @param phase
	 */
	public PhaseLoader() {
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		PersistenceManager.getInstance().loadGates();
		PersistenceManager.getInstance().getEntityPersistence().load(phase);
		
		if(listener != null) {
			listener.finishedLoading();
			listener = null;
		}
	}
	
	public void setPhase(int phase) {
		this.phase = phase;
	}

	public void setListener(EntitiesLoadedListener l) {
		this.listener = l;
	}
	
}