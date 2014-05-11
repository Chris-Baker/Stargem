/**
 * 
 */
package com.stargem;

import com.badlogic.gdx.Input.Keys;

/**
 * Preferences.java
 *
 * @author 	Chris B
 * @date	16 Dec 2013
 * @version	1.0
 */
public class Preferences {

	/* @formatter:off */
	
	// Keyboard input
	public static int KEY_FORWARD 					= Keys.W;
	public static int KEY_BACKWARD 					= Keys.S;
	public static int KEY_LEFT 						= Keys.A;
	public static int KEY_RIGHT 					= Keys.D;
	public static int KEY_JUMP 						= Keys.SPACE;
	public static int KEY_SPECIAL					= Keys.F;
		
	// Mouse Input
	public static int MOUSE_SENSITIVITY 			= 10;
	
	// Music and Sound
	public static boolean PLAY_MUSIC				= true;
	public static boolean PLAY_SOUNDs				= true;
	public static float   MUSIC_VOLUME				= 0.5f;
	public static float   SOUND_VOLUME				= 0.5f;
	
	/* @formatter:on */	
}