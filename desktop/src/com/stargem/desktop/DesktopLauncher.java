package com.stargem.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.stargem.Stargem;
import com.stargem.persistence.ActionResolver;
import com.stargem.persistence.DesktopActionResolver;
import com.stargem.utils.PlatformResolver;

public class DesktopLauncher {
	public static void main(String[] arg) {

		// Windows XP has a high-accuracy timer, you just have to tell it to use it - 
		// by default, it will not. Due to whatever JVM vagaries, having a sleeping 
		// thread forces it to keep using the high-res system timer. What's even more 
		// peculiar is the timer setting seems to be cross-application, so you may get 
		// a high-accuracy timer just because another app you're also running at the time 
		// asked for it.
		Thread timerAccuracyThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(Long.MAX_VALUE);
					}
					catch (Exception e) {
					}
				}
			}
		});
		timerAccuracyThread.setDaemon(true);
		timerAccuracyThread.start();

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Stargem";
		config.width = 1024;
		config.height = 768;
		config.fullscreen = true;

		ActionResolver actionResolver = new DesktopActionResolver();
		PlatformResolver platformResolver = new DesktopPlatformResolver(actionResolver);

		new LwjglApplication(new Stargem(platformResolver), config);
	}
}
