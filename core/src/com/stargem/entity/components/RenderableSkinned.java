/**
 * 
 */
package com.stargem.entity.components;

import com.stargem.graphics.RepresentationManager;

/**
 * 
 * Trigger.java
 *
 * @author 	Chris B
 * @date	17 Nov 2013
 * @version	1.0
 */
public class RenderableSkinned extends AbstractComponent {

	public int modelIndex;
	public String modelPath;
	public String currentAnimationName;
	
	@Override
	public void free() {
		RepresentationManager.getInstance().removeModelInstance(modelIndex);
	}
}