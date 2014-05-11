/**
 * 
 */
package com.stargem.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.stargem.GameManager;
import com.stargem.Stargem;
import com.stargem.views.MenuView;
import com.stargem.views.View;

/**
 * 
 * SplashScreen.java
 *
 * @author 	Chris B
 * @date	20 Apr 2014
 * @version	1.0
 */
public class MenuScreen extends AbstractScreen {
	
	private enum MenuScreenState {
		LOADING_VIEW,
		READY,
		UNLOADING_VIEW
	}
	
	private MenuScreenState currentState;
	private final GameManager gameManager = GameManager.getInstance();
	private final AssetManager assetManager;
	private Screen nextScreen;
	private View view;
	
	/**
	 * @param game
	 */
	public MenuScreen(Stargem game) {
		super(game);
		this.assetManager = gameManager.getAssetManager();
	}
	
	@Override
	public void show() {
		
		this.view = new MenuView(assetManager);
		
		// reset the state
		currentState = MenuScreenState.LOADING_VIEW;				

	}
	
	@Override
	public void render(float delta) {
		
		// render the view
		this.view.render(delta);
		
		switch(this.currentState) {
		
			case LOADING_VIEW:				
				// finish loading the view
				if (assetManager.update()) {																	
					// transition to show the screen and progress bar
					this.currentState = MenuScreenState.READY;
					
					// show the view
					this.view.show();
					
					// set an input processor to listen for any key presses
					//anyKeyPressedProcessor = new AnyKeyPressedProcessor();
					//this.anyKeyPressedProcessor.addObserver(this);
					//GameManager.getInstance().addInputProcessor(anyKeyPressedProcessor);
				}				
			break;
			
			case READY:
				// this state just waits for user input
			break;
			
			case UNLOADING_VIEW:
				
				// just for good measure reset the state
				currentState = MenuScreenState.LOADING_VIEW;
								
				// dispose the view
				this.view.dispose();
				
				// set the next screen
				this.game.setScreen(nextScreen);
				
			break;
			
		default:
			currentState = MenuScreenState.LOADING_VIEW;
			break;
		}		
	}

	@Override
	public void resize(int width, int height) {
		this.view.resize(width, height);
	}
	
}