/**
 * 
 */
package com.stargem.screens;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.stargem.Config;
import com.stargem.GameManager;
import com.stargem.Stargem;
import com.stargem.controllers.AnyKeyPressedProcessor;
import com.stargem.graphics.RepresentationManager;
import com.stargem.persistence.PersistenceManager;
import com.stargem.persistence.SimulationPersistence;
import com.stargem.physics.PhysicsManager;
import com.stargem.profile.PlayerProfile;
import com.stargem.profile.ProfileManager;
import com.stargem.scripting.ScriptManager;
import com.stargem.terrain.SkySphere;
import com.stargem.terrain.TerrainSphere;
import com.stargem.utils.AssetList;
import com.stargem.utils.Log;
import com.stargem.utils.StringHelper;
import com.stargem.views.LoadingScreenView;
import com.stargem.views.View;
import com.stargem.world.WorldDetails;

/**
 * LoadingScreen.java
 *
 * @author 	Chris B
 * @date	18 Nov 2013
 * @version	1.0
 */
public class LoadingScreen extends AbstractScreen implements Observer {
	
	private enum LoadingScreenState {
		LOADING_VIEW, 
		FADING_IN, 
		LOADING_WORLD_ASSETS, 
		LOADING_TERRAIN, 
		LOADING_ENTITIES,
		READY,
		FADING_OUT, 
		UNLOADING_VIEW
	}
	
	LoadingScreenState currentState = LoadingScreenState.LOADING_VIEW;
	
	//private final ScriptManager script = ScriptManager.getInstance();
	private AssetList currentWorldAssets;
	private AssetList oldWorldAssets;
	private final AssetManager assets;
		
	// this is the folder which holds the current world's entities database, 
	// triggers script, and terrain height data
	private String currentWorldFilePath;

	// location of the current script file
	private String scriptPath;

	// location of the current terrain file containing height data
	private String terrainHeightMapPath;
	
	// texture and dimension details for the terrain, skybox terrain, music name, ambiance name, and world name
	private final WorldDetails worldDetails;
	
	// an input processor which listens for any key press or mouse click and updates observers
	private final AnyKeyPressedProcessor anyKeyPressedProcessor;
	
	private View view;
	
	// manages the scripting, this is used to load entities so that scripts have references
	ScriptManager scriptManager = ScriptManager.getInstance();
	
	/**
	 * @param game
	 */
	public LoadingScreen(Stargem game) {
		super(game);
		this.assets = GameManager.getInstance().getAssetManager();
		this.anyKeyPressedProcessor = new AnyKeyPressedProcessor();
		this.worldDetails = new WorldDetails();
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
		
		Log.debug(Config.IO_ERR, "Asset list created for the world.");
		Log.debug(Config.IO_ERR, this.currentWorldAssets.toString());
	}
	
	@Override
	public void render(float delta) {
		
		switch(this.currentState) {
			
			case LOADING_VIEW:
				
				// finish loading the view
				if (assets.update()) {
					
					// show the view
					this.view.show();
					
					// add the world assets to the asset manager to be loaded next
					this.addWorldAssets();
										
					// transition to show the screen and progress bar
					this.currentState = LoadingScreenState.FADING_IN;
				}
				
			break;
			
			case FADING_IN:
				
				// fade in the screen to show the loading progress bar
				
				// render the view
				this.view.render(delta);
				
				// then transition
				this.currentState = LoadingScreenState.LOADING_WORLD_ASSETS;
				
			break;
			
			case LOADING_WORLD_ASSETS:
				
				// render the view
				this.view.render(delta);
				
				// update the asset manager
				if (assets.update()) {
					
					// TODO set music track specified in the database in the sound manager
					
					// TODO set ambiance track in the sound manager
					
					this.currentState = LoadingScreenState.LOADING_TERRAIN;
				}
				
			break;
			
			case LOADING_TERRAIN:
			
				// render the view
				this.view.render(delta);
				
				// build a terrain object
				// the terrain
				
				// TODO load the terrain file with the asset loader
				// TODO pass terrain file directly to terrain constructor
				int scale = this.worldDetails.getTerrainScale();
				int segmentWidth = this.worldDetails.getTerrainSegmentWidth();
				int numSegments = this.worldDetails.getTerrainNumSegments();
				Pixmap heightMap = this.assets.get(this.terrainHeightMapPath, Pixmap.class);
				TerrainSphere terrain = new TerrainSphere(scale, segmentWidth, numSegments, heightMap);
				
				// pass the terrain to the physics manager
				PhysicsManager.getInstance().createBodyFromTerrain(terrain);
				
				// pass the terrain to the representation manager with the loaded textures
				Texture terrain_1 = this.assets.get(this.worldDetails.getTerrainTexture_1(), Texture.class);
				Texture terrain_2 = this.assets.get(this.worldDetails.getTerrainTexture_2(), Texture.class);
				Texture terrain_3 = this.assets.get(this.worldDetails.getTerrainTexture_3(), Texture.class);
				RepresentationManager.getInstance().createInstanceFromTerrain(terrain, terrain_1, terrain_2, terrain_3);
				
				// create the skybox
				// the skybox textures will be loaded by the asset manager
				SkySphere sky = new SkySphere();
				
				// pass the skybox to the representation manager with the loaded textures
				Texture sky_1 = this.assets.get(this.worldDetails.getSkyboxTexture_1(), Texture.class);
				Texture sky_2 = this.assets.get(this.worldDetails.getSkyboxTexture_2(), Texture.class);
				Texture sky_3 = this.assets.get(this.worldDetails.getSkyboxTexture_3(), Texture.class);
				Texture sky_4 = this.assets.get(this.worldDetails.getSkyboxTexture_4(), Texture.class);
				Texture sky_5 = this.assets.get(this.worldDetails.getSkyboxTexture_5(), Texture.class);
				Texture sky_6 = this.assets.get(this.worldDetails.getSkyboxTexture_6(), Texture.class);
				RepresentationManager.getInstance().createInstanceFromSky(sky, sky_1, sky_2, sky_3, sky_4, sky_5, sky_6);
				
				// can this happen on another thread for feedback purposes?
				
				// query the terrain object for updates so we can display
				// progress to user and we know when it has finished
				
				// transition when finished
				this.currentState = LoadingScreenState.LOADING_ENTITIES;
				
			break;
				
			case LOADING_ENTITIES:
			
				// render the view
				this.view.render(delta);
								
//				EntityPersistence entityPersistence = PersistenceManager.getInstance().getEntityPersistence();
//				EntityManager entityManager = EntityManager.getInstance();
//								
//				int numEntities = entityPersistence.beginLoading();
//				Entity[] entities = new Entity[numEntities];
//				
//				Log.info(Config.ENTITY_ERR, "Loading " + numEntities + " entities");
//				
//				for (int i = 0; i < numEntities; i += 1) {					
//					Entity e = entityPersistence.loadEntity();					
//					entities[i] = e;
//				}
				
				// This script loads all the entities, it is the same as the above but allows the script
				// environment to have a copy of each entity reference.
				this.scriptManager.execute("entities", "load");				
				
				// we are now ready to transition
				// TODO this should be done on a separate thread and a callback should switch the state 
				
				// set an input processor to listen for any key presses
				this.anyKeyPressedProcessor.addObserver(this);
				Gdx.input.setInputProcessor(this.anyKeyPressedProcessor);
				
				Log.debug(Config.INFO, "ready...");
				
				this.currentState = LoadingScreenState.READY;
				
			break;
			
			case READY:
				
				// render the view
				this.view.render(delta);
				
				// wait for user to click or press the any key
				// Once the player has clicked, the Observer update method will be called
				
			break;
			
			case FADING_OUT:
				
				// render the view
				this.view.render(delta);
				
				// fade to black
				
				// transition
				this.currentState = LoadingScreenState.UNLOADING_VIEW;
				
			break;
			
			case UNLOADING_VIEW:
				
				// dispose the view
				this.view.dispose();
				
				// set current state back to loading view
				this.currentState = LoadingScreenState.LOADING_VIEW;
				
				// switch screen
				this.game.setPlayScreen();
				
			break;
			
			default:
				this.currentState = LoadingScreenState.LOADING_VIEW;
			break;
		
		}
	}

	@Override
	public void resize(int width, int height) {
		this.view.resize(width, height);
	}

	@Override
	public void show() {
		
		// Create a new loading screen view.
		this.view = new LoadingScreenView(assets);
				
		// read the current world name
		PlayerProfile profile = ProfileManager.getInstance().getActiveProfile();
		
		String world 	= profile.getWorldName();
		String campaign = profile.getCampaignName();
		
		// populate the world details
		PersistenceManager.getInstance().getSimulationPersistence().populateWorldDetails(worldDetails);
		
		// get the filepath for the current world
		StringBuilder sb = StringHelper.getBuilder();
		sb.append(Config.CAMPAIGN_PATH);
		sb.append(campaign);
		sb.append("/");
		sb.append(world);
		sb.append("/");	
		this.currentWorldFilePath = sb.toString();
		
		// get the script filepath
		sb.setLength(0);
		sb.append(this.currentWorldFilePath);
		sb.append(Config.WORLD_SCRIPT_NAME);
		sb.append(Config.SCRIPT_EXTENSION);
		this.scriptPath = sb.toString();
		
		// get the terrain filepath
		sb.setLength(0);
		sb.append(this.currentWorldFilePath);
		sb.append("heightMap.png");
		this.terrainHeightMapPath = sb.toString();
		
		// reset and initialise the sctipting environment
		this.scriptManager.close();
		this.scriptManager.initialise(scriptPath);
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

	/**
	 * This screen is observing for any key presses or mouse clicks when all loading has finished.
	 * The screen will then transition to the play screen.
	 * 
	 * This method is called whenever the observed object is changed. 
	 * An application calls an Observable object's notifyObservers method 
	 * to have all the object's observers notified of the change.
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
				
		if(this.currentState == LoadingScreenState.READY) {
			Log.info(Config.INFO, "Key pressed, transitioning next state");
			this.anyKeyPressedProcessor.deleteObserver(this);			
			this.currentState = LoadingScreenState.FADING_OUT;		
		}
		else {
			throw new Error("Observer update called on Loading Screen when it shouldn't have been!" +
					" The screen must be in its ready state before it can transition.");
		}
		
	}
	
}