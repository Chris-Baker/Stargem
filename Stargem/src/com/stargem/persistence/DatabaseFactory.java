/**
 * 
 */
package com.stargem.persistence;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.stargem.Config;
import com.stargem.entity.components.Component;
import com.stargem.utils.Log;
import com.stargem.utils.StringHelper;

/**
 * DatabaseFactory.java
 *
 * @author 	Chris B
 * @date	19 Dec 2013
 * @version	1.0
 */
public class DatabaseFactory {
	
	public static void createProfileDatabase(String databasePath) {
		
		ActionResolver ar = new DesktopActionResolver();
		Connection connection = ar.getConnection(databasePath);
		
		// profile table
		createProfileTable(connection);
		
	}
	
	public static void createWorldDatabase(String databasePath, Array<Class<? extends Component>> componentTypes) {
		
		ActionResolver ar = new DesktopActionResolver();
		Connection connection = ar.getConnection(databasePath);
				
		// assets table
		createAssetsTable(connection);
		
		// world table
		createWorldTable(connection);
		
		// players table
		createPlayerTable(connection);
		
		// entity table
		createEntityTable(connection);
				
		// try to create a table for each of the component types
		for (Class<? extends Component> type : componentTypes) {
			createTableFromComponentType(connection, type);
		}
	}
		
	/**
	 * Create a table to store assets. This is called by the editor
	 * and later imported into the game from a world database.
	 */
	private static void createAssetsTable(Connection connection) {

		// CREATE TABLE IF NOT EXISTS Assets (path TEXT PRIMARY KEY, shape TEXT);
		
		StringBuilder sql = StringHelper.getBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS ");
		sql.append(Config.TABLE_ASSETS);
		sql.append(" (path TEXT PRIMARY KEY, shape TEXT);");

		// run the query
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql.toString());
			statement.close();
		}
		catch (SQLException e) {
			Log.error(Config.SQL_ERR, e.getMessage() + " while creating the assets table: " + sql.toString());
		}
	}
	
	/**
	 * Create a table to store world information. This is called by the editor
	 * and later imported into the game from a world database.
	 */
	private static void createWorldTable(Connection connection) {
		
		// CREATE TABLE IF NOT EXISTS World (name TEXT PRIMARY KEY, music TEXT, ambiance TEXT, skybox TEXT);
				
		StringBuilder sql = StringHelper.getBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS ");
		sql.append(Config.TABLE_WORLD);
		sql.append(" (name TEXT PRIMARY KEY, music TEXT, ambiance TEXT, skybox TEXT);");

		// run the query
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql.toString());
			statement.close();
		}
		catch (SQLException e) {
			Log.error(Config.SQL_ERR, e.getMessage() + " while creating the world table: " + sql.toString());
		}
	}
	
	/**
	 * create a table to match entity id's with player id's
	 * player id's are indices in the player list stored by the simulation
	 */
	private static void createPlayerTable(Connection connection) {

		StringBuilder sql = StringHelper.getBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS ");
		sql.append(Config.TABLE_PLAYERS);
		sql.append(" (playerId INTEGER PRIMARY KEY, entityId INTEGER);");

		// run the query
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql.toString());
			statement.close();
		}
		catch (SQLException e) {
			Log.error(Config.SQL_ERR, e.getMessage() + " while creating the players table: " + sql.toString());
		}
	}
	
	/**
	 * Create a table to store entity id's. The id's are generated automatically.
	 */
	private static void createEntityTable(Connection connection) {

		StringBuilder sql = StringHelper.getBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS Entity (entityId INTEGER PRIMARY KEY);");

		// run the query
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql.toString());
			statement.close();
		}
		catch (SQLException e) {
			Log.error(Config.SQL_ERR, e.getMessage() + " while creating the Entity table: " + sql.toString());
		}
	}

	/**
	 * create a table which serialises the given component shape
	 * 
	 * @param shape the component shape to create a table for
	 */
	private static void createTableFromComponentType(Connection connection, Class<? extends Component> type) {

		ObjectMap<String, String> datatypes = PersistenceManager.getInstance().getDatatypes();
		
		// build a query to create  a new table
		StringBuilder sql = StringHelper.getBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS ");
		sql.append(type.getSimpleName());
		sql.append(" (");
		sql.append("entityId INTEGER PRIMARY KEY,");

		Field[] fields = type.getFields();

		for (int i = 0, n = fields.length; i < n; i += 1) {

			Field f = fields[i];
			String datatype = f.getType().getSimpleName();

			// if this is a recognised datatype then we can add it as a table field			
			if (datatypes.containsKey(datatype)) {
				sql.append(f.getName());
				sql.append(" ");
				sql.append(datatypes.get(datatype));

				// add a comma if this is not the last field
				if (i < n - 1) {
					sql.append(", ");
				}
			}

			// The datatype was not recognised so we should create a table to hold the
			// object and its fields. This is done with a recursive call.
			else {
				//Log.echo("Some kind of recursive call?");
				throw new Error("Unknown shape: " + datatype + " can only store primitives and Strings.");
			}
		}

		sql.append(");");

		// run the query
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql.toString());
			statement.close();
		}
		catch (SQLException e) {
			Log.error(Config.SQL_ERR, e.getMessage() + " while creating the " + type.getSimpleName() + " table: " + sql.toString());
		}
	}
	
	/**
	 * 
	 */
	private static void createProfileTable(Connection connection) {
		StringBuilder sql = StringHelper.getBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS Profile (databaseName TEXT PRIMARY KEY, name TEXT, level TEXT, campaign TEXT);");

		// run the query
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql.toString());
			statement.close();
		}
		catch (SQLException e) {
			Log.error(Config.SQL_ERR, e.getMessage() + " while creating the Profile table: " + sql.toString());
		}
	}
}