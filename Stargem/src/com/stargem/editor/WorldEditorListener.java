/**
 * 
 */
package com.stargem.editor;

/**
 * WorldEditorListener.java
 *
 * @author 	Chris B
 * @date	16 Feb 2014
 * @version	1.0
 */
public interface WorldEditorListener {

	/**
	 * 
	 */
	public void setToolSelect();
	

	/**
	 * 
	 */
	public void setToolMove();
	

	/**
	 * 
	 */
	public void setToolRaiseTerrain();
	

	/**
	 * 
	 */
	public void setToolLowerTerrain();
	

	/**
	 * 
	 */
	public void setToolSmoothTerrain();
	

	/**
	 * 
	 */
	public void setToolFlattenTerrain();
	

	/**
	 * 
	 */
	public void setToolNoiseTerrain();
	

	/**
	 * 
	 */
	public void setToolPaintTexture();
	

	/**
	 * 
	 */
	public void setToolPlaceEntity();
	
	
}