/**
 * 
 */
package com.stargem.physics;

/**
 * CollisionFilterGroups.java
 *
 * @author 	Chris B
 * @date	31 Jan 2014
 * @version	1.0
 */
public class CollisionFilterGroups {

	// These are the default Bullet physics filter bits
	public final static short ALL_GROUP 		= -1;
	public final static short DEFAULT_GROUP 	= 1;
	public final static short STATIC_GROUP 		= 2;
	public final static short KINEMATIC_GROUP 	= 4;
	public final static short DEBRIS_GROUP 		= 8;
	public final static short TRIGGER_GROUP 	= 16;
	public final static short CHARACTER_GROUP 	= 32;
	
}