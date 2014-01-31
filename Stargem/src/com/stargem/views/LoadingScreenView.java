/**
 * 
 */
package com.stargem.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.stargem.AssetList;
import com.stargem.views.widgets.LoadingBar;

/**
 * LoadingScreenView.java
 *
 * @author 	Chris B
 * @date	30 Jan 2014
 * @version	1.0
 */
public class LoadingScreenView implements View {

	private final Stage stage;
	private final AssetManager assets;
	private final AssetList assetList;
	
	private Table root;
	private LoadingBar loadingBar;
	
	// local asset paths
	private final String backgroundPath 		= "data/screens/loading/background.jpg";
	private final String loadingBarEmptyPath 	= "data/screens/loading/loading-bar-empty.png";
	private final String loadingBarFullPath 	= "data/screens/loading/loading-bar-full.png";
	private final String loadingTextPath		= "data/screens/loading/initialising-uplink.png";
	private final String loadingMusicPath		= "data/screens/loading/FutureWorld_Loading_Loop.ogg";
		
	public LoadingScreenView(AssetManager assets) {
		this.assets = assets;
		this.stage = new Stage();
		
		// create an asset list for the view
		this.assetList = new AssetList(this.assets);
		
		this.assetList.add(backgroundPath, Texture.class);
		this.assetList.add(loadingBarEmptyPath, Texture.class);
		this.assetList.add(loadingBarFullPath, Texture.class);
		this.assetList.add(loadingTextPath, Texture.class);
		this.assetList.add(loadingMusicPath, Music.class);
		
		this.assetList.load();
	}
	
	/**
	 * This is called once the view is ready to be shown.
	 * All assets loaded etc
	 */
	@Override
	public void show() {
		
		// get the loaded assets from the asset manager
		Texture backgroundTexture 		= this.assets.get(backgroundPath, Texture.class);
		Texture loadingBarEmptyTexture 	= this.assets.get(loadingBarEmptyPath, Texture.class);
		Texture loadingBarFullTexture 	= this.assets.get(loadingBarFullPath, Texture.class);
		Texture loadingTextTexture 		= this.assets.get(loadingTextPath, Texture.class);
		
		// create the root table
		this.root = new Table();
		this.root.setFillParent(true);
		this.stage.addActor(root);
		
		// add the background to the root table
		Image backgroundImage = new Image(backgroundTexture);
		backgroundImage.setFillParent(true);
		root.addActor(backgroundImage);
		
		Image loadingBarEmptyImage = new Image(loadingBarEmptyTexture);
		Image loadingBarFullImage = new Image(loadingBarFullTexture);
		loadingBar = new LoadingBar(loadingBarEmptyImage, loadingBarFullImage);
		
		root.add().expandY();
		root.row();
		root.add(loadingBar).expandX().padBottom(140);
				
		// Play the background music
		Music loadingMusic = this.assets.get(loadingMusicPath, Music.class);	
		loadingMusic.setLooping(true);
		loadingMusic.play();
		
		// enable debug draw
		//this.root.debug();
	}
	
	@Override
	public void render(float deltaTime) {
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		this.loadingBar.update(this.assets.getProgress() * 100);
		
		this.stage.act(deltaTime);	
		this.stage.draw();
		
		// debug draw table
		//Table.drawDebug(stage);
	}

	@Override
	public void dispose() {
		this.assetList.unload();
		this.stage.dispose();		
	}

	/* (non-Javadoc)
	 * @see com.stargem.views.View#resize(float, float)
	 */
	@Override
	public void resize(float width, float height) {
		stage.setViewport(width, height, true);
	}	
}