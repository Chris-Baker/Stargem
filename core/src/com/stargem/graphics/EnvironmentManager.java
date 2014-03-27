/**
 * 
 */
package com.stargem.graphics;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.utils.IntMap;
import com.stargem.entity.components.RenderablePointLight;

/**
 * EnvironmentManager.java
 *
 * @author 	Chris B
 * @date	25 Mar 2014
 * @version	1.0
 */
public class EnvironmentManager {

	private final Environment environment;
	
	// Entity IDs are used as keys
	private final IntMap<DirectionalLight > directionalLights;
	private final IntMap<PointLight > pointLights;
	
	// Singleton instance
	public static EnvironmentManager getInstance() {
		return instance;
	}
	private static final EnvironmentManager instance = new EnvironmentManager();
	
	private EnvironmentManager() {
		
		directionalLights = new IntMap<DirectionalLight>();
		pointLights = new IntMap<PointLight>();
		
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 1.f));
		
		DirectionalLight shadowLight = new DirectionalLight();
		shadowLight.set(0.8f, 0.8f, 0.8f, -0.5f, -1f, 0.7f);
		environment.add(shadowLight);
		
	}
	
	/**
	 * @return the environment
	 */
	public Environment getEnvironment() {
		return environment;
	}
	
	/**
	 * Set the ambient light of the environment
	 * 
	 * @param r red value minimum 0 max 1
	 * @param g green value minimum 0 max 1
	 * @param b blue value minimum 0 max 1
	 * @param a alpha value minimum 0 max 1
	 */
	public void setAmbientLight(float r, float g, float b, float a) {
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, r, g, b, a));
	}
	
	/**
	 * Reset the environment and remove all lights.
	 */
	public void clear() {
		environment.clear();
		directionalLights.clear();
		pointLights.clear();
	}	
	
	/**
	 * Create a point light from an equivalent component and add it to the environment
	 * 
	 * @param entityID the entityID of the entity who owns the component.
	 * @param component the component which contains all the information needed to create a point light.
	 */
	public void createLightFromComponent(int entityID, RenderablePointLight component) {
		
		PointLight pointLight = new PointLight();
		pointLight.color.set(component.colour);
		pointLight.intensity = component.intensity;
		pointLight.position.x = component.x;
		pointLight.position.y = component.y;
		pointLight.position.z = component.z;
		
		pointLights.put(entityID, pointLight);
		
	}
	
	/**
	 * Update the light mapped to the given Entity ID setting its colour, intensity, and position.
	 * 
	 * @param entityID the Entity ID that the light is mapped to
	 * @param colour the colour as an RGBA integer
	 * @param intensity theintensity of the light
	 * @param x
	 * @param y
	 * @param z
	 */
	public void updatePointLight(int entityID, int colour, float intensity, float x, float y, float z) {		
		if(!pointLights.containsKey(entityID)) {
			return;
		}		
		PointLight pointLight = pointLights.get(entityID);
		pointLight.color.set(colour);
		pointLight.intensity = intensity;
		pointLight.position.x = x;
		pointLight.position.y = y;
		pointLight.position.z = z;
	}
	
	/**
	 * Remove a point light from the environment given the Entity ID
	 * the light is mapped to.
	 * 
	 * @param entityID the Entity ID that the light is mapped to.
	 */
	public void removePointLight(int entityID) {
		if(!pointLights.containsKey(entityID)) {
			return;			
		}		
		PointLight pointLight = pointLights.remove(entityID);
		environment.pointLights.removeValue(pointLight, false);	
	}

	/**
	 * @param lightIndex
	 * @return
	 */
	public PointLight getPointLight(int lightIndex) {
		return pointLights.get(lightIndex);
	}	
	
}