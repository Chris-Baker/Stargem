/**
 * 
 */
package com.stargem.entity.components;

/**
 * Physics.java
 * 
 * @author Chris B
 * @date 17 Nov 2013
 * @version 1.0
 */
public class Physics extends AbstractComponent {

	// physics body bodyIndex in the manager
	public int bodyIndex;
	
	// transform matrix
	public float m00, m01, m02, m03;
	public float m04, m05, m06, m07;
	public float m08, m09, m10, m11;
	public float m12, m13, m14, m15;

	// shape
	public int type; // sphere, box, cylinder, capsule
	public float width, height, depth; // half extents
	
	// velocity
	public float angluarVelocityX, 	angluarVelocityY, 	angluarVelocityZ;
	public float linearVelocityX, 	linearVelocityY, 	linearVelocityZ;
		
	// gravity
	public float gravityX, gravityY, gravityZ;
	
	// inverse mass
	public float mass;
	
	// restitution
	public float restitution;
	
	// activation state
	public int activationState;
}