package com.stargem;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.stargem.editor.screens.EditorScreen;
import com.stargem.persistence.PersistenceManager;
import com.stargem.screens.LoadingScreen;
import com.stargem.screens.PlayScreen;
import com.stargem.utils.Log;

/**
 * Stargem.java
 * 
 * @author 24233
 * @date 14 Nov 2013
 * @version 1.0
 */
public class Stargem implements ApplicationListener {

	// The Game Manager class is in charge of the overall game state.
	private final GameManager gameManager = GameManager.getInstance();
	
	// current screen
	private Screen currentScreen;
	
	// game screens
	private Screen loadingScreen;
	private Screen playScreen;
	
	// editor screen
	private Screen editorScreen;
	
	// resume currentScreen
	// splash currentScreen
	// main menu currentScreen
	// create profileManager currentScreen
	// select profileManager currentScreen
	// profileManager menu currentScreen
	// options currentScreen

	// briefing currentScreen
	// play currentScreen
	// shop currentScreen	

	@Override
	public void create() {
		
		// Set the logging level to everything
		Gdx.app.setLogLevel(Application.LOG_INFO);
		Log.setLogLevel(Log.DEBUG);
		
		// set the debug renderer as on for the physics simulation
		//PhysicsManager.getInstance().setDebug(true);
		
		// set the game instance in the Game Manager
		this.gameManager.setGame(this);
		
		// Create the screens
		this.editorScreen = new EditorScreen(this);
		this.loadingScreen = new LoadingScreen(this);
		this.playScreen = new PlayScreen(this);
		
		// resume currentScreen
		// splash currentScreen
		// main menu currentScreen
		// create profileManager currentScreen
		// select profileManager currentScreen
		// profileManager menu currentScreen
		// options currentScreen

		// loading currentScreen
		// briefing currentScreen
		// play currentScreen
		// shop currentScreen		

		// sound manager

		// start the editor
		//this.setScreen(this.editorScreen);
		
		// create profile or load it
		try {
			PersistenceManager persistenceManager = PersistenceManager.getInstance();
			String profileName = "Chris Baker";
			String databaseName = persistenceManager.getNewDatabaseName(profileName);
			if (persistenceManager.exists(databaseName)) {
				Log.info("Profile", "Loading existing profile " + databaseName);				
				this.gameManager.loadProfile(databaseName);
			}
			else {
				Log.info("Profile", "Creating new profile");
				this.gameManager.newProfile(profileName);
			}						
		}
		catch (Exception e) {
			Log.error(Config.IO_ERR, e.getMessage() + " whilst trying to load profile");
		}
		
		// start the game
		this.gameManager.loadGame();
		//this.gameManager.createWorld();
		
	}
	
	/**
	 * Sets the current currentScreen. {@link Screen#hide()} is called on any old currentScreen, and {@link Screen#show()} is called on the new
	 * currentScreen, if any.
	 * 
	 * @param currentScreen may be {@code null}
	 */
	public void setScreen(Screen screen) {
		if (this.currentScreen != null) {
			this.currentScreen.hide();
		}
		this.currentScreen = screen;
		if (this.currentScreen != null) {
			this.currentScreen.show();
			this.currentScreen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
	}

	/** @return the currently active {@link Screen}. */
	public Screen getScreen() {
		return currentScreen;
	}

	@Override
	public void resize(int width, int height) {
		if (currentScreen != null) {
			currentScreen.resize(width, height);
		}
	}

	@Override
	public void render() {
		if (currentScreen != null) {
			currentScreen.render(Gdx.graphics.getDeltaTime());
		}
	}

	@Override
	public void pause() {
		// TODO throw a pause event to the current currentScreen
		if (currentScreen != null) {
			this.currentScreen.pause();
		}
		
		// this could pause gameplay in the one player mode
		// or simply display the in game menu in coop.
		// it is the same event as pressing escape or alt-tab in game
	}

	@Override
	public void resume() {

		// TODO reload OpenGL context sensitive assetManager such as textures
		// set the current currentScreen to the previous currentScreen
		// display the resume currentScreen which reloads assetManager with lost contexts
		
		// TODO throw a resume event to the current currentScreen
		if (currentScreen != null) {
			this.currentScreen.resume();
		}
	}

	/**
	 * Sets the play screen as the current screen
	 */
	public void setPlayScreen() {
		this.setScreen(this.playScreen);
	}

	/**
	 * Sets the loading screen as the current screen
	 */
	public void setLoadingScreen() {
		this.setScreen(this.loadingScreen);
	}
	
	@Override
	public void dispose() {
		
		// dispose all screens
		this.loadingScreen.dispose();
		this.playScreen.dispose();
		
		// dispose all managers
		//this.gameManager.dispose();
	}
}