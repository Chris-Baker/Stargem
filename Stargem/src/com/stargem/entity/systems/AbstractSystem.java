package com.stargem.entity.systems;

import java.util.Iterator;

import com.stargem.entity.Entity;
import com.stargem.entity.EntityManager;

public abstract class AbstractSystem implements SubSystem {

	protected Iterator<Entity> entities;
	protected final EntityManager em = EntityManager.getInstance();
	
	public AbstractSystem() {
		this.entities = em.nullIterator;
	}
	
	@Override
	public void process(float deltaTime) {
		while(entities.hasNext()) {
			process(deltaTime, entities.next());
		}
	}
	
	@Override
	public abstract void process(float deltaTime, Entity entity);
	
}