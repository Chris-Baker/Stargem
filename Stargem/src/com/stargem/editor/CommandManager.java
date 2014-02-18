/**
 * 
 */
package com.stargem.editor;

import com.badlogic.gdx.utils.Array;
import com.stargem.editor.commands.EditorCommand;

/**
 * CommandManager.java
 *
 * @author 	Chris B
 * @date	16 Feb 2014
 * @version	1.0
 */
public class CommandManager {

	private final Array<CommandStackSizeListener> listeners;
	private final Array<EditorCommand> undoStack;
	private final Array<EditorCommand> redoStack;
	
	/**
	 * Manages two command stacks an undo and a redo stack.
	 * Commands that are executed using the execute method
	 * are added to the undo stack. When a command is undone,
	 * its undo method is called and it is added to the redo
	 * stack. When the redo method is called, the top of the
	 * redo stack is popped and the command is executed and
	 * pushed back onto the undo stack. 
	 * 
	 */
	public CommandManager() {
		this.listeners = new Array<CommandStackSizeListener>();
		this.undoStack = new Array<EditorCommand>();
		this.redoStack = new Array<EditorCommand>();
	}

	/**
	 * Undo the last executed command
	 */
	public void undo() {
		EditorCommand command = undoStack.pop();
		command.undo();
		redoStack.add(command);
		
		for(CommandStackSizeListener l : this.listeners) {
			l.stackSizeChanged(undoStack.size, redoStack.size);
		}
	}
	
	/**
	 * Redo the last undone command
	 */
	public void redo() {
		EditorCommand command = redoStack.pop();
		command.execute();
		undoStack.add(command);
		
		for(CommandStackSizeListener l : this.listeners) {
			l.stackSizeChanged(undoStack.size, redoStack.size);
		}
	}
	
	/**
	 * Execute the given command
	 * 
	 * @param command
	 */
	public void execute(EditorCommand command) {
		command.execute();
		undoStack.add(command);
		redoStack.clear();
		
		for(CommandStackSizeListener l : this.listeners) {
			l.stackSizeChanged(undoStack.size, redoStack.size);
		}
	}
	
	/**
	 * Add a stack size listener
	 * 
	 * @param listener
	 */
	public void addListener(CommandStackSizeListener listener) {
		if(!this.listeners.contains(listener, false)) {
			this.listeners.add(listener);
		}
	}
	
	/**
	 * Remove a stack size listener
	 * 
	 * @param listener
	 */
	public void removeListener(CommandStackSizeListener listener) {
		this.listeners.removeValue(listener, false);
	}

	/**
	 * Clear all the world event listeners
	 */
	public void clearListeners() {
		this.listeners.clear();
	}
	
}