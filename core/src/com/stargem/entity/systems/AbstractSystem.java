package com.stargem.entity.systems;

import java.util.Iterator;

import com.stargem.entity.Entity;
import com.stargem.entity.EntityManager;

public abstract class AbstractSystem implements SubSystem {

	protected Iterator<Entity> entities;
	protected final EntityManager em = EntityManager.getInstance();
	
	public AbstractSystem() {
		this.entities = em.nullEntityIterator;
	}
	
	@Override
	public void process(float delta) {
		while(entities.hasNext()) {
			process(delta, entities.next());
		}
	}
	
	@Override
	public abstract void process(float delta, Entity entity);
	
}