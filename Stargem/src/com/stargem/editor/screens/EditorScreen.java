/**
 * 
 */
package com.stargem.editor.screens;

import com.stargem.Config;
import com.stargem.Stargem;
import com.stargem.editor.controllers.WorldEditorController;
import com.stargem.editor.models.WorldEditor;
import com.stargem.editor.views.WorldEditorView;
import com.stargem.screens.AbstractScreen;

/**
 * PlayScreen.java
 *
 * @author 	Chris B
 * @date	30 Jan 2014
 * @version	1.0
 */
public class EditorScreen extends AbstractScreen {

	// for fixed time step simulation
	private float accum = 0;
	private int iterations = 0;
	
	// the main simulation and the renderer
	//private final Simulation simulation;
	
	private final WorldEditor editor;
	private final WorldEditorController controller;
	private final WorldEditorView view;
	
	/**
	 * @param game
	 */
	public EditorScreen(Stargem game) {
		super(game);
		
		// Create the main simulation and its view
		//this.simulation = new Simulation();
		
		this.editor = new WorldEditor();
		this.controller = new WorldEditorController(editor);
		this.view = new WorldEditorView(controller);
		
		this.editor.addListener(view);
	}
	
	/* (non-Javadoc)
	 * @see com.stargem.views.View#render()
	 */
	@Override
	public void render(float delta) {
		
		// Update
		accum += delta;
		iterations = 0;
		while (accum > Config.FIXED_TIME_STEP && iterations < Config.MAX_UPDATE_ITERATIONS) {
			// tweenManager.update(tweenTimeStep);
			//simulation.update(Config.FIXED_TIME_STEP);
			accum -= Config.FIXED_TIME_STEP;
			iterations++;
		}

		// Render
		view.render(delta);
		
	}
	
	@Override
	public void show() {
		//Gdx.input.setCursorCatched(true);
	}
	
	@Override
	public void hide() {
		//Gdx.input.setCursorCatched(false);
	}
	
}