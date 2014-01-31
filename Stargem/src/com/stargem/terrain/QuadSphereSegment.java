/**
 * 
 */
package com.stargem.terrain;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * QuadSphereSegment.java
 * 
 * A quad sphere segment is a section of one of the faces of the cube which makes up
 * the quad sphere.
 * 
 * @author Chris B
 * @date 20 Jun 2013
 * @version 1.0
 */
public class QuadSphereSegment {

	// the cube map is laid out like so
	//   2
	// 1 4 0 5
	//   3
	public static final int RIGHT = 0;
	public static final int LEFT = 1;
	public static final int TOP = 2;
	public static final int BOTTOM = 3;
	public static final int FRONT = 4;
	public static final int BACK = 5;

	// we want all dimensions to be the same so we have a perfect cube
	// therefore width^2 represents the total number of vertices in this segment 
	protected int width;

	// the direction this segment faces north, south, east, west, up, down
	protected int orientation;

	// the scale is the width and height of each quad in world coordinates
	protected int scale;

	// this is the bottom left vertex position of this segment
	protected float offsetX, offsetY, offsetZ;

	// the vertices, normals and uv coords of this segment
	protected Vector3[][] vertices;
	protected Vector3[][] normals;
	protected Vector2[][] uvCoords;
	
	// tmp vector used in uv mapping
	private final Vector2 tmp = new Vector2();
	
	// whether or not the surface normals should be reversed
	private final boolean isInverted;
	
	// these are all used when calculating the smoothed vertex normal of a vertex
	//     c
	//   / | \
	//  b--v--d
	//   \ | /
	//     a
	private final Vector3 v = new Vector3();
	private final Vector3 a = new Vector3();
	private final Vector3 b = new Vector3();
	private final Vector3 c = new Vector3();
	private final Vector3 d = new Vector3();
	private final Vector3 abv = new Vector3();
	private final Vector3 bcv = new Vector3();
	private final Vector3 cdv = new Vector3();
	private final Vector3 dav = new Vector3();
	private final Vector3 norm = new Vector3();
	
	/**
	 * A quad sphere segment is a section of one of the faces of the cube which makes up
	 * the quad sphere. The starting coordinates given are the first vertex of the segment, the
	 * bottom left corner in the segments local coordinates
	 * 
	 * @param width the number of vertices across
	 * @param orientation which face on the quad sphere is this
	 * @param scale how many meters between each vertex
	 * @param startX the x coordinate of the first vertex of the segment
	 * @param startY the y coordinate of the first vertex of the segment
	 * @param startZ the z coordinate of the first vertex of the segment
	 * @param isInverted whether or not the surface normals should be reversed
	 */
	public QuadSphereSegment(int width, int orientation, int scale, float startX, float startY, float startZ, boolean isInverted) {

		// orientation must be 0 - 5
		if (orientation < 0 || orientation > 5) {
			throw new GdxRuntimeException("Unknown orientation " + orientation + ". Orientation must be 0 - 5");
		}

		// width must be 2 or more
		if (width < 2) {
			throw new GdxRuntimeException("Width must be 2 or more. Width represents the number of vertices in each dimension.");
		}

		if (scale < 1) {
			throw new GdxRuntimeException("Scale must be 1 or more");
		}

		this.orientation = orientation;
		this.width = width;
		this.scale = scale;
		this.offsetX = startX;
		this.offsetY = startY;
		this.offsetZ = (orientation == TOP || orientation == RIGHT || orientation == BACK) ? -startZ : startZ;
		this.isInverted = isInverted;
		
		this.vertices = new Vector3[width][width];
		this.normals = new Vector3[width][width];
		this.uvCoords = new Vector2[width][width];

		this.initVertices();

	}

