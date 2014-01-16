package com.stargem;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.stargem.entity.EntityManager;
import com.stargem.entity.components.Component;
import com.stargem.entity.components.Expires;
import com.stargem.entity.components.Physics;
import com.stargem.entity.components.RenderableSkinned;
import com.stargem.entity.components.RenderableStatic;
import com.stargem.entity.components.Trigger;
import com.stargem.graphics.RepresentationManager;
import com.stargem.models.Simulation;
import com.stargem.physics.PhysicsManager;
import com.stargem.profile.ProfileManager;
import com.stargem.screens.LoadingScreen;
import com.stargem.sql.DatabaseFactory;
import com.stargem.sql.EntityPersistence;
import com.stargem.sql.PersistenceManager;
import com.stargem.sql.ProfilePersistence;
import com.stargem.sql.SimulationPersistence;
import com.stargem.views.SimulationView;

/**
 * Stargem.java
 * 
 * @author 24233
 * @date 14 Nov 2013
 * @version 1.0
 */
public class Stargem implements ApplicationListener {

	// asset manager
	private final AssetManager assetManager = new AssetManager();
	
	// entity manager
	private final EntityManager entityManager = EntityManager.getInstance();

	// entity persistence manager
	private final PersistenceManager persistenceManager = PersistenceManager.getInstance();

	// phsysics manager keeps track of physics objects
	private final PhysicsManager physicsManager = PhysicsManager.getInstance();

	// the current game user profileManager. chosen by the profileManager select currentScreen or
	// create by the create profileManager currentScreen and then selected.
	// once selected the player is on the profileManager menu currentScreen.
	// if the profileManager menu currentScreen is exited then the profileManager is unloaded.
	private final ProfileManager profileManager = ProfileManager.getInstance();

	//representation manager keeps track of model instances
	private final RepresentationManager representationManager = RepresentationManager.getInstance();

	// the main game simulation and its renderer
	private final Simulation simulation = new Simulation();
	private final SimulationView simulationView = new SimulationView();
	
	// array of all component types
	private final Array<Class<? extends Component>> componentTypes = new Array<Class<? extends Component>>();
	
	// screens
	private Screen currentScreen;

	// resume currentScreen
	// splash currentScreen
	// main menu currentScreen
	// create profileManager currentScreen
	// select profileManager currentScreen
	// profileManager menu currentScreen
	// options currentScreen

	// loading currentScreen
	private final Screen loadingScreen = new LoadingScreen(this);

	// briefing currentScreen
	// play currentScreen
	// shop currentScreen	

	@Override
	public void create() {

		// Set the logging level to everything
		Gdx.app.setLogLevel(Application.LOG_INFO);
		Log.setLogLevel(Log.INFO);
		
		// create the entity persistence layer
		// It needs to be registered with the entity manager
		// so that it can observe and track entity recycle events.
		EntityPersistence entityPersistence = new EntityPersistence();
		entityManager.registerEntityRecycleObserver(entityPersistence);

		// add all the component types which need to be tracked.		
		this.componentTypes.add(Expires.class);
		this.componentTypes.add(Physics.class);
		this.componentTypes.add(RenderableStatic.class);
		this.componentTypes.add(RenderableSkinned.class);
		this.componentTypes.add(Trigger.class);
		
		for(Class<? extends Component> type : this.componentTypes) {
			entityPersistence.registerComponentType(type);
		}	

		ProfilePersistence profilePersistence = new ProfilePersistence();		
		SimulationPersistence worldPersistence = new SimulationPersistence();		
		
		// register the persistence layers with the persistence manager
		this.persistenceManager.setEntityPersistence(entityPersistence);
		this.persistenceManager.setProfilePersistence(profilePersistence);
		this.persistenceManager.setWorldPersistence(worldPersistence);
		
		// This is to assign the asset manager to reload textures on resume
		Texture.setAssetManager(assetManager);
		
		// this allows the representation manager to load assets from the asset manager
		this.representationManager.setAssetManager(this.assetManager);

		// resume currentScreen
		// splash currentScreen
		// main menu currentScreen
		// create profileManager currentScreen
		// select profileManager currentScreen
		// profileManager menu currentScreen
		// options currentScreen

		// loading currentScreen
		// briefing currentScreen
		// play currentScreen
		// shop currentScreen		

		// sound manager

		// create profile or load it
		try {
			String profileName = "Chris Baker";
			String databaseName = this.persistenceManager.getNewDatabaseName(profileName);
			if (this.persistenceManager.exists(databaseName)) {
				Log.info("profile", "loading old profile " + databaseName);				
				this.loadProfile(databaseName);
			}
			else {
				Log.info("profile", "loading new profile");
				this.newProfile(profileName);
			}						
		}
		catch (Exception e) {
			Log.error(Config.IO_ERR, e.getMessage() + " whilst trying to load profile");
		}
		
		// start the game
		this.loadGame();
		//this.createWorld();
		
	}

	/**
	 * this method simulates creating a new world from the
	 * world editor.
	 */
	private void createWorld() {
		
		String campaignName = Config.DEFAULT_CAMPAIGN;
		String worldName = Config.DEFAULT_WORLD;
		
		// build the path to the world's database
		StringBuilder s = StringHelper.getBuilder();
		s.append(Config.WORLD_DATABASE_PATH);
		s.append(campaignName);
		//s.append(File.separator);
		s.append("/");
		s.append(worldName);
		//s.append(File.separator);
		s.append("/");
		s.append(Config.WORLD_DATABASE_NAME);
		s.append(Config.DATABASE_EXTENSION);
		String worldDatabasePath = s.toString();
		
		DatabaseFactory.createWorldDatabase(worldDatabasePath, this.componentTypes);
		
	}
	
