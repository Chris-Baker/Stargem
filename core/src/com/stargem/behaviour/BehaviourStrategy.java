/**
 * 
 */
package com.stargem.behaviour;

/**
 * BehaviourStrategy.java
 *
 * @author 	Chris B
 * @date	23 Apr 2014
 * @version	1.0
 */
public interface BehaviourStrategy {

	public void onIdle();
	public void onWakeUp();
	public void onSleep();
	public void onCombatStarted();
	public void onMoveForward();
	public void onMoveBackward();
	public void onMoveLeft();
	public void onMoveRight();
	public void onJump();
	public void onStopMoving();
	public void onDamaged();
	public void onDeath();
	public void onAttack();
}