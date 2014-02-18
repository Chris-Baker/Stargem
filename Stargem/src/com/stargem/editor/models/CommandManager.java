/**
 * 
 */
package com.stargem.editor.models;

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
	}
	
	/**
	 * Redo the last undone command
	 */
	public void redo() {
		EditorCommand command = redoStack.pop();
		command.execute();
		undoStack.add(command);
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
	}

	public int getUndoStackSize() {
		return this.undoStack.size;
	}
	
	public int getRedoStackSize() {
		return this.redoStack.size;
	}
}