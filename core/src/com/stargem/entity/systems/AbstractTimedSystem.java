package com.stargem.entity.systems;


public abstract class AbstractTimedSystem extends AbstractSystem {

	protected final float frequency;
	protected float timer;
	
	/**
	 * A system which is processed on a timer.
	 * 
	 * @param frequency in nanoseconds
	 */
	public AbstractTimedSystem(float frequency) {
		super();
		this.frequency = frequency;
		this.timer = frequency;
	}

	@Override
	public void process(float delta) {
		this.timer -= delta;
		if(timer <= 0) {
			this.timer = frequency;
			super.process(delta);
		}		
	}
	
}