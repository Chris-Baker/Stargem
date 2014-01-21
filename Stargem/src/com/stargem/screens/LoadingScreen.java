/**
 * 
 */
package com.stargem.screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.stargem.AssetList;
import com.stargem.Config;
import com.stargem.Log;
import com.stargem.Stargem;
import com.stargem.StringHelper;
import com.stargem.entity.Entity;
import com.stargem.entity.EntityManager;
import com.stargem.profile.PlayerProfile;
import com.stargem.profile.ProfileManager;
import com.stargem.scripting.LuaScript;
import com.stargem.sql.EntityPersistence;
import com.stargem.sql.PersistenceManager;
import com.stargem.sql.SimulationPersistence;

/**
 * LoadingScreen.java
 *
 * @author 	Chris B
 * @date	18 Nov 2013
 * @version	1.0
 */
public class LoadingScreen extends AbstractScreen {

	private final LuaScript script = LuaScript.getInstance();
	private final AssetList localAssets;
	private AssetList currentWorldAssets;
	private AssetList oldWorldAssets;
	private final AssetManager assets;	
	private boolean isLoadingLocalAssets = true;
	private boolean isLoadingWorldAssets = true;
	
	// local asset paths
	private final String backgroundPath 		= "data/screens/loading/background.jpg";
	private final String loadingBarEmptyPath 	= "data/screens/loading/loading-bar-empty.png";
	private final String loadingBarFullPath 	= "data/screens/loading/loading-bar-full.png";
	private final String loadingTextPath		= "data/screens/loading/initialising-uplink.png";
	private final String loadingMusicPath		= "data/screens/loading/FutureWorld_Loading_Loop.ogg";
		
	/**
	 * @param game
	 */
	public LoadingScreen(Stargem game) {
		super(game);
		
		// get the asset manager
		this.assets = game.getAssetManager();
		
		// create an asset list for the loading screen
		this.localAssets = new AssetList(this.assets);
		
		// add the local assets to the asset list this needs to be loaded when the screen is shown
		this.localAssets.add(backgroundPath, Texture.class);
		this.localAssets.add(loadingBarEmptyPath, Texture.class);
		this.localAssets.add(loadingBarFullPath, Texture.class);
		this.localAssets.add(loadingTextPath, Texture.class);
		this.localAssets.add(loadingMusicPath, Music.class);
		
		Log.info(Config.IO_ERR, "Asset list created for the loading screen.");
		Log.info(Config.IO_ERR, this.localAssets.toString());
	}

	/**
	 * Once the local assets have been loaded we can create the screen
	 * and start to load the game assets
	 */
	private void doneLoadingLocalAssets() {
		
		// get the loaded assets from the asset manager
		Texture backgroundTexture 		= this.assets.get(backgroundPath, Texture.class);
		Texture loadingBarEmptyTexture 	= this.assets.get(loadingBarEmptyPath, Texture.class);
		Texture loadingBarFullTexture 	= this.assets.get(loadingBarFullPath, Texture.class);
		Texture loadingTextTexture 		= this.assets.get(loadingTextPath, Texture.class);
		Music loadingMusic				= this.assets.get(loadingMusicPath, Music.class);	
		
		loadingMusic.setLooping(true);
		loadingMusic.play();
		
		Log.info(Config.IO_ERR, "Finished loading asset list for the loading screen.");
		
		this.isLoadingLocalAssets = false;
	}
	
	private void doneLoadingWorldAssets() {
		
		
		Log.info(Config.IO_ERR, "Finished loading asset list for the loading screen.");
		
		this.isLoadingWorldAssets = false;
	}
	
	/**
	 * Add all the current game worlds assets into the asset manager
	 */
	private void addWorldAssets() {
		
		SimulationPersistence simulationPersistence = PersistenceManager.getInstance().getSimulationPersistence();
		
		// save the current assets as the old assets
		this.oldWorldAssets = this.currentWorldAssets;
		
		// create a new empty asset list
		this.currentWorldAssets = new AssetList(this.assets);
		
		// populate the new asset list
		simulationPersistence.populateAssetList(this.currentWorldAssets);
		
		// load the new asset list into the asset manager
		this.currentWorldAssets.load();
		
		// unload the old assets. we do this last because the asset manager counts references
		// this saves us unloading anything we still need. Old world assets will always be a
		// null reference when the first world is loaded.
		if(this.oldWorldAssets != null) {
			this.oldWorldAssets.unload();
			this.oldWorldAssets = null;
		}
		
		Log.info(Config.IO_ERR, "Asset list created for the world.");
		Log.info(Config.IO_ERR, this.currentWorldAssets.toString());
	}
	
	@Override
	public void render(float delta) {
		
		
		// finish loading local assets
		if (isLoadingLocalAssets && assets.update()) {
			this.doneLoadingLocalAssets();
			this.addWorldAssets();
		}
				
		// update asset manager
		
		// display progress bar
		
		// once assets are loaded then run Lua name to initiate entity load
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		
		// load the assets for the loading screen back into the manager
		this.localAssets.load();
				
		// read the current world name
		PlayerProfile profile = ProfileManager.getInstance().getActiveProfile();
		
		String world 	= profile.getWorldName();
		String campaign = profile.getCampaignName();
				
		// load Lua name for the level
		StringBuilder luaPath = StringHelper.getBuilder();
		luaPath.append(Config.CAMPAIGN_PATH);
		luaPath.append(campaign);
		luaPath.append("/");
		luaPath.append(world);
		luaPath.append("/triggers.lua");
		
		script.close();
		script.initialise(luaPath.toString());
		//name.execute("entities", "load");
		
		EntityPersistence entityPersistence = PersistenceManager.getInstance().getEntityPersistence();
		EntityManager entityManager = EntityManager.getInstance();
		int numEntities = entityPersistence.beginLoading();
		Entity[] entities = new Entity[numEntities];
		for (int i = 0; i < numEntities; i += 1) {
			Entity e = entityManager.createEntity();
			entityPersistence.loadEntity(e);
			entities[i] = e;
		}
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
	
}