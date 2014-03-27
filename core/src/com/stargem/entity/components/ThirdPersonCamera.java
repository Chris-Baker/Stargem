/**
 * 
 */
package com.stargem.entity.components;

/**
 * ThirdPersonCamera.java
 *
 * @author 	Chris B
 * @date	31 Jan 2014
 * @version	1.0
 */
public class ThirdPersonCamera extends AbstractComponent {

	// whether or not this component currently controls the 
	public boolean hasFocus;
	
	// camera distance from the target matrix
	public float minDistance;
	public float maxDistance;
	public float currentDistance;
	
	// height offset from the target matrix
	public float heightOffset;
	
	// the current pitch of the camera
	public float pitch;
	
	// the amount to change the pitch of the camera by next update
	public float deltaPitch;
	
}