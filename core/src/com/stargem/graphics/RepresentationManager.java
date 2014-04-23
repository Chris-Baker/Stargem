/**
 * 
 */
package com.stargem.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.IntMap;
import com.stargem.Config;
import com.stargem.entity.Entity;
import com.stargem.entity.EntityManager;
import com.stargem.entity.components.RenderableSkinned;
import com.stargem.entity.components.RenderableStatic;
import com.stargem.physics.PhysicsManager;
import com.stargem.terrain.SkySphere;
import com.stargem.terrain.TerrainSphere;
import com.stargem.utils.Log;
import com.stargem.utils.StringHelper;

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
	private final IntMap<AnimationController> animationControllers = new IntMap<AnimationController>();	
	
	// Singleton instance
	public static RepresentationManager getInstance() {
		return instance;
	}
	private static final RepresentationManager instance = new RepresentationManager();
	private RepresentationManager(){}
	
	/**
	 * Given an Entity and a Component creates a skinned model instance
	 * 
	 * @param entity
	 * @param component
	 */
	public void createInstanceFromComponent(Entity entity, RenderableSkinned component) {
		if(this.assetManager == null) {
			throw new Error("Asset manager needs to be set in the RepresentationManager before adding model modelInstances from components.");
		}
				
		Model model = this.assetManager.get(component.modelPath, Model.class);
		
		// we need to add all the animations for this skinned model
		this.addAnimations(model, component.modelPath);
		
		ModelInstance instance = new ModelInstance(model);
		this.modelInstances.add(instance);
		component.modelIndex = this.modelInstances.size - 1;
				
		// set the transform to that of the physics component if there is one
		Matrix4 transform = PhysicsManager.getInstance().getTransformMatrix(entity);
		if(transform != null) {
			instance.transform = transform;
		}
		
		// this is needed for deleting instances and updating the indices in the entities components
		instance.userData = entity;
		
		// we need to create an animation controller for this instance
		AnimationController controller = new AnimationController(instance);
		this.animationControllers.put(component.modelIndex, controller);
	}
	
	/**
	 * This method is used as a part of loading skinned models.
	 * Given a model instance and model path tries to load all the models
	 * on the same path with a matching name and a name which follows the convention
	 * modelName@animationName and add any found animations to the given instance.
	 * 
	 * @param instance the instance to add animations to
	 * @param modelPath the path to the directory containing the animations and also the name of the base model
	 */
	private void addAnimations(Model model, String modelPath) {
				
		// cut up the path to get the name of the model without an extension
		String[] pathParts = modelPath.split("/|\\\\");
		String[] nameParts = pathParts[pathParts.length - 1].split("\\.");
		String   modelName = nameParts[0];
				
		// rebuild the path without the name of the file
		StringBuilder s = StringHelper.getBuilder();
		for(int i = 0, n = pathParts.length - 1; i < n; i += 1) {
			s.append(pathParts[i]);
			s.append("/");
		}
		String path = s.toString();
		
		// we then need a list of all models in the same directory with the same name
		// and with animations i.e. jenny@run.g3dj
		
		FileHandle[] directoryContents = Gdx.files.internal(path).list();
		if(directoryContents.length == 0) {
			Log.debug(Config.INFO, "The folder is empty " + path);
			throw new GdxRuntimeException("The folder is empty, which should not happen! " + path);
		}
		
		// match anything with the model name and the @animationName naming convention
		String nameMatch = modelName + "@\\w+\\.g3dj|G3DJ|g3db|G3DB";
				
		for(int i = 0, n = directoryContents.length; i < n; i += 1) {
			
			String fileName = directoryContents[i].name();
						
			// check to see if the file matches the correct naming convention
			if(fileName.matches(nameMatch)) {
				
				Log.debug(Config.IO_ERR, "Animation file found " + fileName);
				
				// see if the model has been loaded by the asset manager
				if(this.assetManager.isLoaded(path + fileName)) {				
				
					// if so then we grab the animations from the model
					Model animationModel = this.assetManager.get(path + fileName, Model.class);
					Animation anim = animationModel.animations.get(0);
					
					// get the animation name
					String[] animNameParts = fileName.split("\\.|@");
					
					// rename the animation to match the string after the @ i.e run					
					anim.id = animNameParts[1];
					
					// add the animation to the main instance we are building
					model.animations.add(anim);
				}
				else {
					Log.debug(Config.IO_ERR, "Animation not loaded because the asset is not loaded: " + fileName);
				}
			}
		}		
	}

	/**
	 * Given an Entity and a Component creates a static model instance
	 * 
	 * @param entity
	 * @param component
	 */
	public void createInstanceFromComponent(Entity entity, RenderableStatic component) {
		if(this.assetManager == null) {
			throw new Error("Asset manager needs to be set in the RepresentationManager before adding model modelInstances from components.");
		}
		Model model = this.assetManager.get(component.modelPath, Model.class);
		ModelInstance instance = new ModelInstance(model);
		this.modelInstances.add(instance);
		component.modelIndex = this.modelInstances.size - 1;
		
		// set the transform to that of the physics component if there is one
		Matrix4 transform = PhysicsManager.getInstance().getTransformMatrix(entity);
		if(transform != null) {
			instance.transform = transform;
		}
		
		// this is needed for deleting instances and updating the indices in the entities components
		instance.userData = entity;
	}
	
	/**
	 * Returns the model instance at the index given.
	 * 
	 * There are no checks done to see if the instance exists.
	 * 
	 * @param index the index of the instance to return
	 * @return the model insrance at the given index
	 */
	public ModelInstance getModelInstance(int index) {
		return this.modelInstances.get(index);
	}
	
	/**
	 * Given an index returns the animation controller at that index.
	 * The index is usually stored in the skinned representation component
	 * 
	 * @param index the index of the controller to return
	 * @return the animation controller at the index given
	 */
	public AnimationController getAnimationController(int index) {
		return this.animationControllers.get(index);
	}
	
	/**
	 * Set the asset manager which has all the loaded assets
	 * 
	 * @param assetManager the asset manager which has all the loaded assets
	 */
	public void setAssetManager(AssetManager assetManager) {
		this.assetManager = assetManager;
	}

	/**
	 * Given an entity returns the related transform matrix if one can be found in a 
	 * RenderableSkinned or RenderableStatic component or null if no renderable component 
	 * is attached.
	 * 
	 * @param entity the entity whose transform matrix is returned
	 * @return the transform matrix of the given entity if one can be found in a model component, null otherwise
	 */
	public Matrix4 getTransformMatrix(Entity entity) {
		
		Matrix4 transform = null;
		
		// look for an animated model component
		RenderableSkinned renderableSkinned = EntityManager.getInstance().getComponent(entity, RenderableSkinned.class);
		if(renderableSkinned != null) {
			ModelInstance model = this.getModelInstance(renderableSkinned.modelIndex);
			transform = model.transform;			
		}
		
		// look for a static model component
		RenderableStatic renderableStatic = EntityManager.getInstance().getComponent(entity, RenderableStatic.class);
		if(renderableStatic != null) {
			ModelInstance model = this.getModelInstance(renderableStatic.modelIndex);
			transform = model.transform;
		}
		
		return transform;
	}

	/**
	 * Create the renderable model instances from a TerrainSphere and three textures.
	 * The three textures are blended using vertex weights.
	 * 
	 * @param terrain
	 * @param texture_1 
	 * @param texture_2 
	 * @param texture_3 
	 */
	public void createInstanceFromTerrain(TerrainSphere terrain, Texture texture_1, Texture texture_2, Texture texture_3) {
		this.terrain = new TerrainRepresentation(terrain, texture_1, texture_2, texture_3);
	}

	/**
	 * Create the renderable model instances from a SkySphere and six textures. 
	 * One texture is used on each face of the quad sphere. 
	 * 
	 * @param sky
	 * @param texture_1
	 * @param texture_2
	 * @param texture_3
	 * @param texture_4
	 * @param texture_5
	 * @param texture_6
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

	/**
	 * Remove the model instance from the list of model instances
	 * 
	 * @param modelIndex the index of the model to remove from the list of model instances
	 */
	public void removeModelInstance(int modelIndex) {
		this.modelInstances.removeIndex(modelIndex);
		
		// if the model index removed was the last in the list then we are done
		// otherwise we need to update the component of the model which
		// has been copied into that index
		if(modelIndex < this.modelInstances.size) {
			
			Entity entity = (Entity) this.modelInstances.get(modelIndex).userData;
			
			RenderableStatic renderableStatic = EntityManager.getInstance().getComponent(entity, RenderableStatic.class);
			if (renderableStatic != null) {
				renderableStatic.modelIndex = modelIndex;
			}
			
			RenderableSkinned renderableSkinned = EntityManager.getInstance().getComponent(entity, RenderableSkinned.class);
			if (renderableSkinned != null) {
				renderableSkinned.modelIndex = modelIndex;
			}
			
		}
		
	}
	
	/**
	 * Dispose all model objects
	 */
	public void dispose() {	
		this.terrain.dispose();
		for(Model model : this.models) {
			model.dispose();
		}
	}
	
}