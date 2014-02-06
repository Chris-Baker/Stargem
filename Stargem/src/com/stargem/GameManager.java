/**
 * 
 */
package com.stargem;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.stargem.entity.Entity;
import com.stargem.entity.EntityManager;
import com.stargem.entity.components.Component;
import com.stargem.entity.components.Expires;
import com.stargem.entity.components.KeyboardMouseController;
import com.stargem.entity.components.Physics;
import com.stargem.entity.components.RenderableSkinned;
import com.stargem.entity.components.RenderableStatic;
import com.stargem.entity.components.RunSpeed;
import com.stargem.entity.components.ThirdPersonCamera;
import com.stargem.entity.components.Trigger;
import com.stargem.graphics.RepresentationManager;
import com.stargem.persistence.DatabaseFactory;
import com.stargem.persistence.EntityPersistence;
import com.stargem.persistence.PersistenceManager;
import com.stargem.persistence.ProfilePersistence;
import com.stargem.persistence.SimulationPersistence;
import com.stargem.physics.PhysicsManager;
import com.stargem.profile.ProfileManager;
import com.stargem.utils.Log;
import com.stargem.utils.StringHelper;


/**
 * GameManager.java
 *
 * @author 	Chris B
 * @date	18 Nov 2013
 * @version	1.0
 */
public class GameManager {
	
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

	// players manager keeps track of the active players in a game.
	private final PlayersManager playersManager = PlayersManager.getInstance();
	
	// array of all component types
	private final Array<Class<? extends Component>> componentTypes = new Array<Class<? extends Component>>();

	// the game instance that this game manager manages.
	private Stargem game;
			
	/**
	 * Return the singleton instance of the game manager.
	 * 
	 * @return
	 */
	public static GameManager getInstance() {
		if(instance == null) {
			instance = new GameManager();
		}
		return instance;
	}
	private static GameManager instance;
	
	private GameManager () {
		
		// add all the component types which need to be tracked.		
		this.componentTypes.add(Expires.class);
		this.componentTypes.add(KeyboardMouseController.class);
		this.componentTypes.add(Physics.class);
		this.componentTypes.add(RenderableStatic.class);
		this.componentTypes.add(RenderableSkinned.class);
		this.componentTypes.add(RunSpeed.class);
		this.componentTypes.add(ThirdPersonCamera.class);
		this.componentTypes.add(Trigger.class);
		
		// create the entity persistence layer
		// It needs to be registered with the entity manager
		// so that it can observe and track entity recycle events.
		EntityPersistence entityPersistence = new EntityPersistence();
		entityManager.registerEntityRecycleObserver(entityPersistence);
		
		for(Class<? extends Component> type : this.componentTypes) {
			entityPersistence.registerComponentType(type);
		}

		ProfilePersistence profilePersistence = new ProfilePersistence();		
		SimulationPersistence worldPersistence = new SimulationPersistence();		
		
		// register the persistence layers with the persistence manager
		this.persistenceManager.setEntityPersistence(entityPersistence);
		this.persistenceManager.setProfilePersistence(profilePersistence);
		this.persistenceManager.setSimulationPersistence(worldPersistence);
		
		// This is to assign the asset manager to reload textures on resume
		Texture.setAssetManager(assetManager);
		
		// this allows the representation manager to load assets from the asset manager
		this.representationManager.setAssetManager(this.assetManager);
		
	}
	
	/**
	 * this method simulates creating a new world from the
	 * world editor.
	 */
	public void createWorld() {
		
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
		
		// create the database tables for a profile
		DatabaseFactory.createProfileDatabase(databasePath);
		
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
		if(!this.profileManager.isLoaded()) {			
			throw new Error("Method called out of order. A profile must be selected before the game can be loaded.");
		}
		
		// if there is no level or campaign set then set the default one
		// this could be changed in future to allow selection of different campaigns
		// levels associated with a campaign could be stored in a jSON or XML file
		// for now we will just hard code the campaign and first level.
		
		// is this a new game?
		if(!this.profileManager.isLevelSet()) {
			
			// register a new player and set it as the local player
			this.playersManager.join();
			
			// set the starting campaign and world
			this.changeWorld(Config.DEFAULT_CAMPAIGN, Config.DEFAULT_WORLD);
						
		}
		
		game.setLoadingScreen();
		
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
		
		// unload entities from the entity manager so that we have a clean start
		// we want to keep any entities that are players because they are persistent
		// form world to world.
		this.unloadNonPlayerEntities();
				
		// clear player entity IDs so they can be set by the new world import
		this.playersManager.resetPlayerIds();
		
		// import entity and world tables from the world database into the active profile
		this.persistenceManager.importWorld(worldDatabasePath);
				
		// set the new player entity IDs from the world import
		this.playersManager.setPlayerIDs(this.persistenceManager.getPlayerIDs());
		
		// set the campaign and level in the profile
		this.profileManager.setCampaignName(campaignName);
		this.profileManager.setLevelName(levelName);
		
	}
	
	/**
	 * Remove all non player entities from the world.
	 */
	private void unloadNonPlayerEntities() {		
		for(Entity e : this.entityManager.getAllEntities()) {			
			// if the players list does not include this entity recycle it
			if(!this.playersManager.playerExists(e)) {
				this.entityManager.recycle(e);
			}		
		}
		
		// we have to save entities here so that the deleted entity list is cleared
		this.persistenceManager.save();
	}
	
	/**
	 * @return the asset manager
	 */
	public AssetManager getAssetManager() {
		return this.assetManager;
	}

	/**
	 * @param stargem
	 */
	public void setGame(Stargem game) {
		this.game = game;
	}
	
	/**
	 * dispose of all resources created by the game manager
	 */
	public void dispose() {
		
		// dispose of all managers
		this.persistenceManager.dispose();
		this.physicsManager.dispose();
		this.representationManager.dispose();
		this.assetManager.dispose();	
	}
}