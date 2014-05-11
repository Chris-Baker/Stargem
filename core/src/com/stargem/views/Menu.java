/**
 * 
 */
package com.stargem.views;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.tablelayout.Cell;

/**
 * Menu.java
 *
 * @author 	Chris B
 * @date	9 May 2014
 * @version	1.0
 */
public class Menu extends Table {

	private final Array<Button> buttons;
	
	public Menu() {
		this(null);
	}
	
	public Menu(Skin skin) {
		super(skin);
		this.buttons = new Array<Button>();
	}
	
	public Cell<Button> addMenuItem(Button button) {
		buttons.add(button);
		Cell<Button> c = this.add(button);
		this.row();
		return c;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		// this is actually default behaviour now
//		// set the button's down image as active onMouseOver
//		for(Button b : buttons) {
//			b.setChecked(b.isOver());
//		}
	}
	
}