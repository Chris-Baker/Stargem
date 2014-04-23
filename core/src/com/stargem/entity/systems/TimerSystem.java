/**
 * 
 */
package com.stargem.entity.systems;

import com.stargem.entity.Entity;
import com.stargem.entity.components.Timer;
import com.stargem.entity.components.Trigger;
import com.stargem.scripting.ScriptManager;

/**
 * TimerSystem.java
 *
 * @author 	Chris B
 * @date	12 Mar 2014
 * @version	1.0
 */
public class TimerSystem extends AbstractSystem {

	@Override
	public void process(float delta) {
		super.entities = em.getAllEntitiesPossessingComponent(Timer.class);
		super.process(delta);
	}
	
	/* (non-Javadoc)
	 * @see com.stargem.entity.systems.AbstractSystem#process(float, com.stargem.entity.Entity)
	 */
	@Override
	public void process(float delta, Entity entity) {
		
		Timer timer = em.getComponent(entity, Timer.class);
		
		timer.timeLeft -= delta;
		
		if(timer.timeLeft <= 0) {
			
			// remove the timer component
			em.removeComponent(entity, Timer.class);
			
			// call a trigger if one exists
			Trigger trigger = em.getComponent(entity, Trigger.class);
			
			if(trigger != null) {				
				ScriptManager.getInstance().execute("triggers", trigger.name, entity);				
			}
			
		}
		
	}

}