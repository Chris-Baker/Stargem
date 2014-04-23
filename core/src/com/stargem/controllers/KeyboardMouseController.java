/**
 * 
 */
package com.stargem.controllers;

import com.badlogic.gdx.InputProcessor;
import com.stargem.GameManager;
import com.stargem.Preferences;
import com.stargem.entity.Entity;
import com.stargem.entity.components.Controller;

/**
 * KeyboardMouseController.java
 *
 * @author 	Chris B
 * @date	21 Mar 2014
 * @version	1.0
 */
public class KeyboardMouseController extends AbstractControllerStrategy implements ControllerStrategy, InputProcessor {
	
	/**
	 * @param entity 
	 * @param component
	 */
	public KeyboardMouseController(Entity entity, Controller component) {
		super(entity, component);
		GameManager.getInstance().addInputProcessor(this);
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#keyDown(int)
	 */
	@Override
	public boolean keyDown(int keycode) {
				
		if(keycode == Preferences.KEY_FORWARD) {
			component.moveForward = true;
		}
		else if(keycode == Preferences.KEY_BACKWARD) {
			component.moveBackward = true;
		}
		else if(keycode == Preferences.KEY_LEFT) {
			component.moveLeft = true;
		}
		else if(keycode == Preferences.KEY_RIGHT) {
			component.moveRight = true;
		}		
		else if(keycode == Preferences.KEY_JUMP) {
			component.isJumping = true;
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#keyUp(int)
	 */
	@Override
	public boolean keyUp(int keycode) {
				
		if(keycode == Preferences.KEY_FORWARD) {
			component.moveForward = false;
		}
		else if(keycode == Preferences.KEY_BACKWARD) {
			component.moveBackward = false;
		}
		else if(keycode == Preferences.KEY_LEFT) {
			component.moveLeft = false;
		}
		else if(keycode == Preferences.KEY_RIGHT) {
			component.moveRight = false;
		}		
		else if(keycode == Preferences.KEY_JUMP) {
			component.isJumping = false;
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#keyTyped(char)
	 */
	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#touchDown(int, int, int, int)
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#touchUp(int, int, int, int)
	 */
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#touchDragged(int, int, int)
	 */
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#mouseMoved(int, int)
	 */
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#scrolled(int)
	 */
	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}