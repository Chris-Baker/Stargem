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
		
		// we need to get this reference in method scope because if we try to get a reference
		// when we instantiate the object, it will be a null.
		ProfilePersistence profilePersistence = PersistenceManager.getInstance().getProfilePersistence();
		
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
		
		// we need to get this reference in method scope because if we try to get a reference
		// when we instantiate the object, it will be a null.
		ProfilePersistence profilePersistence = PersistenceManager.getInstance().getProfilePersistence();
				
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
		
		// we need to get this reference in method scope because if we try to get a reference
		// when we instantiate the object, it will be a null.
		ProfilePersistence profilePersistence = PersistenceManager.getInstance().getProfilePersistence();
				
		profile.campaignName = campaignName;
		profilePersistence.updateCampaignName(profile);
	}

	/**
	 * @param levelName the levelName to set
	 */
	public void setLevelName(String levelName) {
		
		// we need to get this reference in method scope because if we try to get a reference
		// when we instantiate the object, it will be a null.
		ProfilePersistence profilePersistence = PersistenceManager.getInstance().getProfilePersistence();
		
		profile.levelName = levelName;
		profilePersistence.updateWorldName(profile);
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