/**
 * 
 */
package com.stargem.views.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * A context menu containing {@link MenuItem menu items}.
 * 
 * @author Kyu
 * 
 */
public class ContextMenu extends VerticalGroup {

	/**
	 * Store all open {@link ContextMenu context menus} of this {@link ContextMenu context menu}.(
	 */
	private ContextMenu lastOpened;

	/**
	 * Create a {@link ContextMenu context menu}
	 */
	public ContextMenu() {
		setAlignment(Align.top + Align.left);
	}

	/**
	 * Add a menu.
	 * 
	 * @param menu the menu to add
	 */
	public void addMenu(MenuItem menu) {
		setVisible(false);
		super.addActor(menu);
	}

	/**
	 * Close recursively {@link ContextMenu context menus} tree.
	 */
	public void closeMenu() {
		if (lastOpened == null) {
			return;
		}

		lastOpened.closeMenu();
		lastOpened.setVisible(false);
		lastOpened = null;
	}

	/**
	 * Used internally.
	 */
	public void setLastOpened(ContextMenu menu) {
		lastOpened = menu;
	}

}