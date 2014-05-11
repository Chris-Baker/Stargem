/**
 * 
 */
package com.stargem.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import android.content.Context;
import android.os.Handler;

import com.stargem.utils.Log;

/**
 * AndroidActionResolver.java
 * 
 * @author Chris B
 * @date 8 Nov 2013
 * @version 1.0
 */
public class AndroidActionResolver implements ActionResolver {

	Handler uiThread;
	Context appContext;

	public AndroidActionResolver(Context appContext) {
		uiThread = new Handler();
		this.appContext = appContext;
	}

	/**
	 * Connect to the database with the name given. If
	 * the database is not found then it is created.
	 * 
	 * @param database then name of the database to connect to or create
	 * @return
	 */
	@Override
	public Connection getConnection(String database) {
		String url = "jdbc:sqldroid:/data/data/my.app.name/databases/" + database;
		try {
			Class.forName("org.sqldroid.SQLDroidDriver").newInstance();
			return DriverManager.getConnection(url);
		}
		catch (InstantiationException e) {
			Log.error("sql", e.getMessage());
		}
		catch (IllegalAccessException e) {
			Log.error("sql", e.getMessage());
		}
		catch (ClassNotFoundException e) {
			Log.error("sql", e.getMessage());
		}
		catch (SQLException e) {
			Log.error("sql", e.getMessage());
		}
		return null;
	}

}