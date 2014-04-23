/**
 * 
 */
package com.stargem.controllers;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.stargem.entity.Entity;
import com.stargem.entity.components.Controller;

/**
 * ControllerManager.java
 *
 * @author 	Chris B
 * @date	21 Mar 2014
 * @version	1.0
 */
public class ControllerManager {

	public static final int KEYBOARD_MOUSE 	= 0;
	public static final int TOUCH_SCREEN 	= 1;
	public static final int NETWORK			= 2;
	public static final int AI				= 3;
	
	private final Array<ControllerStrategy> controllers;
	
	private final static ControllerManager instance = new ControllerManager();
	public static ControllerManager getInstance() {
		return instance;
	}
	private ControllerManager() {
		this.controllers = new Array<ControllerStrategy>();
	}
	
	/**
	 * Register a new controller from the supplied controller component.
	 * The component must have a valid controller strategy type, one
	 * of four integer values.
	 * 
	 * <ol start="0">
	 *   <li>KEYBOARD_MOUSE</li>
	 *   <li>TOUCH_SCREEN</li>
	 *   <li>NETWORK</li>
	 *   <li>AI</li>
	 * </ol>
	 * 
	 * @param component
	 */
	public void createControllerFromComponent(Entity entity, Controller component) {
		
		ControllerStrategy controller;
		
		switch(component.strategyType) {
			case KEYBOARD_MOUSE:
				controller = new KeyboardMouseController(entity, component);
				break;
				
			case TOUCH_SCREEN:
				controller = null;
				break;
				
			case NETWORK:
				controller = null;
				break;
				
			case AI:
				controller = new AIController(entity, component);
				break;
				
			default:
				throw new GdxRuntimeException("Unknown controller type");
		}
		
		this.controllers.add(controller);
		component.strategyIndex = this.controllers.size - 1;
	}
	
	/**
	 * Create a new controller strategy for the given component. 
	 * The new strategy type must be one of four integer values.
	 * 
	 * <ol start="0">
	 *   <li>KEYBOARD_MOUSE</li>
	 *   <li>TOUCH_SCREEN</li>
	 *   <li>NETWORK</li>
	 *   <li>AI</li>
	 * </ol>
	 * 
	 * @param component the component to set the new strategy for
	 * @param newStrategyType the new strategy type
	 */
	public void swapControllerStrategy(Entity entity, Controller component, int newStrategyType) {
		
		ControllerStrategy controller;
		
		switch(newStrategyType) {
			case KEYBOARD_MOUSE:
				controller = new KeyboardMouseController(entity, component);
				break;
				
			case TOUCH_SCREEN:
				controller = null;
				break;
				
			case NETWORK:
				controller = null;
				break;
				
			case AI:
				controller = new AIController(entity, component);
				break;
				
			default:
				throw new GdxRuntimeException("Unknown controller type");
		}
		component.strategyType = newStrategyType;
		this.controllers.set(component.strategyIndex, controller);		
	}
	
}