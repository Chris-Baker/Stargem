/**
 * 
 */
package com.stargem.entity.components;

/**
 * AbstractComponent.java
 *
 * @author 	Chris B
 * @date	24 Mar 2013
 * @version	1.0
 */
public abstract class AbstractComponent implements Component {

	/**
	 * this can be optionally overridden to free up any members
	 * which can be placed back into pools. components which do
	 * not contain any objects do not need to implement anything here
	 * but this method is called by the component manager when an
	 * entity is recycled.
	 */
	@Override
	public void free() {}
}