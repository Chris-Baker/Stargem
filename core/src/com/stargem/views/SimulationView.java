/**
 * 
 */
package com.stargem.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.stargem.GameManager;
import com.stargem.entity.systems.ThirdPersonCameraSystem;
import com.stargem.graphics.EnvironmentManager;
import com.stargem.graphics.PhysicsDebugDraw;
import com.stargem.graphics.RepresentationManager;
import com.stargem.physics.PhysicsManager;

/**
 * SimulationView.java
 *
 * @author 	Chris B
 * @date	20 Nov 2013
 * @version	1.0
 */
public class SimulationView implements View {

	private final ThirdPersonCameraSystem cameraSystem;
	
	private final ModelBatch modelBatch;
	private final Stage stage;
	private final Camera camera;
	private final Viewport viewport;
	
	// used to rotate the sky box
	private final Vector3 camPosition;
	
	private final RepresentationManager representationManager;
	private final PhysicsDebugDraw physicsDebugDraw;
	
	public SimulationView() {
		super();
		this.modelBatch = new ModelBatch();
		this.stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		this.camPosition = new Vector3();
		this.representationManager = RepresentationManager.getInstance();
		this.physicsDebugDraw = PhysicsDebugDraw.getInstance();
		this.viewport = GameManager.getInstance().getViewport();
		this.camera = viewport.getCamera();
		this.cameraSystem = new ThirdPersonCameraSystem(this.camera);
	}

	/* (non-Javadoc)
	 * @see com.stargem.views.View#render()
	 */
	@Override
	public void render(float delta) {
		
		// update the camera
		cameraSystem.process(delta);
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_TEXTURE_2D);
		Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
			
	    // render sky with new camera position
 		camPosition.set(this.camera.position);
 		camera.position.set(0, 0, 0);
 		camera.update();
 		modelBatch.begin(camera);
 		modelBatch.render(representationManager.getSkyInstances());
 		modelBatch.end();
 		camera.position.set(camPosition);
 		camera.update();
	    
		// render terrain and entity instances
		// TODO add environment lights, fog etc.
		modelBatch.begin(camera);
		modelBatch.render(representationManager.getTerrainInstances(), EnvironmentManager.getInstance().getEnvironment());
		modelBatch.render(representationManager.getEntityInstances(), EnvironmentManager.getInstance().getEnvironment());
		if(PhysicsManager.getInstance().debug()) {
			modelBatch.render(physicsDebugDraw.getEntityInstances());
		}		
		modelBatch.end();
				
		// render the hud
		stage.draw();
	}

	/* (non-Javadoc)
	 * @see com.stargem.views.View#show()
	 */
	@Override
	public void show() {
	}
	
	/* (non-Javadoc)
	 * @see com.stargem.views.View#dispose()
	 */
	@Override
	public void dispose() {
		this.stage.dispose();
		this.modelBatch.dispose();
	}

	/* (non-Javadoc)
	 * @see com.stargem.views.View#resize(float, float)
	 */
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		stage.getViewport().update(width, height, true);
	}
}