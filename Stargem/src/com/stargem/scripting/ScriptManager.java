/**
 * 
 */
package com.stargem.scripting;

import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaObject;
import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

import com.badlogic.gdx.Gdx;
import com.stargem.Config;
import com.stargem.utils.Log;
import com.stargem.utils.StringHelper;

/**
 * ScriptManager.java
 *
 * @author 	Chris B
 * @date	21 Oct 2013
 * @version	1.0
 */
public class ScriptManager {
	
	private static ScriptManager instance;
	private static boolean isInitialised = false;
	
	private LuaState luaState;
	
	public static ScriptManager getInstance() {
		if(instance == null) {
			instance = new ScriptManager();
		}
		return instance;
	}

	/**
	 * Default constructor
	 */
	private ScriptManager() {
		super();
	}
		
	public void initialise(String filePath) {
				
		// throw an appropriate error if no name is found
		if (!Gdx.files.internal(filePath).exists()) {
			Log.error(Config.SCRIPT_ERR, "Lua name file not found: " + filePath);
			throw new Error("Lua name file not found: " + filePath);
		}
		
		// setup a lua state machine and load in the name file contents
		this.luaState = LuaStateFactory.newLuaState();
		this.luaState.openLibs();
		this.luaState.LdoString(Gdx.files.internal(filePath).readString());
		ScriptManager.isInitialised = true;
	}
	
	/**
	 * Ends the use of Lua environment.
	 */
	public void close() {
		if(this.luaState != null) {
			this.luaState.close();
		}
	}
	
	/**
	 * Execute a method passing it 0 or more parameters
	 * 
	 * @param method the name of the method to execute
	 * @param parameters 0 or more parameters
	 */
	public void execute(String method, Object...parameters) {		
		try {
			if (!ScriptManager.isInitialised) {
				Log.error(Config.SCRIPT_ERR, "Lua name file not initialised");
				throw new Error("Lua name file not initialised");
			}
			else {
				luaState.getLuaObject(method).call(parameters, 0);
			}
		}
		catch (LuaException e) {
			Log.error(Config.SCRIPT_ERR, e.getMessage(), e);
		}		
	}
	
	/**
	 * Execute a method belonging to an object passing it 0 or more parameters
	 * 
	 * @param object the name of the Lua object to access
	 * @param method the name of the method to execute
	 * @param parameters 0 or more parameters
	 */
	public void execute(String object, String method, Object...parameters) {		
		try {
			if (!ScriptManager.isInitialised) {
				Log.error(Config.SCRIPT_ERR, "Lua name file not initialised");
				throw new Error("Lua name file not initialised");
			}
			else {
				LuaObject obj = luaState.getLuaObject(object);
				luaState.getLuaObject(obj, method).call(parameters, 0);
			}
		}
		catch (LuaException e) {
			Log.error(Config.SCRIPT_ERR, e.getMessage(), e);
		}
	}
	
	/**
	 * Add the contents of the given file to the Lua State
	 * 
	 * @param filename the name of the Lua file to add, minus the extension
	 */
	public void require(String filename) {
		String filePath = requirePath(filename);
		
		if (!Gdx.files.internal(filePath).exists()) {
			Log.error(Config.SCRIPT_ERR, "Lua name file not found: " + filePath);
			throw new Error("Lua name file not found: " + filePath);
		}
		
		this.luaState.LdoString(Gdx.files.internal(filePath).readString());
	}
	
	/**
	 * Given the name of a file returns the path to the name file
	 * with its extension as specified in the configuration.
	 * 
	 * @param filename the name of the Lua file to add, minus the extension
	 * @return the path to the file including its extension
	 */
	private String requirePath(String filename) {
		StringBuilder luaPath = StringHelper.getBuilder();
		luaPath.append(Config.SCRIPT_PATH);
		luaPath.append(filename);
		luaPath.append(Config.SCRIPT_EXTENSION);
		return luaPath.toString();
	}
}