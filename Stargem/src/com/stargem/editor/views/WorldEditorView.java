/**
 * 
 */
package com.stargem.editor.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.stargem.editor.WorldEditorListener;
import com.stargem.editor.controllers.WorldEditorController;
import com.stargem.views.View;

/**
 * WorldEditorView.java
 *
 * @author 	Chris B
 * @date	14 Feb 2014
 * @version	1.0
 */
public class WorldEditorView implements View, WorldEditorListener {

	private final WorldEditorController controller;
	private final Skin skin;
	private final Stage stage;
	private final Table root;
	private final Table north;
	private final Table south;
	private final Table east;
	private final Table west;
	private final Table center;
	
	// toolbar buttons
	private final TextButton selectBtn;
	private final TextButton moveBtn;
	private final TextButton raiseTerrainBtn;
	private final TextButton lowerTerrainBtn;
	private final TextButton smoothTerrainBtn;
	private final TextButton flattenTerrainBtn;
	private final TextButton noiseTerrainBtn;
	private final TextButton paintTextureBtn;
	private final TextButton placeEntityBtn;
	private final TextButton newEntityTemplateBtn;
	
	// button names
	public static final String SELECT_BTN 				= "Select";
	public static final String MOVE_BTN 				= "Move";
	public static final String RAISE_TERRAIN_BTN 		= "Raise";
	public static final String LOWER_TERRAIN_BTN 		= "Lower";
	public static final String SMOOTH_TERRAIN_BTN 		= "Smooth";
	public static final String FLATTEN_TERRAIN_BTN 		= "Flatten";
	public static final String NOISE_TERRAIN_BTN 		= "Noise";
	public static final String PAINT_TEXTURE_BTN 		= "Paint";
	public static final String PLACE_ENTITY_BTN 		= "Place";
	public static final String NEW_ENTITY_TEMPLATE_BTN 	= "New";
	
	/**
	 * 
	 */
	public WorldEditorView(WorldEditorController controller) {
		this.controller = controller;
		this.skin = new Skin(Gdx.files.internal("holo/Holo-light-ldpi.json"));
		this.stage = new Stage();
		this.root = new Table(skin);
		
		// add the root table to the stage
		this.stage.addActor(root);
		root.setFillParent(true);
		
		// layout tables
		this.north = new Table(skin);
		this.south = new Table(skin);
		this.east = new Table(skin);
		this.west = new Table(skin);
		this.center = new Table(skin);
		
		// layout
		root.defaults().fill();
		root.add(north).colspan(3);
		root.row();
		root.add(west);
		root.add(center).expand();
		root.add(east);
		root.row();
		root.add(south).colspan(3);
		
		// menu bar
		//MenuBar menu = new MenuBar(skin);
		//this.north.add(menu);
		
		//TextButton b = new TextButton("Button", skin);
		//this.north.add(b);
		
		// toolbar
		Window toolbar = new Window("", skin);		
		west.add(toolbar);
		
		selectBtn = new TextButton(SELECT_BTN, skin);
		selectBtn.setName(SELECT_BTN);
		selectBtn.getLabel().setName(SELECT_BTN);
		selectBtn.addListener(controller);
		
		moveBtn = new TextButton(MOVE_BTN, skin);
		moveBtn.setName(MOVE_BTN);
		moveBtn.getLabel().setName(MOVE_BTN);
		moveBtn.addListener(controller);
		
		raiseTerrainBtn = new TextButton(RAISE_TERRAIN_BTN, skin);
		raiseTerrainBtn.setName(RAISE_TERRAIN_BTN);
		raiseTerrainBtn.getLabel().setName(RAISE_TERRAIN_BTN);
		raiseTerrainBtn.addListener(controller);
		
		lowerTerrainBtn = new TextButton(LOWER_TERRAIN_BTN, skin);
		lowerTerrainBtn.setName(LOWER_TERRAIN_BTN);
		lowerTerrainBtn.getLabel().setName(LOWER_TERRAIN_BTN);
		lowerTerrainBtn.addListener(controller);
		
		smoothTerrainBtn = new TextButton(SMOOTH_TERRAIN_BTN, skin);
		smoothTerrainBtn.setName(SMOOTH_TERRAIN_BTN);
		smoothTerrainBtn.getLabel().setName(SMOOTH_TERRAIN_BTN);
		smoothTerrainBtn.addListener(controller);
		
		flattenTerrainBtn = new TextButton(FLATTEN_TERRAIN_BTN, skin);
		flattenTerrainBtn.setName(FLATTEN_TERRAIN_BTN);
		flattenTerrainBtn.getLabel().setName(FLATTEN_TERRAIN_BTN);
		flattenTerrainBtn.addListener(controller);
		
		noiseTerrainBtn = new TextButton(NOISE_TERRAIN_BTN, skin);
		noiseTerrainBtn.setName(NOISE_TERRAIN_BTN);
		noiseTerrainBtn.getLabel().setName(NOISE_TERRAIN_BTN);
		noiseTerrainBtn.addListener(controller);
		
		paintTextureBtn = new TextButton(PAINT_TEXTURE_BTN, skin);
		paintTextureBtn.setName(PAINT_TEXTURE_BTN);
		paintTextureBtn.getLabel().setName(PAINT_TEXTURE_BTN);
		paintTextureBtn.addListener(controller);
		
		placeEntityBtn = new TextButton(PLACE_ENTITY_BTN, skin);
		placeEntityBtn.setName(PLACE_ENTITY_BTN);
		placeEntityBtn.getLabel().setName(PLACE_ENTITY_BTN);
		placeEntityBtn.addListener(controller);
		
		newEntityTemplateBtn = new TextButton(NEW_ENTITY_TEMPLATE_BTN, skin);
		newEntityTemplateBtn.setName(NEW_ENTITY_TEMPLATE_BTN);
		newEntityTemplateBtn.getLabel().setName(NEW_ENTITY_TEMPLATE_BTN);
		newEntityTemplateBtn.addListener(controller);
		
		toolbar.add(selectBtn);
		toolbar.add(moveBtn);
		toolbar.row();
		toolbar.add(raiseTerrainBtn);
		toolbar.add(lowerTerrainBtn);
		toolbar.row();
		toolbar.add(smoothTerrainBtn);
		toolbar.add(flattenTerrainBtn);
		toolbar.row();
		toolbar.add(noiseTerrainBtn);
		toolbar.add(paintTextureBtn);
		toolbar.row();
		toolbar.add(placeEntityBtn);
		toolbar.add(newEntityTemplateBtn);
		toolbar.row();
		
		// entity explorer
		
		// set the stage as input processor
		Gdx.input.setInputProcessor(stage);
	}

