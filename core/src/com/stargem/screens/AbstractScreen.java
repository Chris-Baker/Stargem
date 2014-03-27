/**
 * 
 */
package com.stargem.screens;

import com.badlogic.gdx.Screen;
import com.stargem.Stargem;

/**
 * AbstractScreen.java
 *
 * @author 	Chris B
 * @date	18 Nov 2013
 * @version	1.0
 */
public abstract class AbstractScreen implements Screen {

	Stargem game;
	
	public AbstractScreen(Stargem game) {
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
	
}