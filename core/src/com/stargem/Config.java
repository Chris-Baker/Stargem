package com.stargem;


public class Config {
	
	/* @formatter:off */
	
	// default campaign and level
	public static final String DEFAULT_CAMPAIGN 	= "Stargem";
	public static final String DEFAULT_WORLD 		= "World_01";
	
	// database
	//public static final String PROFILE_DATABASE_PATH = "../Stargem-android/assets/data/profiles/";
	public static final String PROFILE_DATABASE_PATH = "data/profiles/";
	public static final String PROFILE_PATH 		= "data/profiles/";
	public static final String DATABASE_EXTENSION 	= ".db";
	
	// Campaigns path
	public static final String CAMPAIGN_PATH 		= "data/campaigns/";
	//public static final String WORLD_DATABASE_PATH 	= "../Stargem-android/assets/data/campaigns/";
	public static final String WORLD_DATABASE_PATH 	= "data/campaigns/";
	public static final String WORLD_DATABASE_NAME 	= "entities";
	
	// scripting
	public static final String SCRIPT_PATH 			= "data/lua/";
	public static final String SCRIPT_EXTENSION 	= ".lua";
	public static final String WORLD_SCRIPT_NAME	= "Script";
	
	// Error tags for logging
	public static final String EDITOR_ERR 			= "EDITOR";
	public static final String SCRIPT_ERR 			= "SCRIPT";
	public static final String SQL_ERR 				= "SQL";
	public static final String IO_ERR 				= "IO";
	public static final String NET_ERR 				= "NET";
	public static final String PHYSICS_ERR 			= "PHYSICS";
	public static final String REFLECTION_ERR 		= "REFLECTION";
	public static final String SYSTEM_ERR 			= "SYSTEM";
	public static final String ENTITY_ERR 			= "ENTITY";
	public static final String INFO 				= "INFO";
	
	
	public static final int PIXELS_PER_METER 		= 32;
	
	// Collision ID's
	public static final int PICKUP 					= 1;
	public static final int HEALTH 					= 2;
	public static final int AI_HEARING 				= 4;
	public static final int AI_BOT 					= 8;
	public static final int PLAYER	 				= 16;
	public static final int BULLET 					= 32;
		
	// Physics stuff
	public static final float GRAVITY 				= -20f;
	
	// Database table names
	public static final String TABLE_ENTITY			= "Entity";
	public static final String TABLE_ASSETS			= "Assets";
	public static final String TABLE_WORLD			= "World";
	public static final String TABLE_PLAYERS		= "Players";
	public static final String TABLE_GATES 			= "Gates";
	
	// Physics time step
	public static final int MAX_UPDATE_ITERATIONS 	= 5;
	public static final float FIXED_TIME_STEP 		= 1 / 60f;
	public static final int TWEEN_TIME_STEP			= (int) (FIXED_TIME_STEP * 1000);
	public static final int NUM_SUBSTEPS 			= 3;
	
	// auto save frequency in seconds
	public static final float AUTO_SAVE_FREQUENCY 	= 15;
	
	// UI menu item names
	public static final String UI_NEW_GAME			= "New Game";
	public static final String UI_LOAD_GAME			= "Load Game";
	public static final String UI_EXIT_GAME			= "Exit Game";
	
	public static final String UI_PLAY				= "Play";
	public static final String UI_OPTIONS			= "Options";
	public static final String UI_EXIT_PROFILE		= "Exit Profile";
	public static final String UI_NEW_PROFILE_OK 	= "OK";
	public static final String UI_NEW_PROFILE_CANCEL= "Cancel";
	public static final String UI_PROFILE_LOAD 	 	= "Load Profile";
	public static final String UI_PROFILE_CANCEL 	= "Cancel Profile";
}