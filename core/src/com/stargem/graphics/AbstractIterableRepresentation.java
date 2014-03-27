/**
 * 
 */
package com.stargem.graphics;

import java.util.Iterator;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.utils.Array;

/**
 * AbstractIterableRepresentation.java
 *
 * @author 	Chris B
 * @date	29 Jan 2014
 * @version	1.0
 */
public abstract class AbstractIterableRepresentation implements Iterable<ModelInstance> {

	protected final Array<Model> models;
	protected final Array<ModelInstance> modelInstances;
	
	public AbstractIterableRepresentation(int numModels, int numInstances) {
		this.models = new Array<Model>(numModels);
		this.modelInstances = new Array<ModelInstance>(numInstances);
	}
	
	/**
	 * Dispose the models created for this terrain representation
	 */
	public void dispose() {
		
		for(Model model : this.models) {
			model.dispose();
		}
		
	}
	
	@Override
	public Iterator<ModelInstance> iterator() {        
        return modelInstances.iterator(); 
    }
	
}