/**
 * 
 */
package com.stargem.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.stargem.Config;
import com.stargem.utils.Log;

/**
 * DesktopActionResolver.java
 * 
 * @author Chris B
 * @date 8 Nov 2013
 * @version 1.0
 */
public class DesktopActionResolver implements ActionResolver {

	/**
	 * Connect to the database with the name given. If
	 * the database is not found then it is created,
	 * This is the default behaviour of the db controller.
	 * 
	 * @param databaseName the name of the database to connect to or create
	 * @return the connection if one is made, null otherwise
	 */
	@Override
	public Connection getConnection(String databaseName) {
		String url = "jdbc:sqlite:" + databaseName;
		try {
			Class.forName("org.sqlite.JDBC");
			return DriverManager.getConnection(url);
		}
		catch (ClassNotFoundException e) {
			Log.error(Config.SQL_ERR, e.getMessage());
		}
		catch (SQLException e) {
			Log.error(Config.SQL_ERR, e.getMessage());
		}
				
		return null;
	}

}