/**
 * 
 */
package com.stargem.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;

/**
 * An adapter class for the libgdx input class because Lua
 * has trouble importing the sub class directly
 * 
 * InputAdapter.java
 *
 * @author 	Chris B
 * @date	20 Apr 2014
 * @version	1.0
 */
public class InputAdapter {

	public static boolean isKeyPressed(int key) {
		return Gdx.input.isKeyPressed(key);
	}
	
	public static boolean isRightButtonPressed() {
		return Gdx.input.isButtonPressed(Buttons.RIGHT);
	}
	
	public static boolean isLeftButtonPressed() {
		return Gdx.input.isButtonPressed(Buttons.LEFT);
	}
	
	public static void setCursorPosition(int x, int y) {
		Gdx.input.setCursorPosition(x, y);
	}
	
	public static void setCursorPositionCenter() {
		Gdx.input.setCursorPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
	}
	
	public static void setCursorCatched(boolean catched) {
		Gdx.input.setCursorCatched(catched);
	}
	
	public static float getDeltaX() {
		return Gdx.input.getDeltaX();
	}
	
	public static float getDeltaY() {
		return Gdx.input.getDeltaY();
	}
}