package com.stargem.entity;

import java.util.Iterator;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.PooledLinkedList;
import com.stargem.Config;
import com.stargem.entity.components.Component;
import com.stargem.utils.Log;

/**
 * Standard design: c.f. http://entity-systems.wikidot.com/rdbms-with-code-in-systems
 * 
 * Modified in java to use Generics: instead of having a "ComponentType" enum, we use the class shape
 * of each subclass instead. This is safer.
 */

public class EntityManager {
	
	private static final Array<Entity> entityPool = new Array<Entity>();
	
	private final Array<Entity> allEntities;
	private final ObjectMap<Class<?>, ObjectMap<Entity, ? extends Component>> componentStores;
	private final ObjectMap<Entity, PooledLinkedList<? extends Component>> entityComponents;
	private final IntMap<Entity> entityId;
	
	// null iterator is returned if a map of components does not exist. This saves having to 
	// deal with returning nulls, which is messy
	public final Iterator<Entity> nullIterator = new Iterator<Entity>() {

		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public Entity next() {
			return null;
		}

		@Override
		public void remove() {
		}
		
	};

	// registered observers
	private final Array<EntityRecycleObserver> recycleObservers = new Array<EntityRecycleObserver>();
	
	private static final EntityManager instance = new EntityManager();
	
	public static EntityManager getInstance() {
		return instance;
	}
	
	private EntityManager() {
		allEntities = new Array<Entity>();
		componentStores = new ObjectMap<Class<?>, ObjectMap<Entity, ? extends Component>>();
		entityComponents = new ObjectMap<Entity, PooledLinkedList<? extends Component>>();
		entityId = new IntMap<Entity>();
	}

	/**
	 * get the component of the given entity
	 */
	@SuppressWarnings("unchecked")
	synchronized public <T extends Component> T getComponent(Entity entity, Class<T> componentType) {
		
//		if(componentType.equals(Health.class) ) {
//			Log.debug(Config.ENTITY_ERR, "getting health component");
//		}
		
		ObjectMap<Entity, ? extends Component> store = componentStores.get( componentType );

		if (store == null)
		 {
			return null;
		//throw new IllegalArgumentException( "GET FAIL: there are no entities with a Component of class: "+componentType );
		}

		T result = (T) store.get(entity);
		if (result == null)
		 {			
			Log.debug(Config.ENTITY_ERR, "null component returned");
			return null;
			//throw new IllegalArgumentException( "GET FAIL: "+entity+" does not possess Component of class\n   missing: "+componentType );
		}
		
		return result;
	}

	/**
	 * return a list of all components of the given shape
	 */
	@SuppressWarnings("unchecked")
	synchronized public <T extends Component> Iterator<T> getAllComponentsOfType(Class<T> componentType) {
		ObjectMap<Entity, ? extends Component> store = componentStores.get(componentType);

		// TODO this allocates memory how can this be fixed?
		if (store == null) {
			return new Iterator<T>() {

				@Override
				public boolean hasNext() {
					return false;
				}

				@Override
				public T next() {
					return null;
				}

				@Override
				public void remove() {
				}
				
			};
		}

		return (Iterator<T>) store.values();
	}

	/**
	 * return a set of all entities which are mapped to a component
	 */
	synchronized public <T extends Component> Iterator<Entity> getAllEntitiesPossessingComponent(Class<T> componentType) {
		
		ObjectMap<Entity, ? extends Component> store = componentStores.get(componentType);

		if (store == null) {
			return this.nullIterator;
		}

		return store.keys();
	}

	/**
	 * Get a list of components attached to the given entity.
	 * 
	 * @param entity
	 * @return
	 */
	synchronized public PooledLinkedList<? extends Component> getComponents(Entity entity) {
		PooledLinkedList<? extends Component> components = this.entityComponents.get(entity);
		
		if (components == null) {
			components = new PooledLinkedList<Component>(Integer.MAX_VALUE);
			this.entityComponents.put(entity, components);
		}
		
		return components;
	}
	
	/**
	 * map a component to an entity
	 */
	@SuppressWarnings("unchecked")
	synchronized public <T extends Component> void addComponent(Entity entity, T component) {
		ObjectMap<Entity, ? extends Component> store = componentStores.get(component.getClass());

		if (store == null) {
			store = new ObjectMap<Entity, T>();
			componentStores.put(component.getClass(), store);
		}

		((ObjectMap<Entity, T>) store).put(entity, component);
		
		// this data structure is used for recycling entities
		PooledLinkedList<? extends Component> components = this.entityComponents.get(entity);
		
		if (components == null) {
			components = new PooledLinkedList<Component>(Integer.MAX_VALUE);
			this.entityComponents.put(entity, components);
		}
		
		((PooledLinkedList<T>) components).add(component);
	}

	/**
	 * Un-map a component from an entity. this is a relatively slow process because the list of
	 * components is walked to find an instance of the component type.
	 * 
	 * @param e
	 * @param type
	 */
	synchronized public void removeComponent(Entity e, Class<? extends Component> type) {
		
		// get the component so we can recycle it in a moment if it is null we are done
		Component c = this.getComponent(e, type);
		if(c == null)
		{
			return;
		}
		
		// get the component store so we can unmap the component
		ObjectMap<Entity, ? extends Component> store = componentStores.get(type);		
		store.remove(e);
		
		// iterate over the list of component for this entity and remove any with the given class type
		PooledLinkedList<? extends Component> components = this.entityComponents.get(e);
		Component component = null;
		for(components.iter(); (component = components.next()) != null;) {
			if(component.getClass().equals(type)) {
				components.remove();
			}
		}		
			
		// recycle the component
		ComponentManager.getInstance().free(c);
	}
	
	/**
	 * create a new entity adding it to the list
	 * 
	 * @throws Exception
	 */
	synchronized public Entity createEntity() {
		Entity entity;
		if(entityPool.size == 0) {
			entity = new Entity();
		}
		else {
			entity = entityPool.pop();
			entity.id = 0;
		}
		
		allEntities.add(entity);
		return entity;
	}

	/**
	 * remove an entity freeing up its id.
	 */
	private void killEntity(Entity entity) {
		synchronized (this) // make this thread safe
		{
			if(entityId.containsValue(entity, false)) {
				entityId.remove(entityId.findKey(entity, false, 0));
			}
			allEntities.removeValue(entity, false);
			entityPool.add(entity);
			entity.id = 0;
		}
	}
	
	/**
	 * Free the entity and all its components putting them all back into their
	 * respective pools.
	 * 
	 * @param entity the entity to recycle.
	 */
	public void recycle(Entity entity) {
		int id = entity.id;
		synchronized (this) {
			PooledLinkedList<? extends Component> components = this.entityComponents.get(entity);
			if(components != null) {
				
				ComponentManager componentManager = ComponentManager.getInstance();
				
				// free the entity id
				this.killEntity(entity);
				
				// free each component				
				Component c = null;
				for(components.iter(); (c = components.next()) != null;) {
					componentManager.free(c);
					
					// remove the component from the component store
					ObjectMap<Entity, ? extends Component> store = componentStores.get(c.getClass());
					store.remove(entity);				
				}
				
				// remove the entity and component from the component store
				this.entityComponents.remove(entity);				
			}			
		}
		// update observers
		for(EntityRecycleObserver o : this.recycleObservers) {
			o.recycle(id);
		}
	}

	/**
	 * Free the entity and all its components putting them all back into their
	 * respective pools.
	 * 
	 * @param id the id of the entity to recycle.
	 */
	public void recycle(int id) {
		if(entityId.containsKey(id)) {
			recycle(entityId.get(id));
		}
	}
	
	/**
	 * Set the id of the given entity to the given id value and place it
	 * in the entityId map at that key.
	 * 
	 * @param id
	 * @param e
	 */
	public void setEntityId(Entity e, int id) {
		entityId.put(id, e);
		e.id = id;
	}
	
	/**
	 * Get all the entities as an Iterable.
	 * 
	 * @return all the entities as an Iterable.
	 */
	synchronized public Iterable<Entity> getAllEntities() {
		return this.allEntities;
	}
	
	/**
	 * Register a new entity recycle observer 
	 * @param o
	 */
	public void registerEntityRecycleObserver(EntityRecycleObserver o) {
		this.recycleObservers.add(o);
	}
	
	/**
	 * Unregister the entity recycle observer 
	 * @param o
	 */
	public void unregisterEntityRecycleObserver(EntityRecycleObserver o) {
		this.recycleObservers.removeValue(o, false);
	}
}