/**
 * 
 */
package com.stargem.profile;

/**
 * PlayerProfile.java
 *
 * @author 	Chris B
 * @date	14 Nov 2013
 * @version	1.0
 */
public class PlayerProfile {

	protected String name;
	protected String databaseName;
	protected String campaignName;
	protected String levelName;
	
	protected PlayerProfile() {}

	/**
	 * @return the campaignName
	 */
	public String getCampaignName() {
		return campaignName;
	}
	
	/**
	 * @return the levelName
	 */
	public String getWorldName() {
		return levelName;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the databaseName
	 */
	public String getDatabaseName() {
		return databaseName;
	}
	
}