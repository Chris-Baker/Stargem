/**
 * 
 */
package com.stargem.views.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


/**
 * A SubMenuItem allow to open an other {@link ContextMenu context menu} from a {@link ContextMenu context menu}.
 * 
 * @author Kyu
 *
 */
public class SubMenuItem extends MenuItem
{
    
    private ContextMenu menu;
    
    /**
     * Create a {@link SubMenuItem} with default style.
     * 
     * @param text the label of the menu
     * @param skin the skin to use
     */
    public SubMenuItem(String text, Skin skin)
    {
	this(text, skin, "default-sub");
    }
    
    /**
     * Create a {@link SubMenuItem} with the specified style.
     * 
     * @param text the label of the menu
     * @param skin the skin to use
     * @param styleName the style to use
     */
    public SubMenuItem(String text, Skin skin, String styleName)
    {
	super(skin, styleName);
	
	if(style.icon != null) {
		this.iconCell.setWidget(new Image(style.icon));
	}
	if(style.sub != null) {
		this.subCell.setWidget(new Image(style.sub));
	}
	
	this.textCell.setWidget(new Label(text, skin));
    }
    
    /**
     * Set the {@link ContextMenu context menu} to use.
     * 
     * @param menu the {@link ContextMenu context menu} to bind
     * @param stage the stage where you want to show it
     */
    public void setContextMenu(final ContextMenu menu, final Stage stage)
    {
	this.menu = menu;
	
	menu.setVisible(false);
	stage.addActor(menu);
	this.addListener(new InputListener()
	{
	    
	    @Override
	    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
	    {
		SubMenuItem _self = SubMenuItem.this;
		
		ContextMenu _parent = ((ContextMenu) _self.getParent());
		_parent.closeMenu();
		_parent.setLastOpened(menu);
		
		menu.setPosition(_self.getX() + _self.getWidth() + _self.getParent().getX(), _self.getY() + _self.getParent().getY() + _self.getHeight());
		menu.setVisible(true);
	    }
	    
	});
    }
    
    /**
     * @return the {@link ContextMenu context menu} of this {@link SubMenuItem}.
     */
    public ContextMenu getContextMenu()
    {
	return menu;
    }
    
}