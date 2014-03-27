/**
 * 
 */
package com.stargem.entity.systems;

import com.stargem.GameManager;
import com.stargem.entity.Entity;

/**
 * AutoSaveSystem.java
 *
 * @author 	Chris B
 * @date	27 Feb 2014
 * @version	1.0
 */
public class AutoSaveSystem extends AbstractTimedSystem {

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
			
			// we don't want to process any entities we just want to save them all
			GameManager.getInstance().saveGame();
		}		
	}
	
	
	@Override
	public void process(float delta, Entity entity) {}

}