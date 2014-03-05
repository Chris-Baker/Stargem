/**
 * 
 */
package com.stargem.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.stargem.terrain.QuadSphereSegment;
import com.stargem.terrain.TerrainSphere;

/**
 * TerrainRepresentation.java
 *
 * @author 	Chris B
 * @date	29 Jan 2014
 * @version	1.0
 */
public class TerrainRepresentation extends AbstractIterableRepresentation {

	private final int segmentWidth;
	private final int numSegments;
	private final Texture texture_1;
	private final Texture texture_2;
	private final Texture texture_3;
	
	public TerrainRepresentation(TerrainSphere terrain, Texture texture_1, Texture texture_2, Texture texture_3) {
		super(TerrainSphere.NUM_FACES, TerrainSphere.NUM_FACES * terrain.getNumSegments() * terrain.getNumSegments());
		this.segmentWidth = terrain.getSegmentWidth();
		this.numSegments = terrain.getNumSegments();
		this.texture_1 = texture_1;
		this.texture_2 = texture_2;
		this.texture_3 = texture_3;
		this.build(terrain);
	}

	/**
	 * Using the model builder create all the models for the terrain sphere
	 * 
	 * @param terrain
	 */
	private void build(TerrainSphere terrain) {
		ModelBuilder modelBuilder = new ModelBuilder();
		
		TextureAttribute diff = new TextureAttribute(TextureAttribute.Diffuse, texture_1);
		Material material = new Material(diff);
				
		for (int orientation = 0; orientation < TerrainSphere.NUM_FACES; orientation += 1) {

			// create a new mesh part per cube face
			modelBuilder.begin();
			MeshPartBuilder builder = modelBuilder.part("face_0" + orientation, GL20.GL_TRIANGLES, Usage.Position | Usage.Color | Usage.Normal | Usage.TextureCoordinates, material);
			builder.setColor(Color.WHITE);

			
			for (int segmentNumY = 0; segmentNumY < numSegments; segmentNumY += 1) {
				for (int segmentNumX = 0; segmentNumX < numSegments; segmentNumX += 1) {

					QuadSphereSegment[][][] segments = terrain.getSegments();
					QuadSphereSegment segment = segments[orientation][segmentNumX][segmentNumY];
					Vector3[][] vertices = segment.getVertices();
					Vector3[][] normals = segment.getNormals();
					Vector2[][] uvCoords = segment.getUVCoords();
					
					// add all vertices to the model
					for (int y = 0; y < segmentWidth; y += 1) {
						for (int x = 0; x < segmentWidth; x += 1) {
							builder.vertex(vertices[x][y], normals[x][y], null, uvCoords[x][y]);
						}
					}

					// add the indices to the model
					int verticesPerSegment = segmentWidth * segmentWidth;
					for (int y = 1; y < segmentWidth; y += 1) {
						for (int x = 1; x < segmentWidth; x += 1) {
							
							// adjust for the vertex row and the segment we are one
							int yAdjust = segmentWidth * (y - 1) + segmentNumY * verticesPerSegment * numSegments;
							int xAdjust = segmentNumX * verticesPerSegment + x;

							short a = (short) (xAdjust + yAdjust - 1);
							short b = (short) (xAdjust + yAdjust);
							short c = (short) (xAdjust + segmentWidth + yAdjust);
							short d = (short) (xAdjust + segmentWidth + yAdjust - 1);

							//TODO check if we can use the orientation from the outer loop here
							int o = segment.getOrientation();
							if (o == QuadSphereSegment.BOTTOM || o == QuadSphereSegment.RIGHT || o == QuadSphereSegment.BACK) {
								builder.index(a, b, c);
								builder.index(c, d, a);
							}
							else {
								builder.index(c, b, a);
								builder.index(a, d, c);								
							}
						}
					}
				}
			}
			super.models.insert(orientation, modelBuilder.end());
			super.modelInstances.add(new ModelInstance(models.get(orientation)));
		}	
	}
	
}