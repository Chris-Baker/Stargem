/**
 * 
 */
package com.stargem.utils;

/**
 * StringHelper.java
 *
 * @author 	Chris B
 * @date	6 Nov 2013
 * @version	1.0
 */
public class StringHelper {

	private static final StringBuilder builder = new StringBuilder();
	
	/**
	 * Resets and returns the string builder object
	 * @return the string builder object
	 */
	public static StringBuilder getBuilder() {
		builder.setLength(0);
		return builder;
	}
}