	/* (non-Javadoc)
	 * @see com.stargem.views.View#render(float)
	 */
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
	}

	/* (non-Javadoc)
	 * @see com.stargem.views.View#show()
	 */
	@Override
	public void show() {
	}

	/* (non-Javadoc)
	 * @see com.stargem.views.View#resize(float, float)
	 */
	@Override
	public void resize(float width, float height) {
	}

	/* (non-Javadoc)
	 * @see com.stargem.views.View#dispose()
	 */
	@Override
	public void dispose() {
	}

	/* (non-Javadoc)
	 * @see com.stargem.editor.WorldEditorListener#setToolSelect()
	 */
	@Override
	public void setToolSelect() {
	}

	/* (non-Javadoc)
	 * @see com.stargem.editor.WorldEditorListener#setToolMove()
	 */
	@Override
	public void setToolMove() {
	}

	/* (non-Javadoc)
	 * @see com.stargem.editor.WorldEditorListener#setToolRaiseTerrain()
	 */
	@Override
	public void setToolRaiseTerrain() {
	}

	/* (non-Javadoc)
	 * @see com.stargem.editor.WorldEditorListener#setToolLowerTerrain()
	 */
	@Override
	public void setToolLowerTerrain() {
	}

	/* (non-Javadoc)
	 * @see com.stargem.editor.WorldEditorListener#setToolSmoothTerrain()
	 */
	@Override
	public void setToolSmoothTerrain() {
	}

	/* (non-Javadoc)
	 * @see com.stargem.editor.WorldEditorListener#setToolFlattenTerrain()
	 */
	@Override
	public void setToolFlattenTerrain() {
	}

	/* (non-Javadoc)
	 * @see com.stargem.editor.WorldEditorListener#setToolNoiseTerrain()
	 */
	@Override
	public void setToolNoiseTerrain() {
	}

	/* (non-Javadoc)
	 * @see com.stargem.editor.WorldEditorListener#setToolPaintTexture()
	 */
	@Override
	public void setToolPaintTexture() {
	}

	/* (non-Javadoc)
	 * @see com.stargem.editor.WorldEditorListener#setToolPlaceEntity()
	 */
	@Override
	public void setToolPlaceEntity() {
	}

}