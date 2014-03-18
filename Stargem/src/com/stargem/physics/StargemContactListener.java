/**
 * 
 */
package com.stargem.physics;

import com.badlogic.gdx.physics.bullet.ContactListener;
import com.badlogic.gdx.physics.bullet.btRigidBody;
import com.stargem.Config;
import com.stargem.scripting.ScriptManager;
import com.stargem.utils.Log;

/**
 * StargemContactListener.java
 *
 * @author 	Chris B
 * @date	5 Mar 2014
 * @version	1.0
 */
public class StargemContactListener extends ContactListener {
	
//	@Override
//	public void onContactProcessed (int userValue0, boolean match0, int userValue1, boolean match1) {
//		
//		Log.info(Config.PHYSICS_ERR, "contact");
//		
//	}

	@Override
	public void onContactStarted (int userValue0, boolean match0, int userValue1, boolean match1) {		
		Log.info(Config.PHYSICS_ERR, "contact started");
		
		// can the collision solver be written entirely in the scripting environment?
		
		// it would be nice to avoid using components to identify pickups and such
		// and just use the bit mask on the physics body and trigger or callback scripts
		
		// differentiate between triggers which take one entity, the one that called it
		// and collisions which take two entities
		
		// get the physics manager instance so we can get the actual physics bodies
		PhysicsManager physicsManager = PhysicsManager.getInstance();
		
		// get the two physics bodies
		btRigidBody bodyA = physicsManager.getRigidBody(userValue0);
		btRigidBody bodyB = physicsManager.getRigidBody(userValue1);
		
		// send the bodies to the collision resolver script to be handled
		ScriptManager.getInstance().execute("collisions", "resolver", bodyA, bodyB);
	}
	
	@Override
	public void onContactEnded (int userValue0, boolean match0, int userValue1, boolean match1) {		
		Log.info(Config.PHYSICS_ERR, "contact ended");
	}
	
}