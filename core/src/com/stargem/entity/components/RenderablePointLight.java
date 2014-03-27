/**
 * 
 */
package com.stargem.entity.components;

import com.stargem.graphics.EnvironmentManager;

/**
 * 
 * Trigger.java
 *
 * @author 	Chris B
 * @date	17 Nov 2013
 * @version	1.0
 */
public class RenderablePointLight extends AbstractComponent {

	public int lightIndex;
	public int colour;
	public float intensity;
	public float x, y, z;
	
	@Override
	public void free() {
		EnvironmentManager.getInstance().removePointLight(lightIndex);
	}
	
}