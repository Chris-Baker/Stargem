package com.stargem.entity.systems;


public abstract class AbstractTimedSystem extends AbstractSystem {

	private final float frequency;
	private float timer;
	
	public AbstractTimedSystem(float frequency) {
		super();
		this.frequency = frequency;
		this.timer = frequency;
	}

	@Override
	public void process(float deltaTime) {
		this.timer -= deltaTime;
		if(timer <= 0) {
			this.timer = frequency;
			super.process(deltaTime);
		}		
	}
	
}