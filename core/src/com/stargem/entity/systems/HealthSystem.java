/**
 * 
 */
package com.stargem.entity.systems;

import com.stargem.behaviour.BehaviourManager;
import com.stargem.behaviour.BehaviourStrategy;
import com.stargem.entity.Entity;
import com.stargem.entity.components.Health;
import com.stargem.entity.components.SkillModifiers;

/**
 * The health system clamps health to max health taking into account skill modifiers.
 * It also kills entities when they hit zero health calling relevant behaviours.
 *
 * @author 	Chris B
 * @date	12 Mar 2014
 * @version	1.0
 */
public class HealthSystem extends AbstractSystem {

	@Override
	public void process(float delta) {
		super.entities = em.getAllEntitiesPossessingComponent(Health.class);
		super.process(delta);
	}
	
	/* (non-Javadoc)
	 * @see com.stargem.entity.systems.AbstractSystem#process(float, com.stargem.entity.Entity)
	 */
	@Override
	public void process(float delta, Entity entity) {
		
		Health health = em.getComponent(entity, Health.class);
		
		int maxHealth = health.maxHealth;
		
		SkillModifiers skills = em.getComponent(entity, SkillModifiers.class);
		
		if(skills != null) {
			maxHealth += skills.healthIncrease;
		}
		
		if(health.currentHealth > maxHealth) {
			health.currentHealth = maxHealth;
		}
		
		if(health.currentHealth <= 0) {
			health.currentHealth = 0;
			BehaviourStrategy b = BehaviourManager.getInstance().getBehaviour(entity.getId());
			b.onDeath();
		}
		
	}

}