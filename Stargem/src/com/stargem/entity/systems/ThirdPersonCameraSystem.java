/**
 * 
 */
package com.stargem.entity.systems;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.stargem.entity.Entity;
import com.stargem.entity.components.Physics;
import com.stargem.entity.components.ThirdPersonCamera;

/**
 * ThirdPersonCameraSystem.java
 *
 * @author 	Chris B
 * @date	31 Jan 2014
 * @version	1.0
 */
public class ThirdPersonCameraSystem extends AbstractSystem {
	
	private final Camera camera;
	private final Vector3 targetPosition = new Vector3();;
	private final Vector3 upOffset = new Vector3();
	private final Vector3 up = new Vector3();
	private final Vector3 right = new Vector3();
	private final Vector3 out = new Vector3();	
	private final Vector3 pivot = new Vector3();
	
	public ThirdPersonCameraSystem(Camera camera) {
		this.camera = camera;
	}

	@Override
	public void process(float delta, Entity entity) {
		
		// get the third person camera component for this entity
		ThirdPersonCamera c = super.em.getComponent(entity, ThirdPersonCamera.class);
		
		// get the physics body we are following
		Physics p = super.em.getComponent(entity, Physics.class);
		
		// check this entity has camera focus
		if(c.hasFocus) {
		
			// update the camera
			this.right.set(	p.m00, p.m01, p.m02);
			this.up.set(	p.m04, p.m05, p.m06);
			this.out.set(	p.m08, p.m09, p.m10);
					
			// the camera should sit slightly to the right and behind the target
			//this.target.getTranslation(targetPosition);
			targetPosition.set(p.m12, p.m13, p.m14);
			
			this.upOffset.set(up).scl(c.heightOffset);
			
			// find the cameras pitch pivot point to the right of the target
			this.pivot.set(right).scl(-1).add(targetPosition).add(upOffset);
					
			// translate the camera backwards
			this.camera.position.set(out).scl(-c.currentDistance).add(pivot);;
			
			// set the camera forward direction to match the targets forward vector
			this.camera.direction.set(out);
					
			// set the camera up direction to stop the camera rolling as it rotates
			this.camera.up.set(up);
							
			// rotate the camera up or down
			c.pitch += c.deltaPitch * delta;
			if(c.pitch < -90) {
				c.pitch = -90;
			}
			else if (c.pitch > 90) {
				c.pitch = 90;
			}
			this.camera.rotateAround(pivot, right, c.pitch);
									
			this.camera.update();
		}
	}

}