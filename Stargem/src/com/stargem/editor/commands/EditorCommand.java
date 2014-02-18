/**
 * 
 */
package com.stargem.editor.commands;

/**
 * EditorCommand.java
 *
 * @author 	Chris B
 * @date	16 Feb 2014
 * @version	1.0
 */
public interface EditorCommand {

	public void execute();
	public void undo();
	
}