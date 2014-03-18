/**
 * 
 */
package com.stargem.entity.components;

/**
 * PlayerStats.java
 *
 * @author 	Chris B
 * @date	12 Mar 2014
 * @version	1.0
 */
public class PlayerStats extends AbstractComponent {

	// the number of power cores the player has. These are used to unlock the exits
	public int cores;
	
	// Special powers allow the player to activate some ability.
	// When this happens a special power is consumed.
	public int specials;
	
	// gems are used to purchase statistic increases in the store
	public int gems;
	
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
