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
public class TerrainSphere {

	public static final int NUM_FACES = 6;
	private final int scale;
	protected int segmentWidth;
	protected int numSegments;
	protected float segmentOffset;
	protected QuadSphereSegment[][][] segments;
	
	
	/**
	 * 
	 * 
	 * @param scale
	 * @param segmentWidth
	 * @param numSegments the number of segments
	 */
	public TerrainSphere(int scale, int segmentWidth, int numSegments) {
		super();
		this.scale = scale;
		this.segmentWidth = segmentWidth;
		this.numSegments = numSegments;
		this.segmentOffset = -((float)numSegments / 2);
		this.segments = new QuadSphereSegment[NUM_FACES][numSegments][numSegments];
		this.initSegments();
	}

	private void initSegments() {
		
		// create all the terrain segments
		for (int orientation = 0; orientation < NUM_FACES; orientation += 1) {
			for (int segmentNumY = 0; segmentNumY < numSegments; segmentNumY += 1) {
				for (int segmentNumX = 0; segmentNumX < numSegments; segmentNumX += 1) {
					
					float startX = (segmentOffset * (segmentWidth - 1)) + ((segmentWidth - 1) * segmentNumX);
					float startY = (segmentOffset * (segmentWidth - 1)) + ((segmentWidth - 1) * segmentNumY);
					float startZ = (segmentOffset * (segmentWidth - 1));

					// create the terrain segment at the calculated location
					QuadSphereSegment s = new QuadSphereSegment(segmentWidth, orientation, scale, startX, startY, startZ, false);
					
					// add the segment to the array for later processing
					segments[orientation][segmentNumX][segmentNumY] = s;
					s.initNormals();
				}
			}
		}
		
		// This would only need to be called seperately if we are grabbing normals from neighboring segments
		// we are not, so this has been left out. Normals are calculated in the above loops
		// now that all segments are created calculate the surface normals for the lighting
//		for (int orientation = 0; orientation < NUM_FACES; orientation += 1) {
//			for (int segmentNumY = 0; segmentNumY < numSegments; segmentNumY += 1) {
//				for (int segmentNumX = 0; segmentNumX < numSegments; segmentNumX += 1) {
//					
//					segments[orientation][segmentNumX][segmentNumY].initNormals();
//					
//				}
//			}
//		}
	}

	/**
	 * The number of segments wide a face is. Faces are square.
	 * 
	 * @return The number of segments wide a face is. Faces are square.
	 */
	public int getNumSegments() {
		return this.numSegments;
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
	public QuadSphereSegment[][][] getSegments() {
		return this.segments;
	}
	
}