/**
 * 
 */
package com.stargem.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.stargem.Preferences;

/**
 * PlayerInputProcessor.java
 *
 * @author 	24233
 * @date	12 Oct 2013
 * @version	1.0
 */
public class PlayerInputProcessor {

	private final KinematicCharacter player;
	private final ThirdPersonCamera camera;
	
	private final Input input = Gdx.input;
	
	// for controlling the player movement
	private boolean moveForward;
	private boolean moveBackward;
	private boolean moveLeft;
	private boolean moveRight;
	private boolean rotateClockwise;
	private boolean rotateCounterClockwise;
	private boolean isJumping;
	
	/**
	 * @param player
	 * @param camera
	 */
	public PlayerInputProcessor(KinematicCharacter player, ThirdPersonCamera camera) {
		super();
		this.player = player;
		this.camera = camera;
		this.input.setCursorCatched(true);
	}

	/**
	 * Update the player's kinematic physics object and the third person camera based
	 * on the current input from the player. 
	 */
	public void update() {
		
		// pitch the camera up or down
		this.camera.pitch(input.getDeltaY() * Preferences.MOUSE_SENSITIVITY);
		
		// make the player jump
		if(this.isJumping) {
			this.player.jump();
			this.isJumping = false;
		}
		
		// get keyboard input
		moveForward 	= input.isKeyPressed(Preferences.KEY_FORWARD);
		moveBackward 	= input.isKeyPressed(Preferences.KEY_BACKWARD);
		moveLeft		= input.isKeyPressed(Preferences.KEY_LEFT);
		moveRight		= input.isKeyPressed(Preferences.KEY_RIGHT);
		isJumping		= input.isKeyPressed(Preferences.KEY_JUMP);
		
		// rotate the player
		this.player.rotate(input.getDeltaX() * Preferences.MOUSE_SENSITIVITY);
		
		// move the player
		this.player.move(this.moveForward, this.moveBackward, this.moveLeft, this.moveRight);
		
		// reset the cursor location to the middle of the screen
		input.setCursorPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
	}
		
}