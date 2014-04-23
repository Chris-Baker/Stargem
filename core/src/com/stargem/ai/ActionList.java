/**
 * 
 */
package com.stargem.ai;

import com.badlogic.gdx.utils.Array;

/**
 * http://sonargame.com/2011/06/05/action-lists/#more-58
 * ActionList.java
 *
 * @author 	
 * @date	21 May 2013
 * @version	1.0
 */
public class ActionList {
	
	private final Array<Task> tasks = new Array<Task>();
	
	public void update(float delta) {
				
		int blockMask = 0;
		Task task;
		
		for(int i = tasks.size - 1; i >= 0; i -= 1) {
			task = tasks.get(i);
						
			// if any of this task's lanes are blocked, do not execute the task
			if((task.getMask() & blockMask) != 0) {
				continue;
			}
			
			// execute the task, and remove it if it is complete
			if(task.update(delta)) {
				tasks.removeIndex(i);
				i -= 1;
				continue;
			}
			
			// if the task is blocking, then OR its mask with the blocking mask,
		    // which will cause all following actions with any of the same bits
		    // set to be blocked
			if (task.isBlocking()) {
				blockMask |= task.getMask();
			}
			
		}
		
	}
	
	/**
	 * Set the task's action list and add it the the list of
	 * tasks belonging to this action list.
	 * 
	 * @param t
	 */
	public void push(Task t) {
		t.setActionList(this);
		this.tasks.add(t);
	}
	
}