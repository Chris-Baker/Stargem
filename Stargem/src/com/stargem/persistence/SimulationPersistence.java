/**
 * 
 */
package com.stargem.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.stargem.Config;
import com.stargem.utils.AssetList;
import com.stargem.utils.Log;
import com.stargem.utils.StringHelper;

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
	
	

}