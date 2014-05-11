/**
 * 
 */
package com.stargem.views;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import net.dermetfan.utils.libgdx.Typewriter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.stargem.audio.AudioManager;
import com.stargem.utils.AssetList;

/**
 * LoadingScreenView.java
 *
 * @author 	Chris B
 * @date	30 Jan 2014
 * @version	1.0
 */
public class LoadingScreenView implements View {

	private final Stage stage;
	private final AssetManager assetManager;
	private final AssetList assetList;
	
	private Table loading;
	
	// local asset paths
	private final String backgroundPath 		= "data/screens/menu/background.png";
	private final String loadingAtlasPath 		= "data/screens/loading/loading.pack";
	private final String loadingMusicPath		= "data/screens/loading/FutureWorld_Loading_Loop.ogg";
	private final String briefingTextPath;
	
	// drawables
	private Image iconInner;
	private Image iconOuter;
	private Image loadingText;
	private Label briefingLabel;
	private Label continueLabel;
	
	// text writer for the briefing
	private final Typewriter typewriter = new Typewriter();
	private boolean showBriefing = false;
	private String briefingText;
	
	public LoadingScreenView(AssetManager assets, String currentWorldFilePath) {
		
		this.briefingTextPath = currentWorldFilePath + "briefing.txt";
		
		this.assetManager = assets;
		this.stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		
		// create an asset list for the view
		this.assetList = new AssetList(this.assetManager);
		
		this.assetList.add(backgroundPath, Texture.class);
		this.assetList.add(loadingAtlasPath, TextureAtlas.class);
		this.assetList.add(loadingMusicPath, Music.class);
		
		this.assetList.load();
	}
	
	/**
	 * This is called once the view is ready to be shown.
	 * All assets loaded etc
	 */
	@Override
	public void show() {
		
		TextureAtlas loadingAtlas = assetManager.get(loadingAtlasPath, TextureAtlas.class);
		
		// add the background to the root table
		TextureRegion backgroundRegion = new TextureRegion(this.assetManager.get(backgroundPath, Texture.class));
		Drawable backgroundDrawable = new TextureRegionDrawable(backgroundRegion);
		Image backgroundImage = new Image(backgroundDrawable, Scaling.fill);
		backgroundImage.setFillParent(true);
		stage.addActor(backgroundImage);
		
		// add the loading bar
		loadingText = new Image(loadingAtlas.findRegion("loading"));
		loading = new Table();
		loading.setWidth(loadingText.getWidth());
		loading.setHeight(loadingText.getHeight());
		loading.setBackground(loadingText.getDrawable(), false);		
		
		// add the loading icon
		iconInner = new Image(loadingAtlas.findRegion("loading-icon-inner"));
		iconOuter = new Image(loadingAtlas.findRegion("loading-icon-outer"));
				
		iconInner.setOrigin(iconInner.getWidth() / 2, iconInner.getHeight() / 2);
		iconOuter.setOrigin(iconOuter.getWidth() / 2, iconOuter.getHeight() / 2);
		
		Group icon = new Group();
		icon.addActor(iconOuter);
		icon.addActor(iconInner);
		iconOuter.setY(-iconOuter.getHeight() / 2);
		iconInner.setY(-iconInner.getHeight() / 2);
		loading.add(icon);
		loading.add().expand();
		
		// add loading to stage
		stage.addActor(loading);
		loading.setX(0);
		loading.setY(0);
		
		loading.setY(-loading.getHeight());
		loading.addAction(sequence(delay(0.5f), moveBy(0, loading.getHeight(), 0.2f)));
		
		// Play the background music
		Music loadingMusic = this.assetManager.get(loadingMusicPath, Music.class);	
		AudioManager.getInstance().playMusic(loadingMusic);
		
		// typewriter for showing the briefing screen
		BitmapFont frigate = new BitmapFont(Gdx.files.internal("data/fonts/space-frigate.fnt"));
		briefingLabel = new Label("", new LabelStyle(frigate, Color.WHITE));
		briefingLabel.setFillParent(false);
		briefingLabel.setWrap(true);
		typewriter.getInterpolator().setInterpolation(Interpolation.linear);
        typewriter.getAppender().set(new CharSequence[] {"", "."}, 0.5f);
        
        stage.addActor(briefingLabel);
        float margin = 50;
        briefingLabel.setWidth(Gdx.graphics.getWidth() - (margin * 2));
        briefingLabel.setHeight(Gdx.graphics.getHeight() - (margin * 2));
        briefingLabel.setPosition(margin, Gdx.graphics.getHeight() - margin - briefingLabel.getHeight());
        briefingText = Gdx.files.internal(briefingTextPath).readString();
        
        continueLabel = new Label("Click To Continue", new LabelStyle(frigate, Color.WHITE));
        continueLabel.setPosition((Gdx.graphics.getWidth() / 2) - continueLabel.getWidth() / 2, 25);
        continueLabel.getColor().a = 0;
        stage.addActor(continueLabel);
        
		// enable debug draw
		//this.loading.debug();
	}
	
	@Override
	public void hide() {
		AudioManager.getInstance().stopMusic();
	}
	
	@Override
	public void render(float deltaTime) {
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		this.iconInner.rotateBy(150 * deltaTime);
		this.iconOuter.rotateBy(-100 * deltaTime);
		
		this.stage.act(deltaTime);	
		this.stage.draw();
		
		if(this.showBriefing) {
			briefingLabel.setText(typewriter.updateAndType(briefingText, deltaTime));
			
		}
		
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
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);		
	}
	
	/**
	 * This is called when loading is completed
	 */
	public void loadingComplete() {
		// slide out the loading bar and set the briefing to show after it has left the screen
		loading.addAction(sequence(delay(0.5f), moveBy(0, -(loading.getHeight() + 5), 0.2f), new Action() {
			@Override
			public boolean act(float delta) {
				showBriefing  = true;
				continueLabel.addAction(forever(sequence(fadeIn(1f), delay(1f), fadeOut(1f))));
				return true;
			}
		}));
	}
	
}