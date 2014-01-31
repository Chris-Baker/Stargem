/**
 * 
 */
package com.stargem.screens;

import com.stargem.Config;
import com.stargem.Stargem;
import com.stargem.models.Simulation;
import com.stargem.views.SimulationView;

/**
 * PlayScreen.java
 *
 * @author 	Chris B
 * @date	30 Jan 2014
 * @version	1.0
 */
public class PlayScreen extends AbstractScreen {

	// for fixed time step simulation
	private float accum = 0;
	private int iterations = 0;
	
	// the main simulation and the renderer
	private final Simulation simulation;
	private final SimulationView simulationView;
	
	/**
	 * @param game
	 */
	public PlayScreen(Stargem game, Simulation simulation, SimulationView simulationView) {
		super(game);
		this.simulation = simulation;
		this.simulationView = simulationView;
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
			simulation.update(Config.FIXED_TIME_STEP);
			accum -= Config.FIXED_TIME_STEP;
			iterations++;
		}

		// Render
		simulationView.render(delta);
		
	}
	
}