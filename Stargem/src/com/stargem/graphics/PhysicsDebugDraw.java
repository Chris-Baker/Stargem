/**
 * 
 */
package com.stargem.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.stargem.Config;
import com.stargem.physics.PhysicsManager;
import com.stargem.utils.Log;

/**
 * PhysicsDebugDraw.java
 *
 * @author 	Chris B
 * @date	7 Mar 2014
 * @version	1.0
 */
public class PhysicsDebugDraw {
	
	private final Array<ModelInstance> debugInstances = new Array<ModelInstance>();
	private final Array<Model> models = new Array<Model>();
	private final ModelBuilder modelBuilder = new ModelBuilder();
	
	// Singleton instance
	public static PhysicsDebugDraw getInstance() {
		return instance;
	}
	private static final PhysicsDebugDraw instance = new PhysicsDebugDraw();;
	private PhysicsDebugDraw(){}	
	
	public void createDebugInstance(int index, Matrix4 transform, int shape, float width, float height, float depth) {
		
		// this will never add a null to the instances because 
		// the default of the switch is to throw an error
		Model model = null;
		ModelInstance instance = null;
		Material material = new Material(ColorAttribute.createDiffuse(Color.RED), ColorAttribute.createSpecular(Color.WHITE), FloatAttribute.createShininess(64f));
		int attributes = Usage.Position | Usage.Normal;
		int divisions = 8;
		
		// physics dimensions are stored as half extents but the model builder requires full extents
		width  = width * 2;
		height = height * 2;
		depth  = depth * 2;
		
		switch(shape) {
			case PhysicsManager.SHAPE_BOX:
				model = modelBuilder.createBox(width, height, depth, material, attributes);
			break;
			
			case PhysicsManager.SHAPE_CAPSULE:
				model = modelBuilder.createCapsule(width, height, divisions, material, attributes);
				
			break;
			
			case PhysicsManager.SHAPE_CYLINDER:
				model = modelBuilder.createCylinder(width, height, depth, divisions, material, attributes);
			break;
			
			case PhysicsManager.SHAPE_SPHERE:
				model = modelBuilder.createSphere(width, height, depth, divisions, divisions, material, attributes);
			break;
			
			default:
				String message = "Cannot create model for unknown physics shape.";
				Log.error(Config.PHYSICS_ERR, message);
				throw new Error(message);
		}
		
		instance = new ModelInstance(model);
		instance.transform = transform;
		
		//models.set(index, model);
		//this.debugInstances.set(index, instance);
		
		models.add(model);
		debugInstances.add(instance);
		
	}

//	public void removeInstanceAtIndex(int index) {
//		this.models.removeIndex(index);
//		this.debugInstances.removeIndex(index);
//	}
	
	/**
	 * Returns the entity model instances as an Iterable.
	 * 
	 * @return the entity model instances as an Iterable.
	 */
	public Iterable<ModelInstance> getEntityInstances() {
		return this.debugInstances;
	}
	
}