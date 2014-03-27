/**
 * 
 */
package com.stargem.views;

/**
 * View.java
 *
 * @author 	Chris B
 * @date	20 Nov 2013
 * @version	1.0
 */
public interface View {

	public void render(float delta);
	public void show();
	public void resize(int width, int height);
	public void dispose();
	
}