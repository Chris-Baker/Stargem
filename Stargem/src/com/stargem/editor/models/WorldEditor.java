/**
 * 
 */
package com.stargem.editor.models;

import com.badlogic.gdx.utils.Array;
import com.stargem.editor.WorldEditorListener;

/**
 * WorldEditor.java
 *
 * @author 	Chris B
 * @date	16 Feb 2014
 * @version	1.0
 */
public class WorldEditor {

	// an array of world event listeners
	private final Array<WorldEditorListener> listeners;
	
	/**
	 * The world editor facilitates the editing of a game world.
	 */
	public WorldEditor() {
		this.listeners = new Array<WorldEditorListener>();
	}

	/**
	 * Add a world event listener
	 * 
	 * @param listener
	 */
	public void addListener(WorldEditorListener listener) {
		if(!this.listeners.contains(listener, false)) {
			this.listeners.add(listener);
		}
	}
	
	/**
	 * Remove a world event listener
	 * 
	 * @param listener
	 */
	public void removeListener(WorldEditorListener listener) {
		this.listeners.removeValue(listener, false);
	}

	/**
	 * Clear all the world event listeners
	 */
	public void clearListeners() {
		this.listeners.clear();
	}

	/**
	 * 
	 */
	public void setToolSelect() {
		
		// update listeners
		for(WorldEditorListener l : this.listeners) {
			l.setToolSelect();
		}		
	}

	/**
	 * 
	 */
	public void setToolMove() {
		
		// update listeners
		for(WorldEditorListener l : this.listeners) {
			l.setToolMove();
		}
	}

	/**
	 * 
	 */
	public void setToolRaiseTerrain() {
		
		// update listeners
		for(WorldEditorListener l : this.listeners) {
			l.setToolRaiseTerrain();
		}
	}

	/**
	 * 
	 */
	public void setToolLowerTerrain() {
		
		// update listeners
		for(WorldEditorListener l : this.listeners) {
			l.setToolLowerTerrain();
		}
	}

	/**
	 * 
	 */
	public void setToolSmoothTerrain() {
		
		// update listeners
		for(WorldEditorListener l : this.listeners) {
			l.setToolSmoothTerrain();
		}
	}

	/**
	 * 
	 */
	public void setToolFlattenTerrain() {
		
		// update listeners
		for(WorldEditorListener l : this.listeners) {
			l.setToolFlattenTerrain();
		}
	}

	/**
	 * 
	 */
	public void setToolNoiseTerrain() {
		
		// update listeners
		for(WorldEditorListener l : this.listeners) {
			l.setToolNoiseTerrain();
		}
	}

	/**
	 * 
	 */
	public void setToolPaintTexture() {
		
		// update listeners
		for(WorldEditorListener l : this.listeners) {
			l.setToolPaintTexture();
		}
	}

	/**
	 * 
	 */
	public void setToolPlaceEntity() {
		
		// update listeners
		for(WorldEditorListener l : this.listeners) {
			l.setToolPlaceEntity();
		}
	}

}