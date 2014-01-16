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
	private final AssetManager assets;	
	private boolean isLoadingLocalAssets = true;
	
	// local asset paths
	private final String backgroundPath 		= "data/screens/loading/background.jpg";
	private final String loadingBarEmptyPath 	= "data/screens/loading/loading-bar-empty.png";
	private final String loadingBarFullPath 	= "data/screens/loading/loading-bar-full.png";
	private final String loadingTextPath		= "data/screens/loading/initialising-uplink.png";
	private final String loadingMusicPath		= "data/screens/loading/FutureWorld_Loading_Loop.ogg";
	
	// 
	
	/**
	 * @param game
	 */
	public LoadingScreen(Stargem game) {
		super(game);
		
		// get the asset manager
		this.assets = game.getAssetManager();
		
		// create an asset list for the loading screen
		this.localAssets = new AssetList(this.assets);
		
		// add the local assets to the asset list
		this.localAssets.add(backgroundPath, Texture.class);
		this.localAssets.add(loadingBarEmptyPath, Texture.class);
		this.localAssets.add(loadingBarFullPath, Texture.class);
		this.localAssets.add(loadingTextPath, Texture.class);
		this.localAssets.add(loadingMusicPath, Music.class);
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
		
		Log.info(Config.IO_ERR, "Assets loaded");
		
		this.isLoadingLocalAssets = false;
	}
	
	@Override
	public void render(float delta) {
		
		// finish loading local assets
		if (isLoadingLocalAssets && assets.update()) {
			this.doneLoadingLocalAssets();
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
		
		// add assets for the level to a new list
		
		// read the current level name
		PlayerProfile profile = ProfileManager.getInstance().getActiveProfile();
		
		// TODO load this from the profile. this needs to be stored in the profile by the new profile screen
		//String level = profile.getLevelName();
		//String campaign = profile.getCampaignName();
		
		String level 	= Config.DEFAULT_WORLD;
		String campaign = Config.DEFAULT_CAMPAIGN;
		
		// get the asset list from database
		
		// load in all assets to asset manager
		
		// load Lua name for the level
		StringBuilder luaPath = StringHelper.getBuilder();
		luaPath.append(Config.CAMPAIGN_PATH);
		luaPath.append(campaign);
		luaPath.append("/");
		luaPath.append(level);
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