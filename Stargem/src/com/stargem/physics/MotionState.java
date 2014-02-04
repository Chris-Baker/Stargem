/**
 * 
 */
package com.stargem.physics;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.btMotionState;

/**
 * MotionState.java
 *
 * @author 	Chris B
 * @date	17 Nov 2013
 * @version	1.0
 */
public class MotionState extends btMotionState {
	
	protected final Matrix4 transform;
	private boolean updated;
	
	public MotionState(final Matrix4 transform) {
		this.transform = transform;
	}

	@Override
	public void getWorldTransform(Matrix4 worldTrans) {
		worldTrans.set(this.transform);
	}

	/**
	 * This is called by Bullet when the rigid body associated with the motion state
	 * is updated. This function is used to also update the component by setting the updated
	 * flag to true. The physics system will then read this and update the component when
	 * appropriate.
	 */
	@Override
	public void setWorldTransform(Matrix4 worldTrans) {
		this.transform.set(worldTrans);
		this.updated = true;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}
	
	public boolean isUpdated() {
		return this.updated;
	}
	
}