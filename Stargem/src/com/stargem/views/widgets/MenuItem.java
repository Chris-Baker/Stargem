/**
 * 
 */
package com.stargem.views.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.esotericsoftware.tablelayout.Cell;

/**
 * Base for all menu type.
 * 
 * @author Kyu
 *
 */
public class MenuItem extends Button
{
    
    protected MenuItemStyle style;
    
    protected Cell<Actor> iconCell;
    protected Cell<Actor> textCell;
    protected Cell<Actor> shortcutCell;
    protected Cell<Actor> subCell;
    
    /**
     * Create a raw menu with default {@link MenuItemStyle style}.
     * 
     * @param skin the skin to use
     */
    public MenuItem(Skin skin)
    {
	this(skin, "default");
    }
    
    /**
     * Create a raw menu with specified {@link MenuItemStyle style}.
     * 
     * @param skin the skin to use
     * @param styleName the style to use
     */
    @SuppressWarnings("unchecked")
    public MenuItem(Skin skin, String styleName)
    {
	super(skin);
	
	setStyle(skin.get(styleName, MenuItemStyle.class));
	this.defaults().minHeight(10f);
	iconCell = this.add().minWidth(16f);
	textCell = this.add().minWidth(150f).padLeft(5f);
	shortcutCell = this.add().minWidth(50f);
	subCell = this.add().minWidth(8f);
    }
    
    /**
     * Apply the {@link MenuItemStyle style}.
     * 
     * @param style the {@link MenuItemStyle style} to apply
     */
    public void setStyle(MenuItemStyle style)
    {
	this.style = style;
    }
    
    /** 
     * Block forced checked state.
     */
    @Override
    public void setChecked(boolean isChecked)
    {
    }

    /**
     * {@link MenuItemStyle Style} for all menu item.
     * 
     * @author Kyu
     *
     */
    public static class MenuItemStyle
    {

	public TextButtonStyle buttonStyle;
	
	/** Optional. */
	public Drawable icon, sub;

	public MenuItemStyle()
	{

	}

    }
    
}