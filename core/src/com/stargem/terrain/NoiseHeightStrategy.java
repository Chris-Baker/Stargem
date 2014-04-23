/**
 * 
 */
package com.stargem.terrain;

import com.stargem.utils.SimplexNoise;

/**
 * NoiseHeightStrategy.java
 *
 * @author 	Chris B
 * @date	18 Apr 2014
 * @version	1.0
 */
public class NoiseHeightStrategy implements HeightStrategy {
	
	private final SimplexNoise noise;
	
	/**
	 * Uses simplex noise to 
	 * 
	 * @param largestFeature
	 * @param persistence
	 * @param seed
	 */
	public NoiseHeightStrategy(int largestFeature, double persistence, int seed) {
		super();
		this.noise = new SimplexNoise(largestFeature, persistence, seed);
	}

	/* (non-Javadoc)
	 * @see com.stargem.terrain.HeightStrategy#getHeight(float, float, float)
	 */
	@Override
	public float getHeight(float x, float y, float z) {
		return (float)(noise.getNoise(x, y, z));
	}

}