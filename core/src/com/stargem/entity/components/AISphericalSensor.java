/**
 * 
 */
package com.stargem.entity.components;

import com.stargem.physics.PhysicsManager;

/**
 * Physics.java
 * 
 * @author Chris B
 * @date 17 Nov 2013
 * @version 1.0
 */
public class AISphericalSensor extends AbstractComponent {
	
	// physics body bodyIndex in the manager
	public int bodyIndex;
			
	// shape
	public float radius;
	
	// the contact group of this body and the groups which trigger a contact callback.
	// this uses bitwise operations, the groups are found in ContactCallbackFlags
	public int contactGroup;
	public int contactWith;
	
	@Override
	public void free() {
		// remove the associated physics object from the simulation
		PhysicsManager.getInstance().removeRigidBody(bodyIndex);
	}
}