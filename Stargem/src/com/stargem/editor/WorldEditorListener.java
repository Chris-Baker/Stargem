/**
 * 
 */
package com.stargem.editor;

/**
 * WorldEditorListener.java
 *
 * @author 	Chris B
 * @date	16 Feb 2014
 * @version	1.0
 */
public interface WorldEditorListener {

	public void onSetTool(String toolName);
	public void onCommandStackSizeChanged(int undoSize, int redoSize);
	
}