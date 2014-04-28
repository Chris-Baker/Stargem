/**
 * 
 */
package com.stargem.entity.components;

/**
 * SkillModifiers.java
 *
 * @author 	Chris B
 * @date	25 Apr 2014
 * @version	1.0
 */
public class SkillModifiers extends AbstractComponent {

	// there are 3 main modifiers. Each increase is tracked here but
	// the actual values are kept in the individual components 
	// These tracker values are used to cap the increase amounts
	// this could be used to have different player classes.
	
	// skill level increases weapon damage
	public int damageIncrease;
	
	// health increases maximum health
	public int healthIncrease;
	
	// run speed increases maximum velocity
	public int speedIncrease;
	
}