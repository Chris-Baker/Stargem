/**
 * 
 */
package com.stargem.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * VectorUtils.java
 *
 * @author 	Chris B
 * @date	28 Apr 2014
 * @version	1.0
 */
public class VectorUtils {

	private static final Vector3 tmp1 = new Vector3();
	private static final Vector3 tmp2 = new Vector3();
	private static final Vector3 tmp3 = new Vector3();
	private static final Vector2 targetDirection = new Vector2();
	
	/**
	 * Helper function to check whether a direction is facing a point
	 * within an epsilon.
	 * 
	 * @param location the location
	 * @param direction the direction being faced
	 * @param target a point to look at
	 * @param epsilon in radians
	 * @return
	 */
	public static boolean isFacing(Vector3 location, Vector3 direction, Vector3 target, float epsilon) {
		targetDirection.set(target.x - location.x, target.z - location.z).nor();
		float targetAngle = MathUtils.atan2(targetDirection.y, targetDirection.x);		
		float currentAngle = MathUtils.atan2(direction.z, direction.x);
				
		if (currentAngle < targetAngle - epsilon || currentAngle > targetAngle + epsilon) {
			return true;
		}
		return false;
	}
	
	/**
	 * Project the given point onto the given plane
	 * 
	 * @param planeNormal a normalised direction vector
	 * @param planePoint a point on the plane
	 * @param point the point to project
	 * @param out the result vector
	 * @return
	 */
	public static Vector3 projectPointOnPlane(Vector3 planeNormal, Vector3 planePoint, Vector3 point, Vector3 out) {		
		
		tmp1.set(planeNormal);
		tmp2.set(planePoint);
		tmp3.set(point);		
		
		float distance = -tmp1.dot(tmp3.sub(tmp2));	
		out.set(tmp3.add(tmp1.scl(distance)));
		return out;
	}
	
}