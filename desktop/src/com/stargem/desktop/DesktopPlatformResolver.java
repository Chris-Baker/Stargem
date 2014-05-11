/**
 * 
 */
package com.stargem.desktop;

import com.stargem.persistence.ActionResolver;
import com.stargem.utils.PlatformResolver;

/**
 * DesktopPlatformResolver.java
 *
 * @author 	Chris B
 * @date	30 Apr 2014
 * @version	1.0
 */
public class DesktopPlatformResolver implements PlatformResolver {

	private final ActionResolver actionResolver;
	
	public DesktopPlatformResolver(ActionResolver actionResolver) {
		this.actionResolver = actionResolver;
	}
	
	/* (non-Javadoc)
	 * @see com.stargem.utils.PlatformResolver#getActionResolver()
	 */
	@Override
	public ActionResolver getActionResolver() {
		return actionResolver;
	}
	
}