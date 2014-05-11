/**
 * 
 */
package com.stargem.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.stargem.GameManager;
import com.stargem.Preferences;
import com.stargem.entity.Entity;
import com.stargem.entity.EntityManager;
import com.stargem.entity.components.Controller;
import com.stargem.entity.components.Weapon;
import com.stargem.scripting.ScriptManager;
import com.stargem.views.HUDView;
import com.stargem.weapons.WeaponManager;

/**
 * KeyboardMouseController.java
 *
 * @author 	Chris B
 * @date	21 Mar 2014
 * @version	1.0
 */
public class KeyboardMouseController extends AbstractControllerStrategy implements InputProcessor {
	
	private final Vector3 rayFrom = new Vector3();
	private final Vector3 rayTo = new Vector3();
	
	private final boolean shootingMode = true;
	private final boolean freelookMode = false;
	private boolean isShooting = false;
	private final HUDView hud;
	
	public float idleTime = 0;
	
	/**
	 * @param entity 
	 * @param component
	 */
	public KeyboardMouseController(Entity entity, Controller component) {
		super(entity, component);
		GameManager.getInstance().addInputProcessor(this);
		System.out.println(component.behaviour);
		ScriptManager.getInstance().execute("behaviour", component.behaviour, entity);
		this.hud = GameManager.getInstance().getHUD();
		this.hud.setEntity(entity);
		Gdx.input.setCursorCatched(true);
	}

	@Override
	public void update(float delta) {
		Weapon weapon = EntityManager.getInstance().getComponent(entity, Weapon.class);
		if(weapon != null) { 
			if(this.isShooting && weapon.isReady) {									
				Ray ray = GameManager.getInstance().getPickRay(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
				rayFrom.set(ray.origin);
				rayTo.set(ray.direction).scl(300f).add(rayFrom);				
				WeaponManager.getInstance().shoot(entity, weapon, rayFrom, rayTo);				
			}
			else {
				weapon.isShooting = false;
			}
		}
		
		idleTime += delta;
		
		if(idleTime > 30) {
			
		}
		
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
		
		idleTime = 0;
		
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
		else if(keycode == Keys.ESCAPE) {
			Gdx.app.exit();
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
		
//		if(button == Buttons.RIGHT) {
//			this.shootingMode = true;
//			
//			// show the aiming cursor in the HUD view
//			hud.setShootingMode(true);
//			
//		}
		
		//hud.setShootingMode(true);
		//this.shootingMode = true;
		
//		if(button == Buttons.LEFT && !this.shootingMode) {
//			this.freelookMode = true;
//		}
		
		if(button == Buttons.LEFT && this.shootingMode) {
			this.isShooting = true;
		}
		
		idleTime = 0;
		
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#touchUp(int, int, int, int)
	 */
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {		
		
//		if(button == Buttons.RIGHT && this.shootingMode) {
//			this.shootingMode = false;
//			
//			// show the aiming cursor in the HUD view
//			hud.setShootingMode(false);
//			
//		}
//		
//		if(button == Buttons.LEFT && this.freelookMode) {
//			this.freelookMode = false;
//		}
		
		if(button == Buttons.LEFT && this.shootingMode) {
			this.isShooting = false;
		}
		
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