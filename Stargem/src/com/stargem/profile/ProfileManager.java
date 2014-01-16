/**
 * 
 */
package com.stargem.profile;

import com.stargem.sql.PersistenceManager;
import com.stargem.sql.ProfilePersistence;



/**
 * ProfileManager.java
 *
 * @author 	Chris B
 * @date	14 Nov 2013
 * @version	1.0
 */
public class ProfileManager {
	
	// true if a profile has been loaded, false otherwise
	private boolean isLoaded = false;
	
	// keep a reference to the profile profilePersistence layer
	private final ProfilePersistence profilePersistence = PersistenceManager.getInstance().getProfilePersistence();
	
	/**
	 * Stores the actual profile information; name, database name, level, and campaign.
	 */
	private final PlayerProfile profile = new PlayerProfile();
	
	public static ProfileManager getInstance() {
		return instance;
	}
	private static final ProfileManager instance = new ProfileManager();	
	private ProfileManager() {}
	
	// create a new profile and store it.
	
	/**
	 * A new profile is created and stored in the currently connected database
	 * 
	 * @param name
	 * @param databaseName
	 */
	public void newProfile(String name, String databaseName) {
		profile.name = name;
		profile.databaseName = databaseName;
		profile.campaignName = null;
		profile.levelName = null;
						
		profilePersistence.insertProfile(profile);
		
		this.isLoaded = true;
	}
	
	/**
	 * Load the profile information from the database given
	 * 
	 * @param databaseName
	 * @return
	 */
	public void loadProfile(String databaseName) {
		profile.name = profilePersistence.getProfileName(databaseName);		
		profile.databaseName = databaseName;
		profile.campaignName = profilePersistence.getCampaignName(databaseName);
		profile.levelName = profilePersistence.getLevelName(databaseName);
				
		this.isLoaded = true;
	}
	
	/**
	 * @param campaignName the campaignName to set
	 */
	public void setCampaignName(String campaignName) {
		profile.campaignName = campaignName;
		profilePersistence.updateCampaignName(profile);
	}

	/**
	 * @param levelName the levelName to set
	 */
	public void setLevelName(String levelName) {
		profile.levelName = levelName;
		profilePersistence.updateLevelName(profile);
	}
		
	/**
	 * return the currently active profile
	 * 
	 * @return
	 */
	public PlayerProfile getActiveProfile() {
		return this.profile;
	}

	/**
	 * @return
	 */
	public boolean isLoaded() {
		return isLoaded;
	}

	/**
	 * @return
	 */
	public boolean isLevelSet() {
		return profile.campaignName != null && profile.levelName != null;
	}
	
}