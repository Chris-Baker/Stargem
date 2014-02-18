/**
 * 
 */
package com.stargem.editor;

/**
 * CommandStackSizeListener.java
 *
 * @author 	Chris B
 * @date	16 Feb 2014
 * @version	1.0
 */
public interface CommandStackSizeListener {

	public void stackSizeChanged(int undoSize, int redoSize);
	
}