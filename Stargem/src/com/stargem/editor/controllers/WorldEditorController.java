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
 * WorldEditorController.java
 *
 * @author 	Chris B
 * @date	16 Feb 2014
 * @version	1.0
 */
public class WorldEditorController extends InputListener {
	
	private final WorldEditor editor;
	
	public WorldEditorController(WorldEditor editor) {
		this.editor = editor;
	}

	@Override
	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {		
		return true;
	}

	@Override
	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		
		Actor actor = event.getTarget();
		String actorName = actor.getName();
		
		if(actorName.equals(WorldEditorView.SELECT_BTN)) {
			this.editor.setToolSelect();
		}
		else if(actorName.equals(WorldEditorView.MOVE_BTN)) {
			this.editor.setToolMove();
		}
		else if(actorName.equals(WorldEditorView.RAISE_TERRAIN_BTN)) {
			this.editor.setToolRaiseTerrain();
		}
		else if(actorName.equals(WorldEditorView.LOWER_TERRAIN_BTN)) {
			this.editor.setToolLowerTerrain();
		}
		else if(actorName.equals(WorldEditorView.SMOOTH_TERRAIN_BTN)) {
			this.editor.setToolSmoothTerrain();
		}
		else if(actorName.equals(WorldEditorView.FLATTEN_TERRAIN_BTN)) {
			this.editor.setToolFlattenTerrain();
		}
		else if(actorName.equals(WorldEditorView.NOISE_TERRAIN_BTN)) {
			this.editor.setToolNoiseTerrain();
		}
		else if(actorName.equals(WorldEditorView.PAINT_TEXTURE_BTN)) {
			this.editor.setToolPaintTexture();
		}
		else if(actorName.equals(WorldEditorView.PLACE_ENTITY_BTN)) {
			this.editor.setToolPlaceEntity();
		}
		else if(actorName.equals(WorldEditorView.NEW_ENTITY_TEMPLATE_BTN)) {
			this.editor.setToolSelect();
		}
	}	
	
}