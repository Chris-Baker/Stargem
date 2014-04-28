/**
 * 
 */
package com.stargem.entity.components;

/**
 * Inventory.java
 *
 * @author 	Chris B
 * @date	25 Apr 2014
 * @version	1.0
 */
public class Inventory extends AbstractComponent {

	// the number of power cores the player has. These are used to unlock the exits
	public int cores;
		
	// Special powers allow the player to activate some ability.
	// When this happens a special power is consumed.
	public int specials;
	
	// gems are used to purchase statistic increases in the store
	public int gems;
	
}