/**
 * 
 */
package com.stargem;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;

/**
 * A wrapper around the asset manager to allow bundles of assets to be loaded and unloaded.
 * This is useful for grouping all the assets for a certain screen or level together.
 * 
 * AssetList.java
 *
 * @author 	Chris B
 * @date	18 Nov 2013
 * @version	1.0
 */
public class AssetList {

	private boolean isLoaded = false;
	private final Array<String> paths = new Array<String>();
	private final Array<Class<?>> types = new Array<Class<?>>();
	private final AssetManager assets;
	
	public AssetList(AssetManager assets) {
		this.assets = assets;
	}
	
	/**
	 * Add an asset to the list of assets tracked in this list.
	 * Uses reflection to grab the class type from the name of the
	 * class type given
	 * 
	 * @param path the internal file path to the asset
	 * @param typeName the fully qualified name of the class type of the asset
	 */
	public void add(String path, String typeName) {		
		try {
			Class<?> type = Class.forName(typeName);
			
			paths.add(path);
			types.add(type);
		}
		catch (ClassNotFoundException e) {
			Log.error(Config.REFLECTION_ERR, e.getMessage());
		}		
	}
	
	/**
	 * Add an asset to the list of assets tracked in this list.
	 * 
	 * @param path
	 * @param type
	 */
	public void add(String path, Class<?> type) {
		paths.add(path);
		types.add(type);
	}
	
	/**
	 * Load all of the assets added to this list into the asset manager
	 * so they can be asynchronously loaded.
	 */
	public void load() {
		if(this.isLoaded) {
			throw new Error("Cannot load asset list because it is already loaded");
		}		
		for(int i = 0, n = paths.size; i < n; i += 1) {
			this.assets.load(paths.get(i), types.get(i));
		}		
		this.isLoaded = true;
	}
	
	/**
	 * Unload all of the assets added to this list from the asset manager.
	 */
	public void unload() {
		if(!this.isLoaded) {
			throw new Error("Cannot unload asset list because it is not loaded");
		}		
		for(int i = 0, n = paths.size; i < n; i += 1) {
			this.assets.unload(paths.get(i));
		}		
		this.isLoaded = false;
	}
	
	
	
}