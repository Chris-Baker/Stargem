/**
 * 
 */
package com.stargem.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.stargem.entity.systems.ThirdPersonCameraSystem;
import com.stargem.graphics.RepresentationManager;

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
	private PerspectiveCamera camera;
	
	// used to rotate the sky box
	private final Vector3 camPosition;
	
	private final RepresentationManager representationManager;
	
	public SimulationView() {
		super();
		this.modelBatch = new ModelBatch();
		this.stage = new Stage();
		this.camPosition = new Vector3();
		this.representationManager = RepresentationManager.getInstance();
		this.initCamera();
		
		this.cameraSystem = new ThirdPersonCameraSystem(this.camera);
	}

	/**
	 * Initialise the perspective camera
	 */
	private void initCamera() {
		
		final float width = Gdx.graphics.getWidth();
		final float height = Gdx.graphics.getHeight();
		if (width > height) {
			this.camera = new PerspectiveCamera(67f, 3f * width / height, 3f);
		}
		else {
			this.camera = new PerspectiveCamera(67f, 3f, 3f * height / width);
		}
		this.camera.position.set(0, 92, 0);
		this.camera.lookAt(0, 92, 90);
		this.camera.near = 0.1f;
		this.camera.far = 500f;
		this.camera.update();
		
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
		
		// stop the sky box masking everything else out
	    Gdx.gl.glDepthMask(false);
		
		// render sky
	    // move the camera to the origin so that it is inside the skybox
	    // render, then move the camera back to its previous position.
		camPosition.set(this.camera.position);
		camera.position.set(0, 0, 0);
		camera.update();
		modelBatch.begin(camera);
		modelBatch.render(representationManager.getSkyInstances());
		modelBatch.end();
		camera.position.set(camPosition);
		camera.update();
		Gdx.gl.glDepthMask(true);
		
		// render terrain and entity instances
		// TODO add environment lights, fog etc.
		modelBatch.begin(camera);
		modelBatch.render(representationManager.getTerrainInstances());
		modelBatch.render(representationManager.getEntityInstances());
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
	public void resize(float width, float height) {
	}
}