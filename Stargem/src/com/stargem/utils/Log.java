/**
 * 
 */
package com.stargem.utils;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

/**
 * A static wrapper around the {@link Application} log so that it is easier to access from
 * Lua scripting.
 * 
 * @author Chris B
 * @date 6 Nov 2013
 * @version 1.0
 */
public class Log {

	public static final int NONE = 0;
	public static final int ERROR = 1;
	public static final int INFO = 2;
	public static final int DEBUG = 3;

	private static int level = NONE;

	synchronized public static void debug(String tag, String message) {
		if (level >= DEBUG) {
			Gdx.app.debug(tag, message);
		}
	}

	synchronized public static void debug(String tag, String message, Throwable exception) {
		if (level >= DEBUG) {
			Gdx.app.debug(tag, message, exception);
		}
	}

	synchronized public static void info(String tag, String message) {
		if (level >= INFO) {
			Gdx.app.log(tag, message);
		}
	}

	synchronized public static void info(String tag, String message, Throwable exception) {
		if (level >= INFO) {
			Gdx.app.log(tag, message, exception);
		}
	}

	synchronized public static void error(String tag, String message) {
		if (level >= ERROR) {
			Gdx.app.error(tag, message);
		}
	}

	synchronized public static void error(String tag, String message, Throwable exception) {
		if (level >= ERROR) {
			Gdx.app.error(tag, message, exception);
		}
	}

	public static void setLogLevel(int logLevel) {
		Gdx.app.setLogLevel(logLevel);
		level = logLevel;
	}

	public static int getLogLevel() {
		return level;
	}
}