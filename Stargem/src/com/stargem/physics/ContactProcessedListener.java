/**
 * 
 */
package com.stargem.physics;

import com.badlogic.gdx.physics.bullet.ContactListener;
import com.badlogic.gdx.physics.bullet.btCollisionObject;
import com.stargem.Config;
import com.stargem.utils.Log;

/**
 * ContactProcessedListener.java
 *
 * @author 	Chris B
 * @date	5 Mar 2014
 * @version	1.0
 */
public class ContactProcessedListener extends ContactListener {
	
//	@Override
//	public void onContactProcessed (int userValue0, boolean match0, int userValue1, boolean match1) {
//		
//		Log.info(Config.PHYSICS_ERR, "contact");
//		
//	}

	@Override
	public boolean onContactAdded(btCollisionObject colObj0, int partId0, int index0, btCollisionObject colObj1, int partId1, int index1) {
		
		Log.info(Config.PHYSICS_ERR, "contact");
		
		return false;
	}
	
}