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
import com.stargem.terrain.SkySphere;

/**
 * SkyRepresentation.java
 *
 * @author 	Chris B
 * @date	29 Jan 2014
 * @version	1.0
 */
public class SkyRepresentation extends AbstractIterableRepresentation {

	private final int segmentWidth;	
	
	/**
	 * @param sky
	 */
	public SkyRepresentation(SkySphere sky, Texture texture_1, Texture texture_2, Texture texture_3, Texture texture_4, Texture texture_5, Texture texture_6) {
		super(SkySphere.NUM_FACES, SkySphere.NUM_FACES);
		this.segmentWidth = 2;		
		this.build(sky, texture_1, texture_2, texture_3, texture_4, texture_5, texture_6);
	}

	/**
	 * @param sky
	 */
	private void build(SkySphere sky, Texture texture_1, Texture texture_2, Texture texture_3, Texture texture_4, Texture texture_5, Texture texture_6) {
		
		ModelBuilder modelBuilder = new ModelBuilder();
			
		// Texture materials for each face
		Material[] materials = new Material[6];	
		materials[0] = new Material(new TextureAttribute(TextureAttribute.Diffuse, texture_1));
		materials[1] = new Material(new TextureAttribute(TextureAttribute.Diffuse, texture_2));
		materials[2] = new Material(new TextureAttribute(TextureAttribute.Diffuse, texture_3));
		materials[3] = new Material(new TextureAttribute(TextureAttribute.Diffuse, texture_4));
		materials[4] = new Material(new TextureAttribute(TextureAttribute.Diffuse, texture_5));
		materials[5] = new Material(new TextureAttribute(TextureAttribute.Diffuse, texture_6));
		
		for (int orientation = 0; orientation < SkySphere.NUM_FACES; orientation += 1) {

			// create a new mesh part per cube face
			modelBuilder.begin();
			MeshPartBuilder builder = modelBuilder.part("face_0" + orientation, GL20.GL_TRIANGLES, Usage.Position | Usage.Color | Usage.Normal | Usage.TextureCoordinates, materials[orientation]);
			builder.setColor(Color.WHITE);

			QuadSphereSegment[] segments = sky.getSegments();
			QuadSphereSegment segment = segments[orientation];
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
			for (int y = 1; y < segmentWidth; y += 1) {
				for (int x = 1; x < segmentWidth; x += 1) {
					
					// adjust for the vertex row and the segment we are one
					int yAdjust = segmentWidth * (y - 1);

					short a = (short) (x + yAdjust - 1);
					short b = (short) (x + yAdjust);
					short c = (short) (x + segmentWidth + yAdjust);
					short d = (short) (x + segmentWidth + yAdjust - 1);

					//TODO check if we can use the orientation from the outer loop here
					int o = segment.getOrientation();
					if (o == QuadSphereSegment.BOTTOM || o == QuadSphereSegment.RIGHT || o == QuadSphereSegment.BACK) {
						// this is opposite to terrain sphere winding order because the faces are inverted
						builder.index(c, b, a);
						builder.index(a, d, c);	
					}
					else {
						// this is opposite to terrain sphere winding order because the faces are inverted
						builder.index(a, b, c);
						builder.index(c, d, a);						
					}
				}
			}
			
			super.models.insert(orientation, modelBuilder.end());
			super.modelInstances.add(new ModelInstance(models.get(orientation)));
		}
		
	}

	
	
}