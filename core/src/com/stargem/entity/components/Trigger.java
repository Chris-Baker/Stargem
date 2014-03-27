/**
 * 
 */
package com.stargem.entity.components;

/**
 * Trigger.java
 * 
 * The trigger component holds the name of a script function to call.
 * Triggers can be activated for a number of reasons:
 * <ol>
 *   <li>When a player collides with an object that is not a character</li>
 *   <li>When a character is killed then its trigger will be called</li>
 *   <li>When a timer comes to an end</li>
 * <ol>
 * 
 * @author 	Chris B
 * @date	17 Nov 2013
 * @version	1.0
 */
public class Trigger extends AbstractComponent {

	// the name of a script function to call
	public String name;
	
}