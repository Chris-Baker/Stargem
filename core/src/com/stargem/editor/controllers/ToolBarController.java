/**
 * 
 */
package com.stargem.editor.controllers;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.stargem.editor.models.WorldEditor;
import com.stargem.editor.views.WorldEditorView;

/**
 * ToolBarController.java
 *
 * @author 	Chris B
 * @date	16 Feb 2014
 * @version	1.0
 */
public class ToolBarController extends InputListener {
	
	private final WorldEditor editor;
	
	public ToolBarController(WorldEditor editor) {
		this.editor = editor;
	}

	@Override
	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {		
		return true;
	}

	@Override
	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		
		Actor actor = event.getTarget();
		String name = actor.getName();
		
		// set the
		this.editor.setTool(name);
	}	
	
}