	/**
	 * Connect to the chosen game connection and load the profile information
	 * 
	 * @param databaseName the name of the connection to connect to
	 */
	public void loadProfile(String databaseName) throws Exception {
		
		// if the database doesn't exist then throw an error
		// this happens here because the default behaviour of the database manager
		// is to create a database if it doesn;t exist, we don't want this.
		if(!this.persistenceManager.exists(databaseName)) {
			Log.error(Config.IO_ERR, "Profile not found");
			throw new Exception("Profile not found: " + databaseName);
		}
		
		// build the path to the database
		StringBuilder s = StringHelper.getBuilder();
		s.append(Config.PROFILE_DATABASE_PATH);
		s.append(databaseName);
		s.append(Config.DATABASE_EXTENSION);
		String databasePath = s.toString();
		
		// connect to the database
		this.persistenceManager.connect(databasePath);
		
		// load the profile data
		this.profileManager.loadProfile(databaseName);
		
	}

	/**
	 * Create a new profile with the given name. A database is created
	 * whose name is derived from the name given.
	 * 
	 * @param name the name of the profile, the database name is derived from this
	 */
	public void newProfile(String name) throws Exception {
		
		String databaseName = this.persistenceManager.getNewDatabaseName(name);
		
		// We don't want to try to create existing databases
		if(this.persistenceManager.exists(databaseName)) {
			throw new Exception("Cannot create new profile because it already exists: " + databaseName);
		}
		
		// build the path to the database
		StringBuilder s = StringHelper.getBuilder();
		s.append(Config.PROFILE_DATABASE_PATH);
		s.append(databaseName);
		s.append(Config.DATABASE_EXTENSION);
		String databasePath = s.toString();
		
		// create and connect to the database
		this.persistenceManager.connect(databasePath);
		
		// create a new profile with the name given
		this.profileManager.newProfile(name, databaseName);
	}
	
	/**
	 * Load the game associated with the currently selected profile.
	 * If there is no world selected in the profile then this is a new
	 * game and the default campaign and level are imported and used.
	 * The loading screen is then displayed. The loading screen takes
	 * care of actually loading the assets from disk before continuing
	 * to the game.
	 */
	public void loadGame() {
		
		// if the profile has not been loaded then we should not be calling this function yet
		if(this.profileManager.isLoaded()) {			
			throw new Error("Method called out of order. A profile must be selected before the game can be loaded.");
		}
		
		// if there is no level or campaign set then set the default one
		// this could be changed in future to allow selection of different campaigns
		// levels associated with a campaign could be stored in a jSON or XML file
		// for now we will just hard code the campaign and first level.
		if(!this.profileManager.isLevelSet()) {
			this.changeWorld(Config.DEFAULT_CAMPAIGN, Config.DEFAULT_WORLD);
		}
		
		this.setScreen(this.loadingScreen);
		
	}
	
	/**
	 * Copy the world information for the given world into the currently loaded profile.
	 * 
	 * @param campaignName
	 * @param levelName
	 */
	public void changeWorld(String campaignName, String levelName) {
				
		// build the path to the world's database
		StringBuilder s = StringHelper.getBuilder();
		s.append(Config.WORLD_DATABASE_PATH);
		s.append(campaignName);
		//s.append(File.separator);
		s.append("/");
		s.append(levelName);
		//s.append(File.separator);
		s.append("/");
		s.append(Config.WORLD_DATABASE_NAME);
		s.append(Config.DATABASE_EXTENSION);
		String worldDatabasePath = s.toString();
		
		// import entity and world tables from the world database into the active profile
		this.persistenceManager.importWorld(worldDatabasePath);
		
		// set the campaign and level in the profile
		this.profileManager.setCampaignName(campaignName);
		this.profileManager.setLevelName(levelName);
		
	}
	
	// TODO change level
	// fade out
	// update profile
	// go to loading currentScreen

	
	
	/**
	 * @return the asset manager
	 */
	public AssetManager getAssetManager() {
		return this.assetManager;
	}
	
	/**
	 * Sets the current currentScreen. {@link Screen#hide()} is called on any old currentScreen, and {@link Screen#show()} is called on the new
	 * currentScreen, if any.
	 * 
	 * @param currentScreen may be {@code null}
	 */
	public void setScreen(Screen screen) {
		if (this.currentScreen != null) {
			this.currentScreen.hide();
		}
		this.currentScreen = screen;
		if (this.currentScreen != null) {
			this.currentScreen.show();
			this.currentScreen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
	}

	/** @return the currently active {@link Screen}. */
	public Screen getScreen() {
		return currentScreen;
	}

	@Override
	public void resize(int width, int height) {
		// TODO resize all screens
	}

	@Override
	public void render() {
		if (currentScreen != null) {
			currentScreen.render(Gdx.graphics.getDeltaTime());
		}
	}

	@Override
	public void pause() {
		// TODO throw a pause event to the current currentScreen
		// this could pause gameplay in the one player mode
		// or simply display the in game menu in coop.
		// it is the same event as pressing escape or alt-tab in game
	}

	@Override
	public void resume() {

		// TODO reload OpenGL context sensitive assetManager such as textures
		// set the current currentScreen to the previous currentScreen
		// display the resume currentScreen which reloads assetManager with lost contexts

		// TODO throw a resume event to the current currentScreen
	}

	@Override
	public void dispose() {
		
		this.loadingScreen.dispose();
		
		this.physicsManager.dispose();
		this.assetManager.dispose();
		this.persistenceManager.dispose();
	}
	
}