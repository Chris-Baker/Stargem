/**
 * 
 */
package com.stargem.terrain;


/**
 * TerrainSphere.java
 * 
 * @author Chris B
 * @date 23 Jun 2013
 * @version 1.0
 */
public class SkySphere {

	public static final int NUM_FACES = 6;
	private final int scale;
	private final int segmentWidth;
	private final float segmentOffset;
	private final QuadSphereSegment[] segments;
	private final String skyName;
	
	/**
	 * @param scale
	 * @param segmentWidth
	 * @param numSegments the number of segments
	 */
	public SkySphere(String skyName) {
		super();
		this.skyName = skyName;
		this.scale = 1;
		this.segmentWidth = 2;
		this.segmentOffset = -0.5f;
		this.segments = new QuadSphereSegment[NUM_FACES];
		this.initSegments();
	}

	private void initSegments() {
		
		// create all the terrain segments
		for (int orientation = 0; orientation < NUM_FACES; orientation += 1) {
					
			float startX = (segmentOffset * (segmentWidth - 1));
			float startY = (segmentOffset * (segmentWidth - 1));
			float startZ = (segmentOffset * (segmentWidth - 1));

			// create the terrain segment at the calculated location
			QuadSphereSegment s = new QuadSphereSegment(segmentWidth, orientation, scale, startX, startY, startZ, true);
			s.initNormals();
			
			// add the segment to the array for later processing
			segments[orientation] = s;
		}
	}

	/**
	 * The segment width in vertices. Segments are square.
	 * 
	 * @return The segment width in vertices. Segments are square.
	 */
	public int getSegmentWidth() {
		return this.segmentWidth;
	}
	
	/**
	 * The multidimensional array which stores all the segments for this sphere.
	 * 
	 * @return The multidimensional array which stores all the segments for this sphere.
	 */
	public QuadSphereSegment[] getSegments() {
		return this.segments;
	}

	/**
	 * @return
	 */
	public String getSkyName() {
		return this.skyName;
	}
	
}