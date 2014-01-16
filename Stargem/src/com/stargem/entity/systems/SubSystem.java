package com.stargem.entity.systems;

import com.stargem.entity.Entity;

public interface SubSystem {

	public void process(float deltaTime, Entity entity);
	public void process(float deltaTime);
	
}