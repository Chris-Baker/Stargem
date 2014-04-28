/**
 * 
 */
package com.stargem.weapons;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.badlogic.gdx.physics.bullet.collision.LocalRayResult;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.stargem.physics.ContactCallbackFlags;

/**
 * WeaponNotMeRayResultCallback.java
 *
 * @author 	Chris B
 * @date	27 Apr 2014
 * @version	1.0
 */
public class WeaponNotMeRayResultCallback extends ClosestRayResultCallback {

	private btCollisionObject me;
	
	/**
	 * @param rayFromWorld
	 * @param rayToWorld
	 */
	public WeaponNotMeRayResultCallback(Vector3 rayFromWorld, Vector3 rayToWorld) {
		super(rayFromWorld, rayToWorld);
	}

	@Override
	public float addSingleResult(LocalRayResult rayResult, boolean normalInWorldSpace) {
		
		btCollisionObject hit = rayResult.getCollisionObject();
		
		// we want to ignore the object if it is me
		if(me != null && hit.equals(me)) {
			return 1.0f;
		}
		
		// we want to ignore the object if it is a trigger or an AI sensor
		if(((hit.getContactCallbackFlag() & ContactCallbackFlags.AI_SENSOR) > 0) 
		|| ((hit.getContactCallbackFlag() & ContactCallbackFlags.TRIGGER) > 0)) {
			return 1.0f;
		}
				
		return super.addSingleResult(rayResult, normalInWorldSpace);
	}
	
	public void setMe(btCollisionObject me) {
		this.me = me;
	}
	
}