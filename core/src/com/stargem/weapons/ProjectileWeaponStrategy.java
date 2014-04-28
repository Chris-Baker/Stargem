/**
 * 
 */
package com.stargem.weapons;

import com.badlogic.gdx.math.Vector3;
import com.stargem.entity.Entity;
import com.stargem.entity.components.Weapon;

/**
 * This is the interface for projectile weapons to implement.
 * Implementations are in the 'Weapons.Lua' file
 *
 * @author 	Chris B
 * @date	27 Apr 2014
 * @version	1.0
 */
public interface ProjectileWeaponStrategy extends WeaponStrategy {

	/**
	 * When a weapon is successfully shot then this method is called.
	 * This method is implemented in the scripting environment
	 * 
	 * @param entity
	 * @param weapon
	 * @param from
	 * @param forward
	 */
	public void shoot(Entity entity, Weapon weapon, Vector3 from, Vector3 forward);
	
}