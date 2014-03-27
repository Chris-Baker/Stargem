/**
 * 
 */
package com.stargem.editor.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.stargem.editor.WorldEditorListener;
import com.stargem.editor.controllers.ToolBarController;
import com.stargem.views.View;

/**
 * WorldEditorView.java
 *
 * @author 	Chris B
 * @date	14 Feb 2014
 * @version	1.0
 */
public class WorldEditorView implements View, WorldEditorListener {

//	private final ToolBarController toolbarController;
//	private final Skin skin;
//	private final Stage stage;
//	private final Table root;
//	private final Table north;
//	private final Table south;
//	private final Table east;
//	private final Table west;
//	private final Table center;
//	
//	// action bar
//	private final TextButton newBtn;
//	
//	// toolbar buttons
//	private final TextButton selectBtn;
//	private final TextButton moveBtn;
//	private final TextButton raiseTerrainBtn;
//	private final TextButton lowerTerrainBtn;
//	private final TextButton smoothTerrainBtn;
//	private final TextButton flattenTerrainBtn;
//	private final TextButton noiseTerrainBtn;
//	private final TextButton paintTextureBtn;
//	private final TextButton placeEntityBtn;
//	private final TextButton newEntityTemplateBtn;
//	
//	/**
//	 * 
//	 */
	public WorldEditorView(ToolBarController toolbarController) {
//		this.toolbarController = toolbarController;
//		this.skin = new Skin(Gdx.files.internal("holo/Holo-light-ldpi.json"));
//		this.stage = new Stage();
//		this.root = new Table(skin);
//		
//		// add the root table to the stage
//		this.stage.addActor(root);
//		root.setFillParent(true);
//		
//		// layout tables
//		this.north = new Table(skin);
//		this.south = new Table(skin);
//		this.east = new Table(skin);
//		this.west = new Table(skin);
//		this.center = new Table(skin);
//		
//		// layout
//		root.defaults().fill();
//		root.add(north).colspan(3);
//		root.row();
//		root.add(west);
//		root.add(center).expand();
//		root.add(east);
//		root.row();
//		root.add(south).colspan(3);
//		
//		// menu bar
//		//MenuBar menu = new MenuBar(skin);
//		//this.north.add(menu);
//		
//		//TextButton b = new TextButton("Button", skin);
//		//this.north.add(b);
//		
//		// action bar
//		newBtn = new TextButton(WorldEditor.NEW, skin);
//		newBtn.setName(WorldEditor.NEW);
//		newBtn.getLabel().setName(WorldEditor.NEW);
//		//newBtn.addListener(actionbarController);
//		
//		north.add(newBtn).left().expandX();
//		
//		// toolbar
//		Window toolbar = new Window("", skin);		
//		west.add(toolbar);
//		
//		selectBtn = new TextButton(WorldEditor.SELECT_TOOL, skin);
//		selectBtn.setName(WorldEditor.SELECT_TOOL);
//		selectBtn.getLabel().setName(WorldEditor.SELECT_TOOL);
//		selectBtn.addListener(toolbarController);
//		
//		moveBtn = new TextButton(WorldEditor.MOVE_TOOL, skin);
//		moveBtn.setName(WorldEditor.MOVE_TOOL);
//		moveBtn.getLabel().setName(WorldEditor.MOVE_TOOL);
//		moveBtn.addListener(toolbarController);
//		
//		raiseTerrainBtn = new TextButton(WorldEditor.RAISE_TERRAIN_TOOL, skin);
//		raiseTerrainBtn.setName(WorldEditor.RAISE_TERRAIN_TOOL);
//		raiseTerrainBtn.getLabel().setName(WorldEditor.RAISE_TERRAIN_TOOL);
//		raiseTerrainBtn.addListener(toolbarController);
//		
//		lowerTerrainBtn = new TextButton(WorldEditor.LOWER_TERRAIN_TOOL, skin);
//		lowerTerrainBtn.setName(WorldEditor.LOWER_TERRAIN_TOOL);
//		lowerTerrainBtn.getLabel().setName(WorldEditor.LOWER_TERRAIN_TOOL);
//		lowerTerrainBtn.addListener(toolbarController);
//		
//		smoothTerrainBtn = new TextButton(WorldEditor.SMOOTH_TERRAIN_TOOL, skin);
//		smoothTerrainBtn.setName(WorldEditor.SMOOTH_TERRAIN_TOOL);
//		smoothTerrainBtn.getLabel().setName(WorldEditor.SMOOTH_TERRAIN_TOOL);
//		smoothTerrainBtn.addListener(toolbarController);
//		
//		flattenTerrainBtn = new TextButton(WorldEditor.FLATTEN_TERRAIN_TOOL, skin);
//		flattenTerrainBtn.setName(WorldEditor.FLATTEN_TERRAIN_TOOL);
//		flattenTerrainBtn.getLabel().setName(WorldEditor.FLATTEN_TERRAIN_TOOL);
//		flattenTerrainBtn.addListener(toolbarController);
//		
//		noiseTerrainBtn = new TextButton(WorldEditor.NOISE_TERRAIN_TOOL, skin);
//		noiseTerrainBtn.setName(WorldEditor.NOISE_TERRAIN_TOOL);
//		noiseTerrainBtn.getLabel().setName(WorldEditor.NOISE_TERRAIN_TOOL);
//		noiseTerrainBtn.addListener(toolbarController);
//		
//		paintTextureBtn = new TextButton(WorldEditor.PAINT_TEXTURE_TOOL, skin);
//		paintTextureBtn.setName(WorldEditor.PAINT_TEXTURE_TOOL);
//		paintTextureBtn.getLabel().setName(WorldEditor.PAINT_TEXTURE_TOOL);
//		paintTextureBtn.addListener(toolbarController);
//		
//		placeEntityBtn = new TextButton(WorldEditor.PLACE_ENTITY_TOOL, skin);
//		placeEntityBtn.setName(WorldEditor.PLACE_ENTITY_TOOL);
//		placeEntityBtn.getLabel().setName(WorldEditor.PLACE_ENTITY_TOOL);
//		placeEntityBtn.addListener(toolbarController);
//		
//		newEntityTemplateBtn = new TextButton(WorldEditor.NEW_ENTITY_TEMPLATE_TOOL, skin);
//		newEntityTemplateBtn.setName(WorldEditor.NEW_ENTITY_TEMPLATE_TOOL);
//		newEntityTemplateBtn.getLabel().setName(WorldEditor.NEW_ENTITY_TEMPLATE_TOOL);
//		newEntityTemplateBtn.addListener(toolbarController);
//		
//		toolbar.add(selectBtn);
//		toolbar.add(moveBtn);
//		toolbar.row();
//		toolbar.add(raiseTerrainBtn);
//		toolbar.add(lowerTerrainBtn);
//		toolbar.row();
//		toolbar.add(smoothTerrainBtn);
//		toolbar.add(flattenTerrainBtn);
//		toolbar.row();
//		toolbar.add(noiseTerrainBtn);
//		toolbar.add(paintTextureBtn);
//		toolbar.row();
//		toolbar.add(placeEntityBtn);
//		toolbar.add(newEntityTemplateBtn);
//		toolbar.row();
//		
//		// entity explorer
//		
//		// set the stage as input processor
//		Gdx.input.setInputProcessor(stage);
	}

	/* (non-Javadoc)
	 * @see com.stargem.views.View#render(float)
	 */
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		//stage.act(delta);
		//stage.draw();
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
	public void resize(int width, int height) {
	}

	/* (non-Javadoc)
	 * @see com.stargem.views.View#dispose()
	 */
	@Override
	public void dispose() {
	}

	@Override
	public void onSetTool(String toolName) {
		// TODO show the context options for the selected tool
		
	}

	@Override
	public void onCommandStackSizeChanged(int undoSize, int redoSize) {
		// TODO enable / disable undo redo options if needed. Maybe show a number of commands undoable or redoable
		
	}

	

}