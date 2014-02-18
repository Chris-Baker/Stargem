/**
 * 
 */
package com.stargem.views.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Create a simple {@link Table table layout} with a background.
 * 
 * @author Kyu
 * 
 */
public class Panel extends Table {

	private PanelStyle style;

	/**
	 * Create a {@link Panel panel}
	 */
	public Panel() {
	}

	/**
	 * Create a {@link Panel panel} with default style.
	 * 
	 * @param skin the skin to use
	 */
	public Panel(Skin skin) {
		this(skin, "default");
	}

	/**
	 * Create a {@link Panel panel} with specified style.
	 * 
	 * @param skin the skin to use
	 * @param styleName the style to use
	 */
	public Panel(Skin skin, String styleName) {
		super(skin);

		setStyle(skin.get(styleName, PanelStyle.class));
	}

	/**
	 * Apply a {@link PanelStyle style}.
	 * 
	 * @param style the style to apply
	 * @throws IllegalArgumentException if the style is null
	 */
	public void setStyle(PanelStyle style) {
		if (style == null) {
			throw new IllegalArgumentException("style cannot be null");
		}

		this.style = style;
		this.setBackground(style.background);

		invalidateHierarchy();
	}

	/**
	 * @return the current style of this {@link Panel panel}.
	 */
	public PanelStyle getStyle() {
		return style;
	}

	/**
	 * Define the style of a {@link Panel panel}.
	 * 
	 * @author Kyu
	 * 
	 */
	static public class PanelStyle {

		/** Optional. */
		public Drawable background;

		public PanelStyle() {
		}

		public PanelStyle(Drawable background) {
			this.background = background;
		}

		public PanelStyle(PanelStyle style) {
			this.background = style.background;
		}

	}

}