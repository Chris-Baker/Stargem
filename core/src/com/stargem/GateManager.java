/**
 * 
 */
package com.stargem;


/**
 * GateManager.java
 *
 * @author 	Chris B
 * @date	10 Apr 2014
 * @version	1.0
 */
public class GateManager {

	private int entranceGateID;
	private int exitGateID;
	
	private static GateManager instance;
	public static GateManager getInstance() {
		if(instance == null) {
			instance = new GateManager();
		}		
		return instance;		
	}
	/**
	 * @return the entranceGateID
	 */
	public int getEntranceGateID() {
		return entranceGateID;
	}
	/**
	 * @param entranceGateID the entranceGateID to set
	 */
	public void setEntranceGateID(int entranceGateID) {
		this.entranceGateID = entranceGateID;
	}
	
	/**
	 * @return the exitGateID
	 */
	public int getExitGateID() {
		return exitGateID;
	}
	/**
	 * @param exitGateID the exitGateID to set
	 */
	public void setExitGateID(int exitGateID) {
		this.exitGateID = exitGateID;
	}
	
}