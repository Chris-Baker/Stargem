/**
 * 
 */
package com.stargem.weapons;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.IntMap;
import com.stargem.entity.Entity;
import com.stargem.entity.components.Weapon;
import com.stargem.physics.PhysicsManager;

/**
 * Handles the registering, equipping, and shooting of weapons
 *  
 * @author Chris B
 * @date 26 Apr 2014
 * @version 1.0
 */
public class WeaponManager {

	private final WeaponNotMeRayResultCallback rayTestCB = new WeaponNotMeRayResultCallback(Vector3.Zero, Vector3.Z);;
	private final Vector3 hit = new Vector3();

	private final IntMap<WeaponStrategy> weapons;

	private final static WeaponManager instance = new WeaponManager();

	public static WeaponManager getInstance() {
		return instance;
	}

	private WeaponManager() {
		this.weapons = new IntMap<WeaponStrategy>();
	}

	/**
	 * Register a weapon type with the manager. The weapon type can then be used by passing
	 * it a weapon component. The component visits the weapon which alters the component
	 * 
	 * @param weapon the weapon strategy represent a weapon type
	 * @param id a power of two id
	 */
	public void registerWeapon(int id, WeaponStrategy weapon) {
		if (!MathUtils.isPowerOfTwo(id)) {
			throw new GdxRuntimeException("Weapon id must be a power of two");
		}
		this.weapons.put(id, weapon);
	}

	/**
	 * Switch to the given weapon id if the weapon component is
	 * allowed to equip it
	 * 
	 * @param id the weapon to switch to
	 * @param weapon the weapon component to update
	 */
	public void switchToWeapon(int id, Weapon weapon) {
		if ((id & weapon.weapons) > 0) {
			WeaponStrategy weaponStrategy = this.weapons.get(weapon.currentWeapon);
			weapon.currentWeapon = id;
			weapon.maxHeat = weaponStrategy.getMaxHeat();
			weapon.heatRate = weaponStrategy.getHeatRate();
			weapon.coolRate = weaponStrategy.getCoolRate();
			weapon.overHeatingPenalty = weaponStrategy.getOverHeatingPenalty();
		}
	}

	/**
	 * shoot the given weapon from the location specified to the target location
	 * the implementation of the weapon is in the Weapons.lua file.
	 * 
	 * The appropriate strategy is used to fire the weapon, either raycast or
	 * projectile. This is selected based on the interface that has been implemented
	 * 
	 * @param entity
	 * @param weapon
	 * @param rayFrom
	 * @param rayTo
	 */
	public void shoot(Entity entity, Weapon weapon, Vector3 from, Vector3 to) {
		
		// get the weapon strategy for this weapon
		WeaponStrategy weaponStrategy = this.weapons.get(weapon.currentWeapon);

		// delegate to the correct shoot method
		if (weaponStrategy instanceof ProjectileWeaponStrategy) {
			ProjectileWeaponStrategy projectile = (ProjectileWeaponStrategy) weaponStrategy;
			shoot(projectile, entity, weapon, from, to);
		}
		else if (weaponStrategy instanceof RaycastWeaponStrategy) {						
			RaycastWeaponStrategy raycast = (RaycastWeaponStrategy) weaponStrategy;
			shoot(raycast, entity, weapon, from, to);
		}
		else {
			throw new GdxRuntimeException("Unknown weapon type " + weaponStrategy.getClass().getSimpleName());
		}
	}

	/**
	 * Shoots the projectile weapon from the location specified in the forward direction.
	 * No target entity is selected, this is handled by collisions with the projectile spawned.
	 * 
	 * @param entity
	 * @param weapon
	 * @param from
	 * @param to
	 */
	private void shoot(ProjectileWeaponStrategy weaponStrategy, Entity entity, Weapon weapon, Vector3 from, Vector3 forward) {
		weaponStrategy.shoot(entity, weapon, from, forward);
	}

	/**
	 * Casts a ray to the target location, if an entity is hit then it is passed to 
	 * the weapon implementation to be damaged. If not then null is passed.
	 * 
	 * @param entity
	 * @param weapon
	 * @param from
	 * @param to
	 */
	private void shoot(RaycastWeaponStrategy weaponStrategy, Entity entity, Weapon weapon, Vector3 from, Vector3 to) {
		
		// Because we reuse the ClosestRayResultCallback, we need reset it's values
		rayTestCB.setCollisionObject(null);
		rayTestCB.setClosestHitFraction(1f);
		rayTestCB.getRayFromWorld().setValue(from.x, from.y, from.z);
		rayTestCB.getRayToWorld().setValue(to.x, to.y, to.z);
		rayTestCB.setMe(PhysicsManager.getInstance().getRigidBody(entity.getId()));
		
		PhysicsManager.getInstance().rayTest(from, to, rayTestCB);

		if (rayTestCB.hasHit()) {
			hit.set(rayTestCB.getHitPointWorld().getX(), rayTestCB.getHitPointWorld().getY(), rayTestCB.getHitPointWorld().getZ());

			final btCollisionObject obj = rayTestCB.getCollisionObject();
			if (obj.userData instanceof Entity) {
				Entity targetEntity = (Entity) obj.userData;
				weaponStrategy.shoot(entity, weapon, from, hit, targetEntity);
			}
			else {
				weaponStrategy.shoot(entity, weapon, from, hit, null);
			}
		}
		else {
			weaponStrategy.shoot(entity, weapon, from, to, null);
		}
	}

}