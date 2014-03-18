/**
 * 
 */
package com.stargem.terrain;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.stargem.Config;
import com.stargem.utils.Log;



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
	private final float[][][][][] heights;
	private final Pixmap heightMap;
	
	/**
	 * 
	 * 
	 * @param scale
	 * @param segmentWidth
	 * @param numSegments the number of segments
	 */
	public TerrainSphere(int scale, int segmentWidth, int numSegments, Pixmap heightMap) {
		super();
		this.scale = scale;
		this.segmentWidth = segmentWidth;
		this.numSegments = numSegments;
		this.segmentOffset = -((float)numSegments / 2);
		this.segments = new QuadSphereSegment[NUM_FACES][numSegments][numSegments];
		this.heights = new float[NUM_FACES][numSegments][numSegments][segmentWidth][segmentWidth];
		this.heightMap = heightMap;
		this.initHeights();
		this.initSegments();
	}

	/**
	 * Store the height information for each vertex. The information is taken from the
	 * height map passed to the constructor as a Pixmap. 
	 */
	private void initHeights() {
		
		// calculate the number of pixels across a face is
		int numPixelsAcross = (numSegments * (segmentWidth - 1)) + 1;
		
		// now calculate the start pixel of each face on the pixmap
		int[] startPixelsX = new int[NUM_FACES];
		int[] startPixelsY = new int[NUM_FACES];
		
		// the pixmap is laid out as a standard horizontal cross cubemap
		//   t
		// l f r b
		//   b
		startPixelsX[QuadSphereSegment.RIGHT] 	= numPixelsAcross * 2;
		startPixelsY[QuadSphereSegment.RIGHT] 	= numPixelsAcross * 2;
		
		startPixelsX[QuadSphereSegment.LEFT] 	= 0;
		startPixelsY[QuadSphereSegment.LEFT] 	= numPixelsAcross * 2;
		
		startPixelsX[QuadSphereSegment.TOP] 	= numPixelsAcross;
		startPixelsY[QuadSphereSegment.TOP] 	= numPixelsAcross;
		
		startPixelsX[QuadSphereSegment.BOTTOM] 	= numPixelsAcross;
		startPixelsY[QuadSphereSegment.BOTTOM] 	= numPixelsAcross * 3;
		
		startPixelsX[QuadSphereSegment.FRONT] 	= numPixelsAcross;
		startPixelsY[QuadSphereSegment.FRONT] 	= numPixelsAcross * 2;
		
		startPixelsX[QuadSphereSegment.BACK] 	= numPixelsAcross * 3;
		startPixelsY[QuadSphereSegment.BACK] 	= numPixelsAcross * 2;		
		
//		startPixelsX[QuadSphereSegment.RIGHT] 	= numPixelsAcross * 2;
//		startPixelsY[QuadSphereSegment.RIGHT] 	= numPixelsAcross;
//		
//		startPixelsX[QuadSphereSegment.LEFT] 	= 0;
//		startPixelsY[QuadSphereSegment.LEFT] 	= numPixelsAcross;
//		
//		startPixelsX[QuadSphereSegment.TOP] 	= numPixelsAcross;
//		startPixelsY[QuadSphereSegment.TOP] 	= numPixelsAcross * 2;
//		
//		startPixelsX[QuadSphereSegment.BOTTOM] 	= numPixelsAcross;
//		startPixelsY[QuadSphereSegment.BOTTOM] 	= 0;
//		
//		startPixelsX[QuadSphereSegment.FRONT] 	= numPixelsAcross;
//		startPixelsY[QuadSphereSegment.FRONT] 	= numPixelsAcross;
//		
//		startPixelsX[QuadSphereSegment.BACK] 	= numPixelsAcross * 3;
//		startPixelsY[QuadSphereSegment.BACK] 	= numPixelsAcross;	
		
		// all 6 faces
		for (int orientation = 0; orientation < NUM_FACES; orientation += 1) {
			
			Log.debug(Config.INFO, "\n\nFace ---------------------");
			
			// all segments
			for (int segmentNumY = 0; segmentNumY < numSegments; segmentNumY += 1) {
				for (int segmentNumX = 0; segmentNumX < numSegments; segmentNumX += 1) {
										
					// each vertex in the segment
					for(int y = 0; y < segmentWidth; y += 1) {
						for(int x = 0; x < segmentWidth; x += 1) {
							
							// get the colour value from the pixmap
							int vx = startPixelsX[orientation] + (segmentNumX * (segmentWidth - 1)) + x;
							int vy = startPixelsY[orientation] - (segmentNumY * (segmentWidth - 1)) - y;
							Color colour = new Color(this.heightMap.getPixel(vx, vy));
							
							// store the red value as the height for this vertex
							this.heights[orientation][segmentNumX][segmentNumY][x][y] = colour.r;
							
							if((segmentNumX == 0 && segmentNumY == 0)) {
								Log.debug(Config.INFO, "Start: x: " + vx + " y: " + vy);
							}
							
							if((segmentNumX == numSegments - 1 && segmentNumY == numSegments - 1)) {
								Log.debug(Config.INFO, "End  : x: " + vx + " y: " + vy);
							}
						}
					}
					
				}				
			}
		}
	}
	
	/**
	 * create all the terrain segments for the sphere
	 */
	private void initSegments() {
		
		// create all the terrain segments
		for (int orientation = 0; orientation < NUM_FACES; orientation += 1) {
			for (int segmentNumY = 0; segmentNumY < numSegments; segmentNumY += 1) {
				for (int segmentNumX = 0; segmentNumX < numSegments; segmentNumX += 1) {
					
					float startX = (segmentOffset * (segmentWidth - 1)) + ((segmentWidth - 1) * segmentNumX);
					float startY = (segmentOffset * (segmentWidth - 1)) + ((segmentWidth - 1) * segmentNumY);
					float startZ = (segmentOffset * (segmentWidth - 1));
										
					// create the terrain segment at the calculated location
					QuadSphereSegment s = new QuadSphereSegment(segmentWidth, orientation, scale, startX, startY, startZ, false, heights[orientation][segmentNumX][segmentNumY]);
					
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
	 * The array is of the shape [Num Faces][Num Segments][Num Segments]
	 * 
	 * @return The multidimensional array which stores all the segments for this sphere.
	 */
	public QuadSphereSegment[][][] getSegments() {
		return this.segments;
	}
	
}