/**
 * 
 */
package com.stargem.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.stargem.Config;
import com.stargem.GameManager;
import com.stargem.utils.Log;

/**
 * PersistenceManager.java
 *
 * @author 	Chris B
 * @date	19 Nov 2013
 * @version	1.0
 */
public class PersistenceManager {

	private final Array<ConnectionListener> listeners = new Array<ConnectionListener>();
	
	private Connection connection;

	// layers managed by this class
	private EntityPersistence entityPersistence;
	private ProfilePersistence profilePersistence;
	private SimulationPersistence simulationPersistence;
	
	// map Java datatypes to SQLite datatypes
	private final ObjectMap<String, String> datatypes = new ObjectMap<String, String>();

	
	/**
	 * Return the singleton instance of the persistence manager.
	 * 
	 * @return
	 */
	public static PersistenceManager getInstance() {
		if(instance == null) {
			instance = new PersistenceManager();
		}
		return instance;
	}
	private static PersistenceManager instance;
	private PersistenceManager(){
		
		// map Java datatypes to SQLite datatypes
		// this is used for creating, storing and reading component tables
		/* @formatter:off */
		datatypes.put("boolean", 	"NUMERIC");
		datatypes.put("byte", 		"INTEGER");
		datatypes.put("short", 		"INTEGER");
		datatypes.put("int", 		"INTEGER");
		datatypes.put("long", 		"INTEGER");
		datatypes.put("float", 		"REAL");
		datatypes.put("double", 	"REAL");		
		datatypes.put("char", 		"TEXT");
		datatypes.put("String", 	"TEXT");
		/* @formatter:on */
		
	}
	
	/**
	 * Connects to the specified database then updates
	 * all connection listeners setting their connection
	 * to the new connection. the connection is also returned.
	 * 
	 * @param databasePath the path to the database to connect to
	 * @return the new database connection
	 */
	public Connection connect(String databasePath) {
		ActionResolver ar = GameManager.getInstance().getPlatformResolver().getActionResolver();
		this.connection = ar.getConnection(databasePath);	
		
		// update listeners with the new connection
		for(ConnectionListener o : listeners) {
			o.setConnection(connection);
		}
		
		return this.connection;
	}
	
	/**
	 * Register a new connection contactListener
	 * @param o
	 */
	public void registerConnectionListener(ConnectionListener o) {
		this.listeners.add(o);
	}
	
	/**
	 * Unregister the connection contactListener 
	 * @param o
	 */
	public void unregisterConnectionListener(ConnectionListener o) {
		this.listeners.removeValue(o, false);
	}
	
	/**
	 * get the currently set entity persistence layer
	 * 
	 * @return the entityPersistence
	 */
	public EntityPersistence getEntityPersistence() {
		return entityPersistence;
	}

	/**
	 * Set the entity persistence layer.
	 * 
	 * @param entityPersistence the entityPersistence to set
	 */
	public void setEntityPersistence(EntityPersistence entityPersistence) {
		this.unregisterConnectionListener(this.entityPersistence);
		this.entityPersistence = entityPersistence;
		this.registerConnectionListener(this.entityPersistence);
	}

	/**
	 * get the currently set profile persistence layer
	 * 
	 * @return the profilePersistence
	 */
	public ProfilePersistence getProfilePersistence() {
		return profilePersistence;
	}

	/**
	 * Set the profile persistence layer.
	 * 
	 * @param profilePersistence the profilePersistence to set
	 */
	public void setProfilePersistence(ProfilePersistence profilePersistence) {
		this.unregisterConnectionListener(this.profilePersistence);
		this.profilePersistence = profilePersistence;
		this.registerConnectionListener(this.profilePersistence);
	}

	/**
	 * get the currently set world persistence layer
	 * 
	 * @return the simulationPersistence
	 */
	public SimulationPersistence getSimulationPersistence() {
		return simulationPersistence;
	}

	/**
	 * Set the world persistence layer.
	 * 
	 * @param simulationPersistence the simulationPersistence to set
	 */
	public void setSimulationPersistence(SimulationPersistence simulationPersistence) {
		this.unregisterConnectionListener(this.simulationPersistence);
		this.simulationPersistence = simulationPersistence;
		this.registerConnectionListener(this.simulationPersistence);
	}

	/**
	 * Given a profile name returns the database name without a database file extension
	 * 
	 * @param name the name to be converted to a database name
	 * @return
	 */
	public String getNewDatabaseName(String name) {
		return name.replace(" ", "_");
	}
	
	/**
	 * Check if the given database exists
	 * 
	 * @param databaseName the name of the database to check
	 * @return true if the database exists, false otherwise.
	 */
	public boolean exists(String databaseName) {
		boolean result = Gdx.files.internal(Config.PROFILE_DATABASE_PATH + databaseName + Config.DATABASE_EXTENSION).file().exists();
		return result;
	}
	
	/**
	 * dispose of all resources
	 */
	public void dispose() {
		try {
			this.connection.close();
		}
		catch (SQLException e) {
			Log.error(Config.SQL_ERR, e.getMessage());
		}
	}

	/**
	 * Import the entities and world information from the database specified
	 * by the path, into the currently active profile.
	 * 
	 * @param campaignName
	 * @param levelName
	 */
	public void importWorld(String databasePath) {
		this.entityPersistence.importEntities(databasePath);
		this.simulationPersistence.importWorld(databasePath);		
	}

	/**
	 * @return
	 */
	public ObjectMap<String, String> getDatatypes() {
		return this.datatypes;
	}

	/**
	 * Returns the player entity IDs for the currently loaded world.
	 * 
	 * @return the player entity IDs for the currently loaded world.
	 */
	public IntMap<Integer> getPlayerIDs() {
		return this.simulationPersistence.getPlayerIDs();
	}

	/**
	 * Load one entrance and one exit gate from the db into the gate manager
	 */
	public void loadGates() {
		this.simulationPersistence.loadGates();
	}
	
	/**
	 * Save a gate to the db
	 * 
	 * @param entityID the id of the gate
	 * @param type the type of gate 0 entrance or 1 exit
	 */
	public void saveGate(int entityID, int type) {
		this.simulationPersistence.saveGate(entityID, type);
	}
	
}