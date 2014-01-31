/**
 * 
 */
package com.stargem.screens;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.stargem.AssetList;
import com.stargem.Config;
import com.stargem.Log;
import com.stargem.Stargem;
import com.stargem.StringHelper;
import com.stargem.controllers.AnyKeyPressedProcessor;
import com.stargem.entity.Entity;
import com.stargem.entity.EntityManager;
import com.stargem.graphics.RepresentationManager;
import com.stargem.persistence.EntityPersistence;
import com.stargem.persistence.PersistenceManager;
import com.stargem.persistence.SimulationPersistence;
import com.stargem.physics.PhysicsManager;
import com.stargem.profile.PlayerProfile;
import com.stargem.profile.ProfileManager;
import com.stargem.terrain.SkySphere;
import com.stargem.terrain.TerrainSphere;
import com.stargem.views.LoadingScreenView;
import com.stargem.views.View;

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
	
	//private final LuaScript script = LuaScript.getInstance();
	private AssetList currentWorldAssets;
	private AssetList oldWorldAssets;
	private final AssetManager assets;
		
	// this is the folder which holds the current world's entities database, 
	// triggers script, and terrain height data
	private String currentWorldFilePath;

	// location of the current script file
	private String scriptPath;

	// location of the current terrain file
	private String terrainPath;
	
	// an input processor which listens for any key press or mouse click and updates observers
	private final AnyKeyPressedProcessor anyKeyPressedProcessor;
	
	private View view;
	
	/**
	 * @param game
	 */
	public LoadingScreen(Stargem game) {
		super(game);
		this.assets = game.getAssetManager();
		this.anyKeyPressedProcessor = new AnyKeyPressedProcessor();
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
					this.currentState = LoadingScreenState.LOADING_TERRAIN;
				}
				
			break;
			
			case LOADING_TERRAIN:
			
				// render the view
				this.view.render(delta);
				
				// build a terrain object
				// the terrain
				
				// TODO get these from the terrain file
				// TODO load the terrain file with the asset loader
				// TODO pass terrain file directly to terrain constructor
				int scale = 90;
				int segmentWidth = 3;
				int numSegments = 25;
				TerrainSphere terrain = new TerrainSphere(scale, segmentWidth, numSegments);
				
				// pass the terrain to the physics manager
				PhysicsManager.getInstance().createBodyFromTerrain(terrain);
				
				// pass the terrain to the representation manager
				RepresentationManager.getInstance().createInstanceFromTerrain(terrain);
				
				// create the skybox
				// the skybox textures will be loaded by the asset manager
				SkySphere sky = new SkySphere("hires-green-");
				
				// pass the skybox to the representation  manager
				RepresentationManager.getInstance().createInstanceFromSky(sky);
				
				// can this happen on another thread for feedback purposes?
				
				// query the terrain object for updates so we can display
				// progress to user and we know when it has finished
				
				// transition when finished
				this.currentState = LoadingScreenState.LOADING_ENTITIES;
				
			break;
				
			case LOADING_ENTITIES:
			
				// render the view
				this.view.render(delta);
				
				//Log.info(Config.INFO, "Loading Entities");
				
				// load all entities using the loading script. we do this in script so that
				// the scripting environment knows about entities and can access them in 
				// triggers.
				
				// can this be threaded for feedback purposes?
				
				EntityPersistence entityPersistence = PersistenceManager.getInstance().getEntityPersistence();
				EntityManager entityManager = EntityManager.getInstance();
				int numEntities = entityPersistence.beginLoading();
				Entity[] entities = new Entity[numEntities];
				for (int i = 0; i < numEntities; i += 1) {
					Entity e = entityManager.createEntity();
					entityPersistence.loadEntity(e);
					entities[i] = e;
				}
				
				// set an input processor to listen for any key presses
				this.anyKeyPressedProcessor.addObserver(this);
				Gdx.input.setInputProcessor(this.anyKeyPressedProcessor);
				
				Log.info(Config.INFO, "ready...");
				
				this.currentState = LoadingScreenState.READY;
				
			break;
			
			case READY:
				
				// render the view
				this.view.render(delta);
				
				// wait for user to click or press the any key
				//
				
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
		
		// Create a new view view
		this.view = new LoadingScreenView(assets);
				
		// read the current world name
		PlayerProfile profile = ProfileManager.getInstance().getActiveProfile();
		
		String world 	= profile.getWorldName();
		String campaign = profile.getCampaignName();
				
		// get the filepath for the current world
		StringBuilder sb = StringHelper.getBuilder();
		sb.append(Config.CAMPAIGN_PATH);
		sb.append(campaign);
		sb.append(File.separator);
		sb.append(world);
		sb.append(File.separator);	
		this.currentWorldFilePath = sb.toString();
		
		// get the script filepath
		sb.setLength(0);
		sb.append(this.currentWorldFilePath);
		sb.append("triggers.lua");
		this.scriptPath = sb.toString();
		
		// get the terrain filepath
		sb.setLength(0);
		sb.append(this.currentWorldFilePath);
		sb.append("terrain.raw");
		this.terrainPath = sb.toString();
		
		//script.close();
		//script.initialise(scriptPath);
		//name.execute("entities", "load");
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