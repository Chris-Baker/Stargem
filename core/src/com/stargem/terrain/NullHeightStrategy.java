/**
 * 
 */
package com.stargem.terrain;

/**
 * NullHeightStrategy.java
 *
 * @author 	Chris B
 * @date	18 Apr 2014
 * @version	1.0
 */
public class NullHeightStrategy implements HeightStrategy {

	private static final NullHeightStrategy instance = new NullHeightStrategy();
	public static NullHeightStrategy getInstance() {
		return instance;
	}
	private NullHeightStrategy() {}
	
	/* (non-Javadoc)
	 * @see com.stargem.terrain.HeightStrategy#getHeight(float, float, float)
	 */
	@Override
	public float getHeight(float x, float y, float z) {
		return 0;
	};
	
	
	
}