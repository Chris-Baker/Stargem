/**
 * 
 */
package com.stargem.entity.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.stargem.Config;
import com.stargem.Preferences;
import com.stargem.entity.Entity;
import com.stargem.entity.components.KeyboardMouseController;
import com.stargem.entity.components.Physics;
import com.stargem.entity.components.RunSpeed;
import com.stargem.entity.components.ThirdPersonCamera;
import com.stargem.physics.KinematicCharacter;
import com.stargem.physics.PhysicsManager;
import com.stargem.utils.Log;

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
public class KeyboardMouseSystem extends AbstractSystem {

	private final Input input = Gdx.input;
	
	// for controlling the player movement
	private boolean moveForward;
	private boolean moveBackward;
	private boolean moveLeft;
	private boolean moveRight;
	private boolean isJumping;
	
	public KeyboardMouseSystem() {
		super();		
	}

	@Override
	public void process(float delta) {
		super.entities = em.getAllEntitiesPossessingComponent(KeyboardMouseController.class);
		super.process(delta);
	}

	@Override
	public void process(float delta, Entity entity) {
		
		if(super.em.getComponent(entity, KeyboardMouseController.class).hasFocus) {
		
			ThirdPersonCamera camera 	= entity.getComponent(ThirdPersonCamera.class);
			RunSpeed runSpeed 			= entity.getComponent(RunSpeed.class);
			Physics p 					= entity.getComponent(Physics.class);
			
			// we need all three of these components too otherwise this controller won't work
			if(p == null || camera == null || runSpeed == null) {
				Log.info(Config.ENTITY_ERR, "Entity is missing a Component dependency for keyboard and mouse controller");
				return;
			}
			
			// if this is not a character return
			if(p.type != PhysicsManager.CHARACTER) {
				return;
			}
			
			KinematicCharacter player = (KinematicCharacter) PhysicsManager.getInstance().getRigidBody(p.bodyIndex);
			
			// pitch the camera up or down
			camera.deltaPitch = input.getDeltaY() * Preferences.MOUSE_SENSITIVITY;
			
			// make the player jump
			if(this.isJumping) {
				player.jump();
				this.isJumping = false;
			}
			
			// get keyboard input
			moveForward 	= input.isKeyPressed(Preferences.KEY_FORWARD);
			moveBackward 	= input.isKeyPressed(Preferences.KEY_BACKWARD);
			moveLeft		= input.isKeyPressed(Preferences.KEY_LEFT);
			moveRight		= input.isKeyPressed(Preferences.KEY_RIGHT);
			isJumping		= input.isKeyPressed(Preferences.KEY_JUMP);
			
//			if(moveForward) {Log.info(Config.INFO, "forward ");}
//			if(moveBackward) {Log.info(Config.INFO, "back ");}
//			if(moveLeft) {Log.info(Config.INFO, "left ");}
//			if(moveRight) {Log.info(Config.INFO, "right ");}
//			if(isJumping) {Log.info(Config.INFO, "jump ");}
			
			// rotate the player
			player.rotate(input.getDeltaX() * Preferences.MOUSE_SENSITIVITY);
			
			// move the player
			player.move(runSpeed.speed, this.moveForward, this.moveBackward, this.moveLeft, this.moveRight);
			
			// reset the cursor location to the middle of the screen
			input.setCursorPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		}
		
	}
		
}