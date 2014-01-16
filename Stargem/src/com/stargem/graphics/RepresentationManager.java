/**
 * 
 */
package com.stargem.graphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.utils.Array;
import com.stargem.entity.components.RenderableSkinned;
import com.stargem.entity.components.RenderableStatic;

/**
 * RepresentationManager.java
 *
 * @author 	Chris B
 * @date	18 Nov 2013
 * @version	1.0
 */
public class RepresentationManager {

	private final Array<ModelInstance> instances = new Array<ModelInstance>();
	private final Array<Model> models = new Array<Model>();
	private AssetManager assetManager;
	
	
	// Singleton instance
	public static RepresentationManager getInstance() {
		return instance;
	}
	private static final RepresentationManager instance = new RepresentationManager();;
	private RepresentationManager(){}
	
	// add an instance from a component
	public void createInstanceFromComponent(RenderableSkinned component) {
		if(this.assetManager == null) {
			throw new Error("Asset manager not set.");
		}
		Model model = this.assetManager.get(component.modelName, Model.class);
		ModelInstance instance = new ModelInstance(model);
		this.instances.add(instance);
	}
	
	// add an instance from a component
	public void createInstanceFromComponent(RenderableStatic component) {
		if(this.assetManager == null) {
			throw new Error("Asset manager not set.");
		}
		Model model = this.assetManager.get(component.modelName, Model.class);
		ModelInstance instance = new ModelInstance(model);
		this.instances.add(instance);
	}
	
	// add models from asset manager using asset list
	
	// get an instance
	public ModelInstance getModelInstance(int index) {
		return this.instances.get(index);
	}
	/**
	 * @param assetManager
	 */
	public void setAssetManager(AssetManager assetManager) {
		this.assetManager = assetManager;
	}
	
}