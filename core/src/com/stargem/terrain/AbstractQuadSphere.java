/**
 * 
 */
package com.stargem.terrain;

import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * AbstractQuadSphere.java
 *
 * @author 	Chris B
 * @date	27 Mar 2014
 * @version	1.0
 */
public abstract class AbstractQuadSphere {

	public static final int NUM_FACES = 6;
	protected final int scale;
	protected final int segmentWidth;
	protected final int numSegments;
	protected final float segmentOffset;
	protected final QuadSphereSegment[][][] segments;
	
	public AbstractQuadSphere(int scale, int segmentWidth, int numSegments) {
		this.scale = scale;
		this.segmentWidth = segmentWidth;
		this.numSegments = numSegments;
		this.segmentOffset = -((float)numSegments / 2);
		this.segments = new QuadSphereSegment[NUM_FACES][numSegments][numSegments];
	}

	protected abstract void initSegments();
	
	/**
	 * 
	 */
	protected void calculateSmoothNormals() {
		// calculate smooth normals for each vertex
		// we need to grab the neighbouring segments in order to do this successfully
		for (int orientation = 0; orientation < NUM_FACES; orientation += 1) {
			for (int segmentNumY = 0; segmentNumY < numSegments; segmentNumY += 1) {
				for (int segmentNumX = 0; segmentNumX < numSegments; segmentNumX += 1) {
					
					segments[orientation][segmentNumX][segmentNumY].initNormals
					(
						north(orientation, segmentNumX, segmentNumY),
						east(orientation, segmentNumX, segmentNumY),
						south(orientation, segmentNumX, segmentNumY),
						west(orientation, segmentNumX, segmentNumY)
					);
				}
			}
		}
	}
	
	/**
	 * @param orientation
	 * @param segmentNumX
	 * @param segmentNumY
	 * @return
	 */
	protected QuadSphereSegment south(int orientation, int segmentNumX, int segmentNumY) {
		
		QuadSphereSegment segment;
				
		if(segmentNumY != 0) {
			segment = segments[orientation][segmentNumX][segmentNumY - 1];
		}
		else {
			if(orientation == QuadSphereSegment.RIGHT) {
				// south is bottom east edge
				segment = segments[QuadSphereSegment.BOTTOM][numSegments - 1][(numSegments - 1) - segmentNumX];
			}
			else if(orientation == QuadSphereSegment.LEFT) {
				// south is bottom west edge
				segment = segments[QuadSphereSegment.BOTTOM][0][segmentNumX];
			}
			else if(orientation == QuadSphereSegment.TOP) {
				// south is front north edge
				segment = segments[QuadSphereSegment.FRONT][segmentNumX][numSegments - 1];
			}
			else if(orientation == QuadSphereSegment.BOTTOM) {
				// south is back south edge
				segment = segments[QuadSphereSegment.BACK][(numSegments - 1) - segmentNumX][0];
			}
			else if(orientation == QuadSphereSegment.FRONT) {
				// south is bottom north edge
				segment = segments[QuadSphereSegment.BOTTOM][segmentNumX][numSegments - 1];
			}
			else if(orientation == QuadSphereSegment.BACK) {
				// south is bottom south edge
				segment = segments[QuadSphereSegment.BOTTOM][(numSegments - 1) - segmentNumX][0];
			}
			else {
				throw new GdxRuntimeException("Unknown orientation " + orientation + ". Orientation must be 0 - 5");
			}
		}	
		return segment;
	}

	/**
	 * @param orientation
	 * @param segmentNumX
	 * @param segmentNumY
	 * @return
	 */
	protected QuadSphereSegment east(int orientation, int segmentNumX, int segmentNumY) {
		
		QuadSphereSegment segment;
		
		if(segmentNumX < (numSegments - 1)) {
			segment = segments[orientation][segmentNumX + 1][segmentNumY];
		}
		else {
			if(orientation == QuadSphereSegment.RIGHT) {
				// east is back west edge
				segment = segments[QuadSphereSegment.BACK][0][segmentNumY];
			}
			else if(orientation == QuadSphereSegment.LEFT) {
				// east is front west edge
				segment = segments[QuadSphereSegment.FRONT][0][segmentNumY];
			}
			else if(orientation == QuadSphereSegment.TOP) {
				// east is right north edge
				segment = segments[QuadSphereSegment.RIGHT][segmentNumY][numSegments - 1];
			}
			else if(orientation == QuadSphereSegment.BOTTOM) {
				// east is right south edge
				segment = segments[QuadSphereSegment.RIGHT][(numSegments - 1) - segmentNumY][0];
			}
			else if(orientation == QuadSphereSegment.FRONT) {
				// east is right west edge
				segment = segments[QuadSphereSegment.RIGHT][0][segmentNumY];
			}
			else if(orientation == QuadSphereSegment.BACK) {
				// east is left west edge
				segment = segments[QuadSphereSegment.LEFT][0][segmentNumY];
			}
			else {
				throw new GdxRuntimeException("Unknown orientation " + orientation + ". Orientation must be 0 - 5");
			}
		}		
		return segment;
	}

	/**
	 * @param orientation
	 * @param segmentNumX
	 * @param segmentNumY
	 * @return
	 */
	protected QuadSphereSegment north(int orientation, int segmentNumX, int segmentNumY) {
		
		QuadSphereSegment segment;
		
		if(segmentNumY != 0) {
			segment = segments[orientation][segmentNumX][segmentNumY - 1];
		}
		else {
			if(orientation == QuadSphereSegment.RIGHT) {
				// north is top east edge
				segment = segments[QuadSphereSegment.TOP][numSegments - 1][segmentNumX];
			}
			else if(orientation == QuadSphereSegment.LEFT) {
				// north is top west edge
				segment = segments[QuadSphereSegment.TOP][0][(numSegments - 1) - segmentNumX];
			}
			else if(orientation == QuadSphereSegment.TOP) {
				// north is back north
				segment = segments[QuadSphereSegment.BACK][numSegments - 1][(numSegments - 1) - segmentNumX];
			}
			else if(orientation == QuadSphereSegment.BOTTOM) {
				// north is front south
				segment = segments[QuadSphereSegment.BACK][0][segmentNumX];
			}
			else if(orientation == QuadSphereSegment.FRONT) {
				// north is top south
				segment = segments[QuadSphereSegment.TOP][0][segmentNumX];
			}
			else if(orientation == QuadSphereSegment.BACK) {
				// north is top north
				segment = segments[QuadSphereSegment.TOP][numSegments - 1][(numSegments - 1) - segmentNumX];
			}
			else {
				throw new GdxRuntimeException("Unknown orientation " + orientation + ". Orientation must be 0 - 5");
			}
		}
		return segment;
	}

	/**
	 * @param orientation
	 * @param segmentNumX
	 * @param segmentNumY
	 * @return
	 */
	protected QuadSphereSegment west(int orientation, int segmentNumX, int segmentNumY) {
		
		QuadSphereSegment segment;
		
		if(segmentNumX < (numSegments - 1)) {
			segment = segments[orientation][segmentNumX + 1][segmentNumY];
		}
		else {
			if(orientation == QuadSphereSegment.RIGHT) {
				// west is front east edge
				segment = segments[QuadSphereSegment.FRONT][numSegments - 1][segmentNumY];
			}
			else if(orientation == QuadSphereSegment.LEFT) {
				// west is back east edge
				segment = segments[QuadSphereSegment.BACK][numSegments - 1][segmentNumY];
			}
			else if(orientation == QuadSphereSegment.TOP) {
				// west is left north edge
				segment = segments[QuadSphereSegment.LEFT][(numSegments - 1) - segmentNumY][numSegments - 1];
			}
			else if(orientation == QuadSphereSegment.BOTTOM) {
				// west is left south edge
				segment = segments[QuadSphereSegment.LEFT][segmentNumY][0];
			}
			else if(orientation == QuadSphereSegment.FRONT) {
				// west is left east edge
				segment = segments[QuadSphereSegment.LEFT][numSegments - 1][segmentNumY];
			}
			else if(orientation == QuadSphereSegment.BACK) {
				// west is right east edge
				segment = segments[QuadSphereSegment.RIGHT][numSegments - 1][segmentNumY];
			}
			else {
				throw new GdxRuntimeException("Unknown orientation " + orientation + ". Orientation must be 0 - 5");
			}
		}		
		return segment;
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
	 * The array is of the shape [Num Faces][Num Segments][Num Segments]
	 * 
	 * @return The multidimensional array which stores all the segments for this sphere.
	 */
	public QuadSphereSegment[][][] getSegments() {
		return this.segments;
	}
}