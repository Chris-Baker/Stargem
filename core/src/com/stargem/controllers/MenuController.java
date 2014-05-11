/**
 * 
 */
package com.stargem.controllers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.stargem.Config;
import com.stargem.GameManager;
import com.stargem.persistence.PersistenceManager;
import com.stargem.utils.Log;
import com.stargem.views.MenuView;


/**
 * MenuController.java
 *
 * @author 	Chris B
 * @date	1 May 2014
 * @version	1.0
 */
public class MenuController extends InputListener {

	MenuView view;
	String profilePattern = "^[a-zA-Z0-9 ]*$";
	
	/**
	 * @param view
	 */
	public MenuController(MenuView view) {
		super();
		this.view = view;
	}

	@Override
	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		
		Actor actor = event.getListenerActor();
		if(actor == null) {
			return false;
		}
		
		String actorName = actor.getName();
		if(actorName == null) {
			return false;
		}
		
		if(actorName.equals(Config.UI_NEW_GAME)) {
			view.showNewProfileDialogue();
		}
		else if(actorName.equals(Config.UI_LOAD_GAME)) {
			view.transitionOutMainMenu();
			view.transitionInChooseProfile();
		}
		else if(actorName.equals(Config.UI_EXIT_GAME)) {
			view.showExitDialogue();
		}
		else if(actorName.equals(Config.UI_PLAY)) {
			view.transitionOutProfileMenuAndPlay();
		}
		else if(actorName.equals(Config.UI_OPTIONS)) {
			view.transitionOutProfileMenu();
			view.transitionInOptionsMenu();
		}
		else if(actorName.equals(Config.UI_EXIT_PROFILE)) {
			view.transitionOutProfileMenu();
			view.transitionInMainMenu();		
		}
		else if(actorName.equals(Config.UI_NEW_PROFILE_OK)) {
			
			String profileName = view.playerNameInput.getText();
			
			// validate the profile name
			if(profileName == null) {
				view.newProfileError.setText("Enter a name!");
				return false;
			}
			
			if(!profileName.matches(this.profilePattern)) {
				view.newProfileError.setText("Name must be alphanumeric!");
				return false;
			}
			
			// name is fine check if it is in use already
			String databaseName = PersistenceManager.getInstance().getNewDatabaseName(profileName);
			if (PersistenceManager.getInstance().exists(databaseName)) {
				view.newProfileError.setText("Name is in use already!");
				return false;
			}
			
			// yay we have a winner, create the profile
			try {
				GameManager.getInstance().newProfile(profileName);
				view.hideNewProfileDialogue();
				view.transitionOutMainMenu();
				view.transitionInProfileMenu();
			}
			catch (Exception e) {				
				view.newProfileError.setText("Something went wrong!");
			}
			
		}
		else if(actorName.equals(Config.UI_NEW_PROFILE_CANCEL)) {
			view.hideNewProfileDialogue();
		}
		else if(actorName.equals(Config.UI_PROFILE_LOAD)) {
			
			// get the selected profile name
			String profileName = view.profileList.getSelected();
			
			if(profileName == null) {
				return false;
			}
			
			// get the database name for the profile
			String databaseName = PersistenceManager.getInstance().getNewDatabaseName(profileName);
			
			// load the profile
			try {
				GameManager.getInstance().loadProfile(databaseName);
				view.transitionOutChooseProfile();
				view.transitionInProfileMenu();
			}
			catch (Exception e) {
				Log.error(Config.IO_ERR, e.getMessage() + " whilst trying to load profile");
			}		
		}
		else if(actorName.equals(Config.UI_PROFILE_CANCEL)) {
			view.transitionOutChooseProfile();
			view.transitionInMainMenu();
		}
		else {
			// do nothing
		}
		
		return false;
	}

	@Override
	public boolean keyUp(InputEvent event, int keycode) {
		
		if(keycode == Keys.ESCAPE) {
			view.showExitDialogue();
		}
		
		return false;
	}
	
}