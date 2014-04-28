/**
 * 
 */
package com.stargem.behaviour;

/**
 * Singleton null behaviour
 * 
 * NullBehaviour.java
 *
 * @author 	Chris B
 * @date	24 Apr 2014
 * @version	1.0
 */
public class NullBehaviour implements BehaviourStrategy {

	private static NullBehaviour instance = new NullBehaviour();
	public static NullBehaviour getInstance() {
		return instance;
	}
	private NullBehaviour() {}
	
	/* (non-Javadoc)
	 * @see com.stargem.ai.Behaviour#onIdle()
	 */
	@Override
	public void onIdle() {
	}

	/* (non-Javadoc)
	 * @see com.stargem.ai.Behaviour#onWakeUp()
	 */
	@Override
	public void onWakeUp() {
	}

	/* (non-Javadoc)
	 * @see com.stargem.ai.Behaviour#onSleep()
	 */
	@Override
	public void onSleep() {
	}

	/* (non-Javadoc)
	 * @see com.stargem.ai.Behaviour#onCombat()
	 */
	@Override
	public void onCombatStarted() {
	}

	/* (non-Javadoc)
	 * @see com.stargem.ai.Behaviour#onMoveForward()
	 */
	@Override
	public void onMoveForward() {
	}

	/* (non-Javadoc)
	 * @see com.stargem.ai.Behaviour#onMoveBackward()
	 */
	@Override
	public void onMoveBackward() {
	}

	/* (non-Javadoc)
	 * @see com.stargem.ai.Behaviour#onMoveLeft()
	 */
	@Override
	public void onMoveLeft() {
	}

	/* (non-Javadoc)
	 * @see com.stargem.ai.Behaviour#onMoveRight()
	 */
	@Override
	public void onMoveRight() {
	}

	/* (non-Javadoc)
	 * @see com.stargem.ai.Behaviour#onJump()
	 */
	@Override
	public void onJump() {
	}
	/* (non-Javadoc)
	 * @see com.stargem.ai.Behaviour#onStopMoving()
	 */
	@Override
	public void onStopMoving() {
	}
	/* (non-Javadoc)
	 * @see com.stargem.behaviour.BehaviourStrategy#onDamaged()
	 */
	@Override
	public void onDamaged() {
	}
	/* (non-Javadoc)
	 * @see com.stargem.behaviour.BehaviourStrategy#onAttack()
	 */
	@Override
	public void onAttack() {
	}
	/* (non-Javadoc)
	 * @see com.stargem.behaviour.BehaviourStrategy#onDeath()
	 */
	@Override
	public void onDeath() {
	}

}