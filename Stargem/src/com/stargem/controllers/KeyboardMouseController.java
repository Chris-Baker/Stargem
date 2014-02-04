/**
 * 
 */
package com.stargem.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.stargem.Preferences;
import com.stargem.entity.Entity;
import com.stargem.entity.components.Physics;
import com.stargem.entity.components.RunSpeed;
import com.stargem.entity.components.ThirdPersonCamera;
import com.stargem.physics.KinematicCharacter;
import com.stargem.physics.PhysicsManager;

/**
 * The keyboard and mouse controller acts on a single entity passing player input
 * the the physics and camera components of the player.
 * 
 * KeyboardMouseController.java
 *
 * @author 	24233
 * @date	12 Oct 2013
 * @version	1.0
 */
public class KeyboardMouseController {

	private final KinematicCharacter player;
	private final ThirdPersonCamera camera;
	private final RunSpeed runSpeed;
	
	private final Input input = Gdx.input;
	
	// for controlling the player movement
	private boolean moveForward;
	private boolean moveBackward;
	private boolean moveLeft;
	private boolean moveRight;
	private boolean isJumping;
	
	public KeyboardMouseController(Entity entity) {
		super();
		this.input.setCursorCatched(true);
		
		Physics p = entity.getComponent(Physics.class);
		this.camera = entity.getComponent(ThirdPersonCamera.class);
		this.runSpeed = entity.getComponent(RunSpeed.class);
		
		if(p == null || camera == null || runSpeed == null) {
			throw new Error("The entity supplied to KeyboardMouseController must have Physics, RunSpeed and ThirdPersonCamera components");
		}
		
		if(p.type == PhysicsManager.CHARACTER) {
			this.player = (KinematicCharacter) PhysicsManager.getInstance().getRigidBody(p.bodyIndex);
		}
		else {
			throw new Error("The entity supplied to KeyboardMouseController must have a Physics component with type Character");
		}
		
	}

	/**
	 * Update the player's kinematic physics object and the third person camera based
	 * on the current input from the player. 
	 */
	public void update() {
		
		// pitch the camera up or down
		this.camera.deltaPitch = input.getDeltaY() * Preferences.MOUSE_SENSITIVITY;
		
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
		this.player.move(this.runSpeed.speed, this.moveForward, this.moveBackward, this.moveLeft, this.moveRight);
		
		// reset the cursor location to the middle of the screen
		input.setCursorPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
	}
		
}