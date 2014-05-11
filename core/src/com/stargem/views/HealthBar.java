/**
 * 
 */
package com.stargem.views;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * HealthBar.java
 *
 * @author 	Chris B
 * @date	9 May 2014
 * @version	1.0
 */
public class HealthBar extends Table {

	// drawables
	Image background;
	Image emptyBar;
	Image fullBar;
	Image icon;
	
	float barWidth;
	
	/**
	 * @param background
	 * @param emptyBar
	 * @param fullBar
	 * @param icon
	 */
	public HealthBar(Image background, Image emptyBar, Image fullBar, Image icon) {
		super();
		this.background = background;
		this.emptyBar = emptyBar;
		this.fullBar = fullBar;
		this.icon = icon;
		this.barWidth = fullBar.getWidth();
		
		// background image	and table dimensions	
		this.setBackground(background.getDrawable(), false);
		this.setWidth(background.getWidth());
		this.setHeight(background.getHeight());
		
		// the icon is on the left hand side
		this.add(icon);
		
		// the health bars are a group so they can overlap
		Group bars = new Group();
		bars.addActor(fullBar);
		bars.addActor(emptyBar);
		emptyBar.setX(-emptyBar.getWidth() / 2 - 1);
		emptyBar.setY(-emptyBar.getHeight() / 2);
		emptyBar.setWidth(0);
		fullBar.setX(-fullBar.getWidth() / 2 - 1);
		fullBar.setY(-fullBar.getHeight() / 2);
		this.add(bars).expand();
				
	}
	
	/**
	 * Adjust the health bar to match the given health
	 */
	public void setHealth(float currentHealth, float maxHealth) {
		float percentHealth = currentHealth / maxHealth;
		emptyBar.setWidth(this.barWidth - (this.barWidth * percentHealth));
		float originX = -barWidth / 2 - 1;
		emptyBar.setX(originX + (this.barWidth * percentHealth));
	}
	
}