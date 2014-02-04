/**
 * 
 */
package com.stargem.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.stargem.Config;
import com.stargem.profile.PlayerProfile;
import com.stargem.utils.Log;
import com.stargem.utils.StringHelper;

/**
 * ProfilePersistence.java
 *
 * @author 	Chris B
 * @date	16 Nov 2013
 * @version	1.0
 */
public class ProfilePersistence implements ConnectionListener {

	// the connection connection
	private Connection connection;
	
	// a statement for executing sql queries
	private Statement statement;
	
	// a result set for holding results
	private ResultSet result;
				
	/**
	 * get current level
	 * 
	 * @return the name of the current level
	 */
	public String getLevelName(String databaseName) {
		
		String level = null;
		
		StringBuilder sql = StringHelper.getBuilder();
		sql.append("SELECT level FROM Profile WHERE databaseName=\"");
		sql.append(databaseName);
		sql.append("\";");

		try {

			this.statement = this.connection.createStatement();
			this.result = this.statement.executeQuery(sql.toString());
			if(result.next()) {
				level = result.getString(1);
			}
			
			this.statement.close();
			this.result.close();
		}
		catch (SQLException e) {
			Log.error(Config.SQL_ERR, e.getMessage() + " while accessing the Profile table: " + sql.toString());
		}
		return level;
	}
	
	/**
	 * get current campaign
	 * 
	 * @return the name of the current campaign
	 */
	public String getCampaignName(String databaseName) {
		
		String campaign = null;
		
		StringBuilder sql = StringHelper.getBuilder();
		sql.append("SELECT campaign FROM Profile WHERE databaseName=\"");
		sql.append(databaseName);
		sql.append("\";");

		try {

			this.statement = this.connection.createStatement();
			this.result = this.statement.executeQuery(sql.toString());
			if(result.next()) {
				campaign = result.getString(1);	
			}
			
			this.result.close();
			this.statement.close();
		}
		catch (SQLException e) {
			Log.error(Config.SQL_ERR, e.getMessage() + " while accessing the Profile table: " + sql.toString());
		}
		return campaign;
	}

	/**
	 * Insert the profile into the connection storing the name and connection name.
	 * The campaign and level should be updated separately.
	 * 
	 * @param profile the profile to store
	 */
	public void insertProfile(PlayerProfile profile) {
		
		StringBuilder sql = StringHelper.getBuilder();
		sql.append("INSERT INTO Profile (databaseName, name) VALUES(");
		sql.append("\"");
		sql.append(profile.getDatabaseName());
		sql.append("\", \"");
		sql.append(profile.getName());
		sql.append("\");");
		
		// run the query
		try {
			this.statement = this.connection.createStatement();
			this.statement.executeUpdate(sql.toString());
			this.statement.close();
		}
		catch (SQLException e) {
			Log.error(Config.SQL_ERR, e.getMessage() + " while inserting into the Profile table: " + sql.toString());
		}		
	}

	/**
	 * update the campaign name in the database with the name in the provided profile.
	 * 
	 * @param profile the currently active player profile
	 */
	public void updateCampaignName(PlayerProfile profile) {
		
		StringBuilder sql = StringHelper.getBuilder();
		sql.append("UPDATE Profile SET campaign=");
		sql.append("\"");
		sql.append(profile.getCampaignName());
		sql.append("\" WHERE databaseName=\"");
		sql.append(profile.getDatabaseName());
		sql.append("\";");
		
		// run the query
		try {
			this.statement = this.connection.createStatement();
			this.statement.executeUpdate(sql.toString());
			this.statement.close();
		}
		catch (SQLException e) {
			Log.error(Config.SQL_ERR, e.getMessage() + " while updating the campaign name: " + sql.toString());
		}
	}

	/**
	 * update the world name in the database with the name in the provided profile.
	 * 
	 * @param profile the currently active player profile
	 */
	public void updateWorldName(PlayerProfile profile) {
		StringBuilder sql = StringHelper.getBuilder();
		sql.append("UPDATE Profile SET level=");
		sql.append("\"");
		sql.append(profile.getWorldName());
		sql.append("\" WHERE databaseName=\"");
		sql.append(profile.getDatabaseName());
		sql.append("\";");
		
		// run the query
		try {
			this.statement = this.connection.createStatement();
			this.statement.executeUpdate(sql.toString());
			this.statement.close();
		}
		catch (SQLException e) {
			Log.error(Config.SQL_ERR, e.getMessage() + " while updating the world name: " + sql.toString());
		}
	}

	/* (non-Javadoc)
	 * @see com.stargem.persistence.ConnectionListener#setConnection(java.sql.Connection)
	 */
	@Override
	public void setConnection(Connection c) {
		this.connection = c;
	}

	/**
	 * @param databaseName
	 * @return
	 */
	public String getProfileName(String databaseName) {
		return databaseName.replace(" ", "_");
	}
}