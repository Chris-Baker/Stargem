/**
 * 
 */
package com.stargem.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;
import com.stargem.Config;

/**
 * An AssetList holds a list of asset file paths and Class types which are
 * related to each other. The asset list batches loading and unloading of
 * the related files to and from the asset manager. This allows all the
 * assets for a World or Screen to be easily managed.
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
	 * Uses reflection to grab the class shape from the name of the
	 * class shape given. Assets are not added to the asset manager right
	 * away. This is done as a separate step, allowing assets to be added
	 * and removed from the manager without destroying the asset list.
	 * 
	 * @param path the internal file path to the asset
	 * @param typeName the fully qualified name of the class shape of the asset
	 */
	public void add(String path, String typeName) {		
		try {
			Class<?> type = Class.forName(typeName);
			this.add(path, type);
		}
		catch (ClassNotFoundException e) {
			Log.error(Config.REFLECTION_ERR, e.getMessage());
		}		
	}
	
	/**
	 * Add an asset to the list of assets tracked in this list.
	 * 
	 * @param path
	 * @param shape
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
		for(String path : paths) {
			this.assets.unload(path);
		}	
		this.isLoaded = false;
	}
	
	@Override
	public String toString() {
		
		StringBuilder s = StringHelper.getBuilder();
		s.append("Asset List:");
		for(int i = 0, n = paths.size; i < n; i += 1) {
			s.append("\n");
			s.append(types.get(i).getSimpleName());
			s.append("\t : ");
			s.append(paths.get(i));			
		}
		return s.toString();		
	}
	
}