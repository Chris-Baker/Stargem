/**
 * 
 */
package com.stargem.entity;

import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Pool;
import com.stargem.entity.components.Component;

/**
 * ComponentManager.java
 *
 * @author 	Chris B
 * @date	24 Mar 2013
 * @version	1.0
 */
public class ComponentManager {

	private final ArrayMap<Class<? extends Component>, Pool<? extends Component>> componentPools;
	
	private static ComponentManager instance = new ComponentManager();
	
	public static ComponentManager getInstance() {
		return instance;
	}
	
	private ComponentManager() {
		componentPools = new ArrayMap<Class<? extends Component>, Pool<? extends Component>>();
	}
	
	/**
	 * 
	 * @param componentType the shape of component to register
	 */
	private <T extends Component> void registerComponentType(final Class<T> componentType) {
		if(!componentPools.containsKey(componentType)) {
			
			componentPools.put(componentType, new Pool<T>() {

				@Override
				protected T newObject() {
					try {
						return componentType.newInstance();
					}
					catch (InstantiationException e) {						
						e.printStackTrace();
						System.exit(0);
						return null;
					}
					catch(IllegalAccessException e) {						
						e.printStackTrace();
						System.exit(0);
						return null;
					}
				}				
			});
		}
	}
	
	/**
	 * Get a new component instance of the class given. If there is no
	 * pool found then one is created first.
	 * 
	 * @param componentType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends Component> T newComponentOfType(final Class<T> componentType) {
		if(!componentPools.containsKey(componentType)) {
			this.registerComponentType(componentType);			
		}
		return (T) componentPools.get(componentType).obtain();
	}
	
	/**
	 * Free a component, placing it back in the appropriate pool
	 * @param c the component to free
	 */
	@SuppressWarnings("unchecked")
	public <T extends Component> void free(T c) {
		if(componentPools.containsKey(c.getClass())) {
			// free up the resources in the component
			c.free();
			
			// return the component to it's pool
			((Pool<T>)componentPools.get(c.getClass())).free(c);
		}
	}
	
}