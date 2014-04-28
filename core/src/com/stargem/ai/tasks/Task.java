/**
 * 
 */
package com.stargem.ai.tasks;


/**
 * Task.java
 *
 * @author 	Chris B
 * @date	22 Apr 2014
 * @version	1.0
 */
public interface Task {

	public boolean update(float delta);
	public int getMask();
	public boolean isBlocking();
	
}