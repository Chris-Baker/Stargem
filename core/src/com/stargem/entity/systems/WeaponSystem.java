/**
 * 
 */
package com.stargem.entity.systems;

import com.stargem.entity.Entity;
import com.stargem.entity.components.Weapon;

/**
 * Run the controller script for the entity
 * 
 * Controller.java
 *
 * @author 	24233
 * @date	12 Oct 2013
 * @version	1.0
 */
public class WeaponSystem extends AbstractSystem {

	public WeaponSystem() {
		super();
	}

	@Override
	public void process(float delta) {
		super.entities = em.getAllEntitiesPossessingComponent(Weapon.class);
		super.process(delta);
	}

	@Override
	public void process(float delta, Entity entity) {
		
		Weapon weapon = em.getComponent(entity, Weapon.class);
		
		// reduce heat
		if(!weapon.isShooting) {			
			weapon.currentHeat -= weapon.coolRate * delta;
			if(weapon.currentHeat < 0) {
				weapon.currentHeat = 0;
			}
		}
		
		// add heat if we are shooting
		if(weapon.isShooting) {
			weapon.currentHeat += weapon.heatRate * delta;
			if(weapon.currentHeat >= weapon.maxHeat) {
				weapon.currentHeat = weapon.maxHeat;
				weapon.isReady = false;
				weapon.remainingPenalty = weapon.overHeatingPenalty;
			}
		}
		
		// if we are not ready then we need to reduce the penalty
		if(!weapon.isReady) {
			weapon.remainingPenalty -= delta;
			if(weapon.remainingPenalty < 0) {
				weapon.remainingPenalty = 0;
				weapon.isReady = true;
			}
		}
		
		
		
	}
		
}