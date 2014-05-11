package com.stargem.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.stargem.Stargem;
import com.stargem.persistence.ActionResolver;
import com.stargem.persistence.AndroidActionResolver;
import com.stargem.utils.PlatformResolver;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		
		ActionResolver actionResolver = new AndroidActionResolver(this);
		PlatformResolver platformResolver = new AndroidPlatformResolver(actionResolver);
		
		initialize(new Stargem(platformResolver), config);
	}
}
