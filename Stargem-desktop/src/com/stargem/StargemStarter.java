package com.stargem;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class StargemStarter {
	public static void main(String[] args) {
		
		// TODO check the settings.ini to see if the resolution has been set if so use it
		
		// else if this is the first time running get the current desktop resolution settings
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Stargem";
		cfg.useGL20 = true;
		cfg.width = 1024;
		cfg.height = 768;
		cfg.fullscreen = false;
		
		new LwjglApplication(new Stargem(), cfg);
	}
}
