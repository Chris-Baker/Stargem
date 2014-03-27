/**
 * 
 */
package com.stargem.persistence;

import java.sql.Connection;

/**
 * ConnectionListener.java
 *
 * @author 	Chris B
 * @date	19 Nov 2013
 * @version	1.0
 */
public interface ConnectionListener {

	public void setConnection(Connection c);
	
}