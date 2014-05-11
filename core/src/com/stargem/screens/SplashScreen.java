/**
 * 
 */
package com.stargem.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.stargem.Stargem;

/**
 * 
 * SplashScreen.java
 *
 * @author 	Chris B
 * @date	30 Apr 2014
 * @version	1.0
 */
public class SplashScreen extends AbstractScreen {
	
	private Sound wavSound;
	private Texture logoTexture;
	private Image logoImage;
	private final Stage stage = new Stage();
	
	
	/**
	 * @param game
	 */
	public SplashScreen(Stargem game) {
		super(game);
		this.initialise();
	}

	private void initialise() {
		
		// sound effect
		// Weaponry_EMP_Blast_05.wav
		wavSound = Gdx.audio.newSound(Gdx.files.internal("data/screens/splash/Weaponry_EMP_Blast_05.ogg"));
		
		// retrieve the splash image's region from the atlas
		logoTexture = new Texture(Gdx.files.internal("data/screens/splash/Base2-grey.png"));			
		logoImage = new Image(new TextureRegionDrawable(new TextureRegion(logoTexture)), Scaling.none);
		logoImage.setFillParent(true);

		// this is needed for the fade-in effect to work correctly; we're just 
		// making the image completely transparent
		logoImage.getColor().a = 0f;

		// and finally we add the actor to the stage
		stage.addActor(logoImage);
	}
	
	@Override
	public void show() {

		Gdx.input.setCursorCatched(true);
		
		wavSound.play();
		
		// configure the fade-in/out effect on the splash image
		logoImage.addAction(sequence(fadeIn(1.5f), delay(1.3f), fadeOut(1.0f), new Action() {
			@Override
			public boolean act(float delta) {
				Gdx.input.setCursorCatched(false);
				game.setMainMenuScreen();
				return true;
			}
		}));
	}

	@Override
	public void render(float delta) {

		// update the actors
		stage.act(delta);

		// clear the screen with the given RGB color (black)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		// draw the actors
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		this.stage.getViewport().update(width, height, true);
	}

	@Override
	public void dispose() {
		this.wavSound.dispose();
		this.logoTexture.dispose();
		this.stage.dispose();
	}
	
}