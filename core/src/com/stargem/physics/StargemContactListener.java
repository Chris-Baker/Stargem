/**
 * 
 */
package com.stargem.physics;

import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.stargem.scripting.ScriptManager;

/**
 * StargemContactListener.java
 *
 * @author 	Chris B
 * @date	5 Mar 2014
 * @version	1.0
 */
public class StargemContactListener extends ContactListener {
	
	@Override
	public void onContactStarted (int userValue0, boolean match0, int userValue1, boolean match1) {		
				
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
	}
	
}