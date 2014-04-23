/**
 * 
 */
package com.stargem.physics;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBvhTriangleMeshShape;
import com.badlogic.gdx.physics.bullet.collision.btTriangleMesh;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody.btRigidBodyConstructionInfo;
import com.badlogic.gdx.utils.Array;
import com.stargem.terrain.QuadSphereSegment;
import com.stargem.terrain.TerrainSphere;

/**
 * TerrainPhysicsBody.java
 *
 * @author 	Chris B
 * @date	3 Aug 2013
 * @version	1.0
 */
public class TerrainPhysicsBody {

	private Array<btTriangleMesh> meshes = new Array<btTriangleMesh>();
	private Array<btBvhTriangleMeshShape> shapes = new Array<btBvhTriangleMeshShape>();
	private Array<btRigidBodyConstructionInfo> infos = new Array<btRigidBodyConstructionInfo>();
	private Array<btRigidBody> bodies = new Array<btRigidBody>();

	public TerrainPhysicsBody(TerrainSphere terrain) {
		this.build(terrain);
	}
	
	/**
	 * Uses the vertices from the terrain segments to create a Bullet physics 
	 * triangle mesh for each segment.
	 */
	private void build(TerrainSphere terrain) {
		
		// for each face
		for (int orientation = 0; orientation < TerrainSphere.NUM_FACES; orientation += 1) {
						
			// for each terrain segment of the face
			int numSegments = terrain.getNumSegments();
			for (int segmentNumY = 0; segmentNumY < numSegments; segmentNumY += 1) {
				for (int segmentNumX = 0; segmentNumX < numSegments; segmentNumX += 1) {
					
					// create a triangle mesh to hold the veretx data for the segment
					btTriangleMesh mesh = new btTriangleMesh();
					
					// get the segment from the terrain
					QuadSphereSegment[][][] segments = terrain.getSegments();
					QuadSphereSegment segment = segments[orientation][segmentNumX][segmentNumY];				
					Vector3[][] vertices = segment.getVertices();
					
					// add all segment triangles to the mesh
					int segmentWidth = terrain.getSegmentWidth();
					for (int y = 0; y < segmentWidth - 1; y += 1) {
						for (int x = 0; x < segmentWidth - 1; x += 1) {
							
							// add two triangles to the mesh which make up a quad
							mesh.addTriangle(vertices[x][y], vertices[x + 1][y], vertices[x + 1][y + 1], false);
							mesh.addTriangle(vertices[x + 1][y + 1], vertices[x][y + 1], vertices[x][y], false);
														
						}
					}
					
					// create a triangle mesh shape and rigid body
					btBvhTriangleMeshShape shape = new btBvhTriangleMeshShape(mesh, true);
					btRigidBodyConstructionInfo info = new btRigidBodyConstructionInfo(0f, null, shape, Vector3.Zero);
					btRigidBody body = new btRigidBody(info);
					
					// keep track of all the builder objects so that the GC doesn't collect them
					this.meshes.add(mesh);
					this.shapes.add(shape);
					this.infos.add(info);
					this.bodies.add(body);
					
				}
			}
						
		}
		
							
	}

	/**
	 * Add the terrain physics bodies for this terrain to the physics world given. 
	 * @param world the dynamic world to add the terrain bodies to.
	 */
	public void addToWorld(btDynamicsWorld world) {
		
		for(btRigidBody body : this.bodies) {
			world.addRigidBody(body);
		}		
	}
	
	/**
	 * Remove the terrain physics bodies for this terrain from the physics world given. 
	 * @param world the dynamic world to add the terrain bodies to.
	 */
	public void removeFromWorld(btDynamicsWorld world) {
		
		for(btRigidBody body : this.bodies) {
			world.removeRigidBody(body);
		}		
	}
	
	/**
	 * Clean up all the native objects associated with this physics terrain.
	 * 
	 * All the physics bodies should be removed from the simulation
	 * before calling this, otherwise there may be side effects due to
	 * orphaned native objects.
	 */
	public void dispose() {
		
		for(int index = 0, n = bodies.size; index < n; index += 1) {
			
			btTriangleMesh mesh = meshes.removeIndex(index);
			if(mesh != null) {
				mesh.dispose();
			} 
			
			btRigidBodyConstructionInfo info = infos.removeIndex(index);
			if(info != null) {
				info.dispose();
			}
			
			btRigidBody body = bodies.removeIndex(index);
			if(body != null) {
				body.dispose();
			}
		}
		
		meshes 	= null;
		shapes 	= null;
		infos 	= null;
		bodies 	= null;
		
	}
	
}