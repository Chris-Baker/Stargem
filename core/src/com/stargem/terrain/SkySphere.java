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
public class SkySphere extends AbstractQuadSphere {
	
	/**
	 * @param scale
	 * @param segmentWidth
	 * @param numSegments the number of segments
	 */
	public SkySphere() {
		super(1, 2, 1);
		this.initSegments();
		super.calculateSmoothNormals();
	}

	@Override
	protected void initSegments() {
		
		// create all the terrain segments
		for (int orientation = 0; orientation < NUM_FACES; orientation += 1) {
			for (int segmentNumY = 0; segmentNumY < numSegments; segmentNumY += 1) {
				for (int segmentNumX = 0; segmentNumX < numSegments; segmentNumX += 1) {		
					float startX = (segmentOffset * (segmentWidth - 1));
					float startY = (segmentOffset * (segmentWidth - 1));
					float startZ = (segmentOffset * (segmentWidth - 1));
					
					// create the terrain segment at the calculated location
					QuadSphereSegment s = new QuadSphereSegment(segmentWidth, orientation, scale, startX, startY, startZ, true);
					
					// add the segment to the array for later processing
					segments[orientation][segmentNumX][segmentNumY] = s;
				}
			}
		}
	}	
}