package com.stargem.entity.components;

/**
 * 
 * Expires.java
 *
 * @author 	Chris B
 * @date	17 Nov 2013
 * @version	1.0
 */
public class Expires extends AbstractComponent {

	public float lifespan;
	
	public Expires() {}
	
	/**
	 * 
	 * @param delta
	 */
	public void step(float delta) {
		this.lifespan -= delta;
		if(this.lifespan < 0) {
			this.lifespan = 0;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isExpired() {
		return this.lifespan == 0;
	}
	
	/**
	 * 
	 * @param lifespan
	 */
	public void setLifespan(float lifespan) {
		this.lifespan = lifespan;
	}
	
}