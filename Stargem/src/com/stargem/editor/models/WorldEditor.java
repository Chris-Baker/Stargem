/**
 * 
 */
package com.stargem.editor.models;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.stargem.editor.CommandManager;
import com.stargem.editor.WorldEditorListener;
import com.stargem.editor.commands.EditorCommand;
import com.stargem.editor.tools.Tool;

/**
 * WorldEditor.java
 *
 * @author 	Chris B
 * @date	16 Feb 2014
 * @version	1.0
 */
public class WorldEditor {

	// Tool names
	public static final String SELECT_TOOL 				= "Select";
	public static final String MOVE_TOOL 				= "Move";
	public static final String RAISE_TERRAIN_TOOL 		= "Raise";
	public static final String LOWER_TERRAIN_TOOL 		= "Lower";
	public static final String SMOOTH_TERRAIN_TOOL 		= "Smooth";
	public static final String FLATTEN_TERRAIN_TOOL 	= "Flatten";
	public static final String NOISE_TERRAIN_TOOL 		= "Noise";
	public static final String PAINT_TEXTURE_TOOL 		= "Paint";
	public static final String PLACE_ENTITY_TOOL 		= "Place";
	public static final String NEW_ENTITY_TEMPLATE_TOOL = "New";
	
	// an array of world event listeners
	private final Array<WorldEditorListener> listeners;
	
	private final ObjectMap<String, Tool> tools;
	private Tool currentTool;
	
	private CommandManager commandManager;
	
	/**
	 * The world editor facilitates the editing of a game world.
	 */
	public WorldEditor() {
		this.listeners = new Array<WorldEditorListener>();
		this.tools = new ObjectMap<String, Tool>();
		this.commandManager = new CommandManager();
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
	 * Set the tool denoted by the given tool name as the current active tool.
	 */
	public void setTool(String toolName) {
		
		if(!this.tools.containsKey(toolName)) 
			throw new RuntimeException("The tool " + toolName + " does not exist and cannot be selected.");
		
		this.currentTool = this.tools.get(toolName);
		
		// update listeners
		for(WorldEditorListener l : this.listeners) {
			l.onSetTool(toolName);
		}		
	}

	/**
	 * Undo the last executed command and update all listeners
	 */
	public void undo() {
		this.commandManager.undo();
		
		int undoStackSize = this.commandManager.getUndoStackSize();
		int redoStackSize = this.commandManager.getRedoStackSize();
		
		for(WorldEditorListener l : this.listeners) {
			l.onCommandStackSizeChanged(undoStackSize, redoStackSize);
		}
	}
	
	/**
	 * Redo the last undone command and update all listeners
	 */
	public void redo() {
		this.commandManager.redo();
		
		int undoStackSize = this.commandManager.getUndoStackSize();
		int redoStackSize = this.commandManager.getRedoStackSize();
		
		for(WorldEditorListener l : this.listeners) {
			l.onCommandStackSizeChanged(undoStackSize, redoStackSize);
		}
	}
	
	/**
	 * Execute the given command and update all listeners
	 * 
	 * @param command
	 */
	public void execute(EditorCommand command) {
		this.commandManager.execute(command);
		
		int undoStackSize = this.commandManager.getUndoStackSize();
		int redoStackSize = this.commandManager.getRedoStackSize();
		
		for(WorldEditorListener l : this.listeners) {
			l.onCommandStackSizeChanged(undoStackSize, redoStackSize);
		}
	}
	
}