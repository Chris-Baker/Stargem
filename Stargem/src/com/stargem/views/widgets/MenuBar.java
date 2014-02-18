/**
 * 
 */
package com.stargem.views.widgets;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.esotericsoftware.tablelayout.Cell;

/**
 * A simple menubar containing {@link ContextMenu context menus}.
 * 
 * @author Kyu
 * 
 */
public class MenuBar extends Panel {

	private MenuBarStyle style;

	private final Map<Button, ContextMenu> menuBind = new HashMap<Button, ContextMenu>();
	private static ContextMenu lastOpened;

	private boolean isOpen;
	private boolean isInitialized;

	/**
	 * Create a {@link MenuBar menubar} with default style.
	 * 
	 * @param skin the skin to use
	 */
	public MenuBar(Skin skin) {
		this(skin, "default");
	}

	/**
	 * Create a {@link MenuBar menubar} with the specified style.
	 * 
	 * @param skin the skin to use
	 * @param styleName the style to use
	 */
	public MenuBar(Skin skin, String styleName) {
		super(skin);

		setStyle(skin.get(styleName, MenuBarStyle.class));
	}

	/**
	 * Apply the style of this {@link MenuBar menubar}.
	 * 
	 * @param style the {@link MenuBarStyle style} to apply.
	 */
	public void setStyle(MenuBarStyle style) {
		this.style = style;

		this.setBackground(style.background);
		this.left();
	}

	/**
	 * Add a {@link ContextMenu context menu}.
	 * 
	 * @param actor the button that will open the {@link ContextMenu context menu}
	 * @param menu the {@link ContextMenu context menu} to add
	 * @param stage the stage where to display the {@link ContextMenu context menu}
	 * 
	 * @return the {@link Cell cell} where the button opening the {@link ContextMenu context menu} was added
	 */
	public Cell<?> addContextMenu(final Button actor, final ContextMenu menu, final Stage stage) {
		stage.addActor(menu);
		menuBind.put(actor, menu);

		if (!isInitialized) {
			isInitialized = true;
			stage.addListener(new InputListener() {

				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					Actor _target = event.getTarget();
					if (_target instanceof ContextMenu || _target instanceof SubMenuItem) {
						return false;
					}
					else if (_target instanceof Label) {
						Actor _parent = _target.getParent();
						if (_parent instanceof TextButton) {
							if (menuBind.containsKey(_parent)) {
								return true;
							}
						}
						else if (_parent instanceof SubMenuItem) {
							return true;
						}
					}

					closeMenu();

					isOpen = false;

					return true;
				}

			});
		}
		actor.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (button != Buttons.LEFT) {
					return false;
				}

				closeMenu();

				lastOpened = menu;

				isOpen = !isOpen;
				if (isOpen) {
					menu.setPosition(actor.getX(), stage.getHeight() - actor.getY() - actor.getHeight());
				}

				menu.setVisible(isOpen);

				return true;
			}

			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				if (!isOpen) {
					return;
				}

				closeMenu();

				lastOpened = menu;

				menu.setVisible(true);
				menu.setPosition(actor.getX(), stage.getHeight() - actor.getY() - actor.getHeight());
			}

		});

		return super.add(actor).minWidth(actor.getWidth());
	}

	@Deprecated
	@Override
	public Cell<?> add() {
		throw new RuntimeException("Use addContextMenu(Button, ContextMenu, Stage) instead.");
	};

	@Deprecated
	@Override
	public Cell<?> add(Actor actor) {
		throw new RuntimeException("Use addContextMenu(Button, ContextMenu, Stage) instead.");
	}

	@Deprecated
	@Override
	public Cell<?> add(String text) {
		throw new RuntimeException("Use addContextMenu(Button, ContextMenu, Stage) instead.");
	}

	@Deprecated
	@Override
	public Cell<?> add(String text, String fontName, Color color) {
		throw new RuntimeException("Use addContextMenu(Button, ContextMenu, Stage) instead.");
	}

	@Deprecated
	@Override
	public Cell<?> add(String text, String fontName, String colorName) {
		throw new RuntimeException("Use addContextMenu(Button, ContextMenu, Stage) instead.");
	}

	@Deprecated
	@Override
	public Cell<?> add(String text, String labelStyleName) {
		throw new RuntimeException("Use addContextMenu(Button, ContextMenu, Stage) instead.");
	}

	@Deprecated
	@Override
	public void addActor(Actor actor) {
		super.addActor(actor);
	}

	@Deprecated
	@Override
	public void addActorAfter(Actor actorAfter, Actor actor) {
		throw new RuntimeException("Use addContextMenu(Button, ContextMenu, Stage) instead.");
	}

	@Deprecated
	@Override
	public void addActorAt(int index, Actor actor) {
		throw new RuntimeException("Use addContextMenu(Button, ContextMenu, Stage) instead.");
	}

	@Deprecated
	@Override
	public void addActorBefore(Actor actorBefore, Actor actor) {
		throw new RuntimeException("Use addContextMenu(Button, ContextMenu, Stage) instead.");
	}

	@Deprecated
	@Override
	public boolean removeActor(Actor actor) {
		throw new RuntimeException("You don't have to remove anything.");
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
	 * Define the style for a {@link MenuBar menubar}.
	 * 
	 * @author Kyu
	 * 
	 */
	public static class MenuBarStyle {

		/** Optional */
		public Drawable background;

		public MenuBarStyle() {

		}

	}

}