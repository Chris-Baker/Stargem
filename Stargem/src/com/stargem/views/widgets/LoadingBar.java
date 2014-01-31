/**
 * 
 */
package com.stargem.views.widgets;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

/**
 * A loading bar consists of two images; a background and a foreground.
 * the foreground image starts out not displayed and fills up to cover
 * the background image when updated with a percentageComplete. 
 *
 * @author 	Chris B
 * @date	30 Jan 2014
 * @version	1.0
 */
public class LoadingBar extends WidgetGroup {

	private final Image background;
	private final Image foreground;
	
	// the amount loaded
	private float percentageComplete;
	
	/**
	 * 
	 */
	public LoadingBar(Image background, Image foreground) {
		
		this.background = background;
		this.foreground = foreground;
		
		this.setWidth(background.getWidth());
		
		this.addActor(background);
		this.addActor(foreground);
				
		background.setX(-this.getWidth()/2);
		background.setY(-this.getHeight()/2);
		
		foreground.setX(-this.getWidth()/2);
		foreground.setY(-this.getHeight()/2);
				
	}

	public void update(float newPercentage) {
		
		if(newPercentage > 100 || newPercentage < 0) {
			throw new Error("New percentage must be between 0 and 100. Failed to update loading bar.");
		}
		
		// interpolate from the current percentage to the new percentage set by the update
		this.percentageComplete = Interpolation.linear.apply(this.percentageComplete, newPercentage, 0.1f);
		
		if(this.percentageComplete > 100) {
			this.percentageComplete = 100;
		}		
		
	}
	
}