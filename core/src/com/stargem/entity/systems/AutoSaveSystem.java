/**
 * 
 */
package com.stargem.entity.systems;

import com.stargem.GameManager;
import com.stargem.entity.Entity;
import com.stargem.persistence.EntitiesSavedListener;

/**
 * AutoSaveSystem.java
 *
 * @author 	Chris B
 * @date	27 Feb 2014
 * @version	1.0
 */
public class AutoSaveSystem extends AbstractTimedSystem implements EntitiesSavedListener {

	/**
	 * @param frequency in nanoseconds
	 */
	public AutoSaveSystem(float frequency) {
		super(frequency);
	}

	@Override
	public void process(float delta) {
		super.timer -= delta;
		if(super.timer <= 0) {
			super.timer = super.frequency;
			
			// show saving icon in the hud
			
			// save all entities
			GameManager.getInstance().saveGame(this);
		}		
	}
	
	
	@Override
	public void process(float delta, Entity entity) {}

	@Override
	public void finishedSaving() {
		// remove saving icon from the hud
	}

}