/**
 * 
 */
package com.stargem.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.stargem.Stargem;

/**
 * BlankScreen.java
 *
 * @author 	Chris B
 * @date	10 May 2014
 * @version	1.0
 */
public class BlankScreen extends AbstractScreen {

	/**
	 * @param game
	 */
	public BlankScreen(Stargem game) {
		super(game);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
	}
	
}