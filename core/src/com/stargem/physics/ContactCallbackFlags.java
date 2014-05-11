/**
 * 
 */
package com.stargem.physics;



/**
 * ContactCallbackFlags.java
 *
 * @author 	Chris B
 * @date	14 Mar 2014
 * @version	1.0
 */
public class ContactCallbackFlags {
	
	// The default flags go up to 128
	// CollisionFlags.CF_NO_CONTACT_RESPONSE == 4;
	
	// These are the extensions for Stargem
	public static final int TRIGGER 		= 128;
	public static final int TEAM_CFP 		= 256;
	public static final int TEAM_BOT 		= 512;
	public static final int TEAM_DMC 		= 1024;
	public static final int TEAM_PIRATE 	= 2048;
	public static final int HEALTH_PACK 	= 4096;
	public static final int POWER_CORE 		= 8192;
	public static final int SMALL_GEM 		= 16384;
	public static final int LARGE_GEM 		= 32768;
	public static final int AI_SENSOR 		= 65536;
	public static final int SPECIAL_POWER 	= 131072;
	public static final int TERRAIN 		= 262144;
	public static final int AMBIENT_SOUND 	= 524288;
	public static final int DAMAGE_ZONE 	= 1048576;
	
	/**
	 * Performs a bitwise AND to see if the flag given has any
	 * bits of the other set.
	 * 
	 * This method is used because Lua lacks any bitwise operations.
	 * 
	 * @param flag A collision flag to test
	 * @param other The collision flag to test against
	 * @return true if any bits match, false otherwise
	 */
	public static boolean compare(int flag, int other) {	
		return (flag & other) > 0;
	}
	
}