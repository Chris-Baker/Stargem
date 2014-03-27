/**
 * 
 */
package com.stargem.editor.tools;

import com.stargem.Config;
import com.stargem.utils.Log;

/**
 * NullAction.java
 *
 * @author 	Chris B
 * @date	18 Feb 2014
 * @version	1.0
 */
public class NullAction implements Action {

	private static final NullAction instance = new NullAction();
	public static NullAction getInstance() {
		return instance;
	}	
	private NullAction() {}
	
	@Override
	public void execute() {
		// do nothing
		Log.info(Config.INFO, "Null action taken");
	}

}