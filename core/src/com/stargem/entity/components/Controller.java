package com.stargem.entity.components;

import com.stargem.behaviour.BehaviourManager;
import com.stargem.controllers.ControllerManager;

public class Controller extends AbstractComponent {

	public int strategyIndex;
	public int strategyType;
	public String controller;
	public String behaviour;
	public boolean moveForward;
	public boolean moveBackward;
	public boolean moveLeft;
	public boolean moveRight;
	public boolean isJumping;
	
	@Override
	public void free() {
		ControllerManager.getInstance().removeController(strategyIndex);
		BehaviourManager.getInstance().removeBehaviour(strategyIndex);
	}
	
}