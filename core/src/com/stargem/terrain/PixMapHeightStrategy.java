/**
 * 
 */
package com.stargem.terrain;

import com.badlogic.gdx.graphics.Pixmap;

/**
 * PixMapHeightStrategy.java
 *
 * @author 	Chris B
 * @date	18 Apr 2014
 * @version	1.0
 */
public class PixMapHeightStrategy implements HeightStrategy {

	private final Pixmap heightMap;	
	private final int scale;
	private final int segmentWidth;
	private final int numSegments;
	private final int[][][] heights;
	
	/**
	 * @param heightMap
	 * @param scale
	 * @param segmentWidth
	 * @param numSegments
	 */
	public PixMapHeightStrategy(Pixmap heightMap, int scale, int segmentWidth, int numSegments) {
		super();
		this.heightMap = heightMap;
		this.scale = scale;
		this.segmentWidth = segmentWidth;
		this.numSegments = numSegments;
		this.heights = new int[1][1][1];
	}



	/* (non-Javadoc)
	 * @see com.stargem.terrain.HeightStrategy#getHeight(float, float, float)
	 */
	@Override
	public float getHeight(float x, float y, float z) {
		return 0;
	}

}