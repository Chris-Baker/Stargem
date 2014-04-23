/**
 * 
 */
package com.stargem.ai;

import com.stargem.entity.Entity;

/**
 * AbstractTask.java
 *
 * @author 	Chris B
 * @date	21 May 2013
 * @version	1.0
 */
public abstract class AbstractTask implements Task {

	protected static final int MASK_ANIMATION 	= (1 << 0); // 0
	protected static final int MASK_MOVEMENT 	= (1 << 1); // 1
	protected static final int MASK_BEHAVIOUR 	= (1 << 2); // 2
	protected static final int MASK_RESET 		= (1 << 3); // 4
	
	protected int mask;
	protected boolean isBlocking;
	protected Entity entity;
	protected AbstractTask parent;
	protected ActionList actionList;
	protected boolean isStarted;
	
	public AbstractTask(Entity entity, AbstractTask parent) {
		this.entity = entity;
		this.parent = parent;
		this.isStarted = false;
	}
	
	@Override
	public abstract boolean update(float delta);
	
	@Override
	public int getMask() {
		return mask;
	}
	
	@Override
	public boolean isBlocking() {
		return isBlocking;
	}
	
	@Override
	public void setActionList(ActionList l) {
		actionList = l;
	}
}