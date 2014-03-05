/**
 * 
 */
package com.stargem.editor.models;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.stargem.Config;
import com.stargem.editor.WorldEditorListener;
import com.stargem.editor.commands.EditorCommand;
import com.stargem.editor.tools.Action;
import com.stargem.editor.tools.NullAction;
import com.stargem.utils.Log;

/**
 * WorldEditor.java
 *
 * @author 	Chris B
 * @date	16 Feb 2014
 * @version	1.0
 */
public class WorldEditor {

	// File menu
	public static final String NEW 						= "New";
	public static final String CLOSE 					= "Close";
	public static final String LOAD 					= "Load";
	public static final String SAVE 					= "Save";
	public static final String SAVE_AS 					= "Save As";
	public static final String EXIT 					= "Exit";
	
	// Edit menu
	public static final String UNDO 					= "Undo";
	public static final String REDO 					= "Redo";
	
	// Import menu
	public static final String TEXTURE_ATLAS_IMPORT		= "Atlas";
	public static final String TEXTURE_IMPORT 			= "Texture";
	public static final String SOUND_IMPORT 			= "Sound";
	public static final String MUSIC_IMPORT 			= "Music";
	public static final String MODEL_IMPORT 			= "Model";
	
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
	
	private final ObjectMap<String, Action> actions;
	private Action currentAction;
	
	private final CommandManager commandManager;
	
	/**
	 * The world editor facilitates the editing of a game world.
	 */
	public WorldEditor() {
		this.listeners = new Array<WorldEditorListener>();
		this.actions = new ObjectMap<String, Action>();
		this.commandManager = new CommandManager();
		
		// add the actions to the map so they can be used
		
		// file menu
		this.actions.put(NEW, NullAction.getInstance());
		this.actions.put(CLOSE, NullAction.getInstance());
		this.actions.put(LOAD, NullAction.getInstance());
		this.actions.put(SAVE, NullAction.getInstance());
		this.actions.put(SAVE_AS, NullAction.getInstance());
		this.actions.put(EXIT, NullAction.getInstance());
		
		// edit menu
		this.actions.put(UNDO, NullAction.getInstance());
		this.actions.put(REDO, NullAction.getInstance());
		
		// import menu
		this.actions.put(TEXTURE_ATLAS_IMPORT, NullAction.getInstance());
		this.actions.put(TEXTURE_IMPORT, NullAction.getInstance());
		this.actions.put(SOUND_IMPORT, NullAction.getInstance());
		this.actions.put(MUSIC_IMPORT, NullAction.getInstance());
		this.actions.put(MODEL_IMPORT, NullAction.getInstance());
		
		// tool bar
		this.actions.put(SELECT_TOOL, NullAction.getInstance());
		this.actions.put(MOVE_TOOL, NullAction.getInstance());
		this.actions.put(RAISE_TERRAIN_TOOL, NullAction.getInstance());
		this.actions.put(LOWER_TERRAIN_TOOL, NullAction.getInstance());
		this.actions.put(SMOOTH_TERRAIN_TOOL, NullAction.getInstance());
		this.actions.put(FLATTEN_TERRAIN_TOOL, NullAction.getInstance());
		this.actions.put(NOISE_TERRAIN_TOOL, NullAction.getInstance());
		this.actions.put(PAINT_TEXTURE_TOOL, NullAction.getInstance());
		this.actions.put(PLACE_ENTITY_TOOL, NullAction.getInstance());
		this.actions.put(NEW_ENTITY_TEMPLATE_TOOL, NullAction.getInstance());
		
		
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
		
		if(!this.actions.containsKey(toolName)) {
			String message = "The tool \"" + toolName + "\" does not exist and cannot be selected.";
			Log.error(Config.EDITOR_ERR, message);
			throw new RuntimeException(message);
		}
		
		this.currentAction = this.actions.get(toolName);
		
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