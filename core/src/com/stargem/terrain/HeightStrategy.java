/**
 * 
 */
package com.stargem.terrain;

/**
 * HeightStrategy.java
 *
 * @author 	Chris B
 * @date	18 Apr 2014
 * @version	1.0
 */
public interface HeightStrategy {
	
	/**
	 * Given an x, y, z coordinate which represents a vector position
	 * on a quad sphere, returns the height displacement for that vector.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return the height displacement for the given vector
	 */
	float getHeight(float x, float y, float z);

}