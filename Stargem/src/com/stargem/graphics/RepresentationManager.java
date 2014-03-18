/**
 * 
 */
package com.stargem.graphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.stargem.entity.Entity;
import com.stargem.entity.EntityManager;
import com.stargem.entity.components.RenderableSkinned;
import com.stargem.entity.components.RenderableStatic;
import com.stargem.physics.PhysicsManager;
import com.stargem.terrain.SkySphere;
import com.stargem.terrain.TerrainSphere;

/**
 * RepresentationManager.java
 *
 * @author 	Chris B
 * @date	18 Nov 2013
 * @version	1.0
 */
public class RepresentationManager {

	private AbstractIterableRepresentation sky;
	private AbstractIterableRepresentation terrain;
	private final Array<ModelInstance> modelInstances = new Array<ModelInstance>();
	private final Array<Model> models = new Array<Model>();
	private AssetManager assetManager;	
	
	// Singleton instance
	public static RepresentationManager getInstance() {
		return instance;
	}
	private static final RepresentationManager instance = new RepresentationManager();;
	private RepresentationManager(){}
	
	// add an instance from a component
	public void createInstanceFromComponent(Entity entity, RenderableSkinned component) {
		if(this.assetManager == null) {
			throw new Error("Asset manager needs to be set in the RepresentationManager before adding model modelInstances from components.");
		}
		Model model = this.assetManager.get(component.modelName, Model.class);
		ModelInstance instance = new ModelInstance(model);
		this.modelInstances.add(instance);
		component.modelIndex = this.modelInstances.size - 1;
		
		// set the transform to that of the physics component if there is one
		Matrix4 transform = PhysicsManager.getInstance().getTransformMatrix(entity);
		if(transform != null) {
			instance.transform = transform;
		}
		
	}
	
	// add an instance from a component
	public void createInstanceFromComponent(Entity entity, RenderableStatic component) {
		if(this.assetManager == null) {
			throw new Error("Asset manager needs to be set in the RepresentationManager before adding model modelInstances from components.");
		}
		Model model = this.assetManager.get(component.modelName, Model.class);
		ModelInstance instance = new ModelInstance(model);
		this.modelInstances.add(instance);
		component.modelIndex = this.modelInstances.size - 1;
		
		// set the transform to that of the physics component if there is one
		Matrix4 transform = PhysicsManager.getInstance().getTransformMatrix(entity);
		if(transform != null) {
			instance.transform = transform;
		}
	}
	
	// add models from asset manager using asset list
	
	// get an instance
	public ModelInstance getModelInstance(int index) {
		return this.modelInstances.get(index);
	}
	
	/**
	 * @param assetManager
	 */
	public void setAssetManager(AssetManager assetManager) {
		this.assetManager = assetManager;
	}

	/**
	 * Given an entity returns the related transform matrix or null if no model component is attached.
	 * 
	 * @param entity
	 * @return
	 */
	public Matrix4 getTransformMatrix(Entity entity) {
		
		Matrix4 transform = null;
		
		// look for an animated model component
		RenderableSkinned renderableSkinned = EntityManager.getInstance().getComponent(entity, RenderableSkinned.class);
		if(renderableSkinned != null) {
			ModelInstance model = this.getModelInstance(renderableSkinned.modelIndex);
			transform = model.transform;			
		}
		else {
			
			// look for a static model component
			RenderableStatic renderableStatic = EntityManager.getInstance().getComponent(entity, RenderableStatic.class);
			if(renderableStatic != null) {
				ModelInstance model = this.getModelInstance(renderableStatic.modelIndex);
				transform = model.transform;
			}
		}
		
		return transform;
	}

	/**
	 * @param terrain
	 * @param texture_3 
	 * @param texture_2 
	 * @param texture_1 
	 */
	public void createInstanceFromTerrain(TerrainSphere terrain, Texture texture_1, Texture texture_2, Texture texture_3) {
		this.terrain = new TerrainRepresentation(terrain, texture_1, texture_2, texture_3);
	}

	/**
	 * @param sky
	 */
	public void createInstanceFromSky(SkySphere sky, Texture texture_1, Texture texture_2, Texture texture_3, Texture texture_4, Texture texture_5, Texture texture_6) {
		this.sky = new SkyRepresentation(sky, texture_1, texture_2, texture_3, texture_4, texture_5, texture_6);
	}
	
	/**
	 * Returns the sky model instances as an Iterable.
	 * 
	 * @return the sky model instances as an Iterable.
	 */
	public Iterable<ModelInstance> getSkyInstances() {
		return sky;
	}
	
	/**
	 * Returns the terrain model instances as an Iterable.
	 * 
	 * @return the terrain model instances as an Iterable.
	 */
	public Iterable<ModelInstance> getTerrainInstances() {
		return terrain;
	}
	
	/**
	 * Returns the entity model instances as an Iterable.
	 * 
	 * @return the entity model instances as an Iterable.
	 */
	public Iterable<ModelInstance> getEntityInstances() {
		return this.modelInstances;
	}
	
	public void dispose() {
		
		this.terrain.dispose();
		
		for(Model model : this.models) {
			model.dispose();
		}
	}

	/**
	 * Remove the model instance from the list of model instances
	 * 
	 * @param modelIndex the index of the model to remove from the list of model instances
	 */
	public void removeModelInstance(int modelIndex) {
		this.modelInstances.removeIndex(modelIndex);
	}
	
}