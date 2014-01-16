/**
 * 
 */
package com.stargem.sql;

import java.sql.Connection;

/**
 * ActionResolver.java
 * 
 * @author 	Chris B
 * @date 	8 Nov 2013
 * @version 1.0
 */
public interface ActionResolver {

	/**
	 * Connect to the database with the name given. If
	 * the database is not found then it is created.
	 * 
	 * @param database then name of the database to connect to or create
	 * @return
	 */
	public Connection getConnection(String database);
}