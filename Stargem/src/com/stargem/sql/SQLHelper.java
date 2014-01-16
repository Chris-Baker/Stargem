/**
 * 
 */
package com.stargem.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.OperationNotSupportedException;

import com.stargem.Config;
import com.stargem.Log;
import com.stargem.StringHelper;

/**
 * SQLHelper.java
 * 
 * @author 	Chris B
 * @date 	18 Dec 2013
 * @version 1.0
 */
public class SQLHelper {

	private SQLHelper() throws OperationNotSupportedException {
		throw new OperationNotSupportedException("Operation not supported: Creating SQLHelper object");
	}
	
	/**
	 * attach the database at the path given. the database can then be referenced
	 * through its attach name.
	 * 
	 * @param connection
	 * @param databasePath
	 * @param attachName
	 */
	public static void attach(Connection connection, String databasePath, String attachName) {

		StringBuilder sql = StringHelper.getBuilder();
		
		sql.append("ATTACH ");
		sql.append("\"");
		sql.append(databasePath);
		sql.append("\" ");
		sql.append("AS \"");
		sql.append(attachName);
		sql.append("\";");

		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql.toString());
			statement.close();
		}
		catch (SQLException e) {
			Log.error(Config.SQL_ERR, e.getMessage() + " while attaching database at " + databasePath + " " + sql.toString());
		}
	}

	/**
	 * Detach the attached database given
	 * 
	 * @param connection
	 * @param attachName
	 */
	public static void detach(Connection connection, String attachName) {
		
		StringBuilder sql = StringHelper.getBuilder();
		
		sql.append("DETACH DATABASE ");
		sql.append("\"");
		sql.append(attachName);
		sql.append("\";");

		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql.toString());
			statement.close();
		}
		catch (SQLException e) {
			Log.error(Config.SQL_ERR, e.getMessage() + " while detaching database " + attachName + " " + sql.toString());
		}		
	}
	
	/**
	 * Copy the contents of the table given by fromTableName into toTableName.
	 * 
	 * @param connection
	 * @param fromTableName
	 * @param string
	 */
	public static void copyTable(Connection connection, String fromTableName, String toTableName) {
		
		StringBuilder sql = StringHelper.getBuilder();
		
		sql.append("INSERT INTO ");
		sql.append(toTableName);
		sql.append(" SELECT * FROM ");
		sql.append(fromTableName);
		sql.append(";");
		
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql.toString());
			statement.close();
		}
		catch (SQLException e) {
			Log.error(Config.SQL_ERR, e.getMessage() + " while copying table " + fromTableName + " " + sql.toString());
		}
		
	}

	/**
	 * Drop the given table if it exists
	 * 
	 * @param connection
	 * @param fromTableName
	 */
	public static void dropTable(Connection connection, String tableName) {
		
		StringBuilder sql = StringHelper.getBuilder();
		
		sql.append("DROP TABLE IF EXISTS ");
		sql.append(tableName);
		sql.append(";");
		
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql.toString());
			statement.close();
		}
		catch (SQLException e) {
			Log.error(Config.SQL_ERR, e.getMessage() + " while dropping table " + tableName + " " + sql.toString());
		}
		
	}

	/**
	 * Create a new table with the name given by toTableName based on the
	 * table given by fromTableName. All fields and indices are copied.
	 * 
	 * @param connection
	 * @param fromTableName
	 * @param string
	 */
	public static void createAs(Connection connection, String fromTableName, String toTableName) {
		
		StringBuilder sql = StringHelper.getBuilder();
		
		sql.append("CREATE TABLE ");
		sql.append(toTableName);
		sql.append(" AS SELECT * FROM ");
		sql.append(fromTableName);
		sql.append(";");
		
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql.toString());
			statement.close();
		}
		catch (SQLException e) {
			Log.error(Config.SQL_ERR, e.getMessage() + " while creating table " + toTableName + " like " + fromTableName + " " + sql.toString());
		}
		
	}
	
}