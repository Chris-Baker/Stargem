package com.stargem.entity.systems;

import com.stargem.entity.Entity;

public interface SubSystem {

	public void process(float delta, Entity entity);
	public void process(float delta);
	
}