	/**
	 * 
	 */
	public void initVertices() {
		// calculate vertex positions and uv coords
		for (int y = 0; y < width; y += 1) {
			for (int x = 0; x < width; x += 1) {

				// vertex position
				float px;
				float py;
				float pz;

				// this converts the segment from local to world coordinates
				if (orientation == BACK || orientation == FRONT) {
					px = x + offsetX;
					py = y + offsetY;
					pz = offsetZ;
				}
				else if (orientation == RIGHT || orientation == LEFT) {
					px = offsetZ;
					py = x + offsetX;
					pz = y + offsetY;
				}
				else if (orientation == TOP || orientation == BOTTOM) {
					px = x + offsetX;
					py = offsetZ;
					pz = y + offsetY;
				}
				else {
					throw new GdxRuntimeException("Unknown orientation " + orientation + ". Orientation must be 0 - 5");
				}

				Vector3 vertex = new Vector3(px, py, pz);
				vertex.nor();
				//vertex.scl(scale + (r.nextFloat() / 2));
				vertex.scl(scale);
				

				// UV coordinates 
				float u;
				float v;			
				
				// evenly space the texture across the face
				u = x * (1.0f / (width - 1));
				v = y * (1.0f / (width - 1));
				
				// fix the orientation of the texture depending on which face this is
				if(orientation == RIGHT) {
					// rotate cw 90 degrees
					tmp.set(u, v);
					tmp.sub(0.5f, 0.5f);
					tmp.rotate(270);
					tmp.add(0.5f, 0.5f);
					u = tmp.x;
					v = tmp.y;
				}
				else if(orientation == LEFT) {
					// rotate cw 90 degrees
					tmp.set(u, v);
					tmp.sub(0.5f, 0.5f);
					tmp.rotate(90);
					tmp.add(0.5f, 0.5f);
					u = tmp.x;
					v = tmp.y;
					
					// vertical flip
					v = 1 - v;
				}
				else if(orientation == TOP) {
					// vertical flip
					v = 1 - v;
				}
				else if(orientation == BOTTOM) {
					// ok
				}
				else if(orientation == FRONT) {
					// vertical flip
					v = 1 - v;
				}
				else if(orientation == BACK) {
					// rotate 180 degrees
					tmp.set(u, v);
					tmp.sub(0.5f, 0.5f);
					tmp.rotate(180);
					tmp.add(0.5f, 0.5f);
					u = tmp.x;
					v = tmp.y;
				}
				else {
					throw new GdxRuntimeException("Unknown orientation " + orientation + ". Orientation must be 0 - 5");
				}
				
				Vector2 uv = new Vector2(u, v);

				// add coords and position
				vertices[x][y] = vertex;
				uvCoords[x][y] = uv;

			}
		}
	}

	/**
	 * calculate the surface normal of each triangle; abv, bcv, cdv, dav
	 * then add them together and normalise them to get the mean surface normal
	 * the mean normal is then set as v's normal.
	 * 
	 * If we are at a face seam then the next face over is not grabbed the current
	 * face is used instead. This could be made more accurate by grabbing the vertex
	 * from the adjacent face, however that is more complicated and so far the current
	 * setup gives good results.
	 * 
	 * (a - v) X (b - v) ... etc.
	 * 
	 *     c
	 *   / | \
	 *  b--v--d
	 *   \ | /
	 *     a
	 */	 
	public void initNormals() {
		// calculate smooth normals
		for (int y = 0; y < width; y += 1) {
			for (int x = 0; x < width; x += 1) {			 

				v.set(vertices[x][y]);
				
				// deal with edge case we are at the bottom of the segment
				if (y == 0) {
					a.set(vertices[x][y]);
				}
				else {
					a.set(vertices[x][y - 1]);
				}
				
				// deal with edge case we are at the left of the segment
				if (x == 0) { 
					b.set(vertices[x][y]);
				} 
				else {
					b.set(vertices[x - 1][y]);
				}
				
				// deal with edge case we are at the top of the segment
				if (y == width - 1) {
					c.set(vertices[x][y]);
				}
				else {
					c.set(vertices[x][y + 1]);
				}
				
				// deal with edge case we are at the right of the segment
				if (x == width - 1) {
					d.set(vertices[x][y]);
				}
				else {
					d.set(vertices[x + 1][y]);
				}

				a.sub(v);
				b.sub(v);
				c.sub(v);
				d.sub(v);

				abv.set(a).crs(b);
				bcv.set(b).crs(c);
				cdv.set(c).crs(d);
				dav.set(d).crs(a);

				norm.set(0, 0, 0).add(abv).add(bcv).add(cdv).add(dav).nor();
				
				// these normals are flipped because the faces are flipped
				if(this.isInverted) {
					if(orientation == TOP || orientation == LEFT || orientation == FRONT) {
						norm.scl(-1);
					}
				}
				else {
					if(orientation == BOTTOM || orientation == RIGHT || orientation == BACK) {
						norm.scl(-1);
					}
				}
				normals[x][y] = norm;
			}
		}
	}

	/**
	 * Accessor for the vertices of the segment
	 * 
	 * @return
	 */
	public Vector3[][] getVertices() {
		return this.vertices;
	}

	/**
	 * Accessor for the normals of the segment
	 * 
	 * @return
	 */
	public Vector3[][] getNormals() {
		return this.normals;
	}

	/**
	 * Accessor for the UV coordinates of the segment
	 * 
	 * @return
	 */
	public Vector2[][] getUVCoords() {
		return this.uvCoords;
	}

	/**
	 * Accessor for the orientation of the segment
	 * 
	 * @return
	 */
	public int getOrientation() {
		return this.orientation;
	}

}