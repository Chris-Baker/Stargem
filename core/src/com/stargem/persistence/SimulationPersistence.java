/**
 * 
 */
package com.stargem.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.badlogic.gdx.utils.IntMap;
import com.stargem.Config;
import com.stargem.utils.AssetList;
import com.stargem.utils.Log;
import com.stargem.utils.StringHelper;
import com.stargem.world.WorldDetails;

/**
 * SimulationPersistence.java
 * 
 * @author 	Chris B
 * @date 	18 Nov 2013
 * @version 1.0
 */
public class SimulationPersistence implements ConnectionListener {

	private Connection connection;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.stargem.persistence.ConnectionListener#updateConnection(java.sql.Connection)
	 */
	@Override
	public void setConnection(Connection c) {
		this.connection = c;
	}

	/**
	 * Populate the given asset list from the currently active profile
	 * 
	 * @param list the asset list to be populated
	 */
	public void populateAssetList(AssetList list) {
		
		// select assets from db
		StringBuilder sql = StringHelper.getBuilder();
		sql.append("SELECT * FROM ");
		sql.append(Config.TABLE_ASSETS);
		sql.append(";");
		
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql.toString());
			
			// add each asset to the list
			while(result.next()) {				
				String path = result.getString(1);
				Class<?> type = Class.forName(result.getString(2));				
				list.add(path, type);
			}			
			
			result.close();
			statement.close();
		}
		catch (SQLException e) {
			Log.error(Config.SQL_ERR, e.getMessage() + " while selecting assets " + sql.toString());
		}
		catch (ClassNotFoundException e) {
			Log.error(Config.REFLECTION_ERR, e.getMessage() + " Class not found whilst populating asset list.");
		}
	}

	/**
	 * Copy the world, Asset, and Players tables from the world database at the 
	 * path given into the currently active profile. This is done as part of the 
	 * level changing process.
	 * 
	 * @param databasePath path to the world database to attach to the current profile connection
	 */
	protected void importWorld(String databasePath) {
		
		// attach the database for the selected world
		String attachName = "world";
		SQLHelper.attach(connection, databasePath, attachName);		
		
		// copy the World and Asset tables
		String[] tables = new String[3];
		tables[0] = Config.TABLE_WORLD;
		tables[1] = Config.TABLE_ASSETS;
		tables[2] = Config.TABLE_PLAYERS;
		
		for(int i = 0, n = tables.length; i < n; i += 1) {		
			String toTableName = "main." + tables[i];
			String fromTableName = "\"" + attachName + "\"" + "." + tables[i];
			
			SQLHelper.dropTable(connection, toTableName);
			SQLHelper.createAs(connection, fromTableName, toTableName);
		}
		
		// detach the database
		SQLHelper.detach(connection, attachName);		
	}
	
	/**
	 * Selects the players table and creates a map of player numbers to entity IDs.
	 * Returns the player entity IDs for the currently loaded world.
	 * 
	 * @return the player entity IDs for the currently loaded world.
	 */
	public IntMap<Integer> getPlayerIDs() {		
		IntMap<Integer> playerEntityIDs = new IntMap<Integer>();
		
		StringBuilder sql = StringHelper.getBuilder();
		sql.append("SELECT playerId, entityId FROM ");
		sql.append(Config.TABLE_PLAYERS);
		sql.append(";");

		try {
			Statement statement = this.connection.createStatement();
			ResultSet result = statement.executeQuery(sql.toString());
						
			while(result.next()) {
				int playerId = result.getInt(1);
				int entityId = result.getInt(2);
				playerEntityIDs.put(playerId, entityId);
			}
			
			result.close();
			statement.close();
		}
		catch (SQLException e) {
			Log.error(Config.SQL_ERR, e.getMessage());
		}
		
		return playerEntityIDs;
	}

	/**
	 * Populate the world details from the database.
	 * 
	 * @param details a world details object to populate from the database.
	 * @return the populated details object.
	 */
	public WorldDetails populateWorldDetails(WorldDetails details) {
		
		StringBuilder sql = StringHelper.getBuilder();
		sql.append("SELECT * FROM ");
		sql.append(Config.TABLE_WORLD);
		sql.append(";");
		
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql.toString());
			
			result.next();
			
			String name = result.getString(1);
			String musicTrack = result.getString(2);
			String ambianceTrack = result.getString(3);
			String skyboxTexture_1 = result.getString(4);
			String skyboxTexture_2 = result.getString(5);
			String skyboxTexture_3 = result.getString(6);
			String skyboxTexture_4 = result.getString(7);
			String skyboxTexture_5 = result.getString(8);
			String skyboxTexture_6 = result.getString(9);
			String terrainTexture_1 = result.getString(10);
			String terrainTexture_2 = result.getString(11);
			String terrainTexture_3 = result.getString(12);
			int terrainScale = result.getInt(13);
			int terrainSegmentWidth = result.getInt(14);
			int terrainNumSegments = result.getInt(15);
			
			details.setName(name);
			details.setMusicTrack(musicTrack);
			details.setAmbianceTrack(ambianceTrack);
			details.setSkyboxTexture_1(skyboxTexture_1);
			details.setSkyboxTexture_2(skyboxTexture_2);
			details.setSkyboxTexture_3(skyboxTexture_3);
			details.setSkyboxTexture_4(skyboxTexture_4);
			details.setSkyboxTexture_5(skyboxTexture_5);
			details.setSkyboxTexture_6(skyboxTexture_6);
			details.setTerrainTexture_1(terrainTexture_1);
			details.setTerrainTexture_2(terrainTexture_2);
			details.setTerrainTexture_3(terrainTexture_3);
			details.setTerrainScale(terrainScale);
			details.setTerrainSegmentWidth(terrainSegmentWidth);
			details.setTerrainNumSegments(terrainNumSegments);
			
			result.close();
			statement.close();
		}
		catch (SQLException e) {
			Log.error(Config.SQL_ERR, e.getMessage() + " while selecting world details " + sql.toString());
		}
		
		return details;
	}
	
}