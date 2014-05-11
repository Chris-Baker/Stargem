/**
 * 
 */
package com.stargem.weapons;


/**
 * WeaponStrategy.java
 *
 * @author 	Chris B
 * @date	26 Apr 2014
 * @version	1.0
 */
interface WeaponStrategy {
	
	public int getDamage();
	public int getRange();
	public int getMaxHeat();
	public int getHeatRate();
	public int getCoolRate();
	public int getOverHeatingPenalty();
	public float getRateOfFire();
	
}