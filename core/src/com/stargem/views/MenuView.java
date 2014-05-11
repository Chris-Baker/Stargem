/**
 * 
 */
package com.stargem.views;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.stargem.Config;
import com.stargem.GameManager;
import com.stargem.audio.AudioManager;
import com.stargem.controllers.MenuController;
import com.stargem.persistence.PersistenceManager;
import com.stargem.utils.AssetList;

/**
 * MenuView.java
 *
 * @author 	Chris B
 * @date	20 Apr 2014
 * @version	1.0
 */
public class MenuView implements View {

	private final AssetManager assetManager;
	private final AssetList assetList;
	private final Stage stage;	
	private final InputListener controller;
	
	// asset paths
	private final String menuAtlasPath 	= "data/screens/menu/menu.pack";
	private final String backgroundPath = "data/screens/menu/background.png";
	private final String musicPath 		= "data/screens/menu/MainTheme.ogg";
	
	// drawbales
	private Image backgroundImage;
	private Image logoImage;
	
	// dialogues
	ExitDialogue exitDialogue;
	NewProfileDialogue newProfileDialogue;
	//ChooseProfileDialogue chooseProfileDialogue;
	
	// menu tables
	Menu mainMenu;
	Menu profileMenu;
	Table optionsMenu;
	Table newPlayerForm;
	Table chooseProfile;
	
	// new profile
	public Label newProfileError;
	public TextField playerNameInput;
	
	// choose profile
	public List<String> profileList;
	private ScrollPane profileScrollPane;
	private Label profileError;
	private Button loadProfileBtn;
	private Button cancelProfileBtn;
	
	/**
	 * @param assetManager
	 */
	public MenuView(AssetManager assetManager) {
		this.assetManager = assetManager;
		this.assetList = new AssetList(this.assetManager);
		this.stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		this.controller = new MenuController(this);
				
		// add assets to the list so they can be loaded by the manager
		this.assetList.add(menuAtlasPath, TextureAtlas.class);
		this.assetList.add(backgroundPath, Texture.class);		
		this.assetList.add(musicPath, Music.class);
		this.assetList.load();
	}

	@Override
	public void render(float delta) {		
		this.stage.act(delta);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);			
		this.stage.draw();
		Table.drawDebug(stage);
	}

	@Override
	public void show() {
		
		// add the stage as an input processor so we can push buttons
		GameManager.getInstance().addInputProcessor(this.stage);				
		this.stage.addListener(controller);
		
		// load the atlas with all our menu textures
		TextureAtlas menuAtlas = assetManager.get(menuAtlasPath, TextureAtlas.class);
		
		// Play the background music
		Music loadingMusic = this.assetManager.get(musicPath, Music.class);	
		AudioManager.getInstance().playMusic(loadingMusic);
		
		// create the background
		TextureRegion backgroundRegion = new TextureRegion(this.assetManager.get(backgroundPath, Texture.class));
		Drawable backgroundDrawable = new TextureRegionDrawable(backgroundRegion);
		backgroundImage = new Image(backgroundDrawable, Scaling.fill);
		backgroundImage.setFillParent(true);
		stage.addActor(backgroundImage);
				
		// create the logo
		logoImage = new Image(menuAtlas.findRegion("logo"));
		stage.addActor(logoImage);
		
		// fade background in
		backgroundImage.getColor().a = 0;
		backgroundImage.addAction(fadeIn(2.0f));
		
		// setup the button style
		TextButtonStyle buttonStyle = new TextButtonStyle();
		Image buttonBackground = new Image(menuAtlas.findRegion("button"));
		Image overBackground = new Image(menuAtlas.findRegion("button-over"));
				
		buttonStyle.font = new BitmapFont(Gdx.files.internal("data/fonts/space-frigate-36.fnt"));
		
		buttonStyle.fontColor = Color.WHITE;
		buttonStyle.overFontColor = Color.WHITE;
		buttonStyle.checkedFontColor = Color.WHITE;
		buttonStyle.checkedOverFontColor = Color.WHITE;
		buttonStyle.disabledFontColor = Color.WHITE;
		buttonStyle.downFontColor = Color.WHITE;		
		// 99ffff		
		
		buttonStyle.up = buttonBackground.getDrawable();
		buttonStyle.over = overBackground.getDrawable();
		buttonStyle.checked = buttonBackground.getDrawable();
		buttonStyle.checkedOver = overBackground.getDrawable();
		buttonStyle.disabled = buttonBackground.getDrawable();
		buttonStyle.down = overBackground.getDrawable();
		
		// create the main menu
		mainMenu = new Menu();
		TextButton newGameBtn = new TextButton(Config.UI_NEW_GAME, buttonStyle);		
		TextButton loadGameBtn = new TextButton(Config.UI_LOAD_GAME, buttonStyle);
		TextButton exitGameBtn = new TextButton(Config.UI_EXIT_GAME, buttonStyle);
		
		newGameBtn.setName(Config.UI_NEW_GAME);
		loadGameBtn.setName(Config.UI_LOAD_GAME);
		exitGameBtn.setName(Config.UI_EXIT_GAME);
		
		newGameBtn.addListener(controller);
		loadGameBtn.addListener(controller);
		exitGameBtn.addListener(controller);
		
		newGameBtn.getLabel().setAlignment(Align.right);
		newGameBtn.padRight(12);
		
		loadGameBtn.getLabel().setAlignment(Align.right);
		loadGameBtn.padRight(12);
		
		exitGameBtn.getLabel().setAlignment(Align.right);
		exitGameBtn.padRight(12);
		
		mainMenu.addMenuItem(newGameBtn).spaceBottom(15);
		mainMenu.addMenuItem(loadGameBtn).spaceBottom(15);
		mainMenu.addMenuItem(exitGameBtn).spaceBottom(15);
		
		mainMenu.setWidth(buttonBackground.getWidth());
		mainMenu.setHeight((buttonBackground.getHeight() + 12) * 3);		
			
		this.stage.addActor(mainMenu);
		
		// create the profile menu
		profileMenu = new Menu();
		TextButton playBtn = new TextButton(Config.UI_PLAY, buttonStyle);		
		TextButton optionsBtn = new TextButton(Config.UI_OPTIONS, buttonStyle);
		TextButton exitProfileBtn = new TextButton(Config.UI_EXIT_PROFILE, buttonStyle);
		
		playBtn.setName(Config.UI_PLAY);
		optionsBtn.setName(Config.UI_OPTIONS);
		exitProfileBtn.setName(Config.UI_EXIT_PROFILE);
		
		playBtn.addListener(controller);
		optionsBtn.addListener(controller);
		exitProfileBtn.addListener(controller);
		
		playBtn.getLabel().setAlignment(Align.right);
		playBtn.padRight(12);
		
		optionsBtn.getLabel().setAlignment(Align.right);
		optionsBtn.padRight(12);
		
		exitProfileBtn.getLabel().setAlignment(Align.right);
		exitProfileBtn.padRight(12);
		
		profileMenu.addMenuItem(playBtn).spaceBottom(15);
		profileMenu.addMenuItem(optionsBtn).spaceBottom(15);
		profileMenu.addMenuItem(exitProfileBtn).spaceBottom(15);
		
		profileMenu.setWidth(buttonBackground.getWidth());
		profileMenu.setHeight((buttonBackground.getHeight() + 12) * 3);		
		
		this.stage.addActor(profileMenu);
		
		// create the new profile form
		newPlayerForm = new Table();
		Skin skin = new Skin(Gdx.files.internal("holo/Holo-dark-hdpi.json"));
		Label playerNameLbl = new Label("Name:", skin);
		playerNameInput = new TextField("", skin);
		newProfileError = new Label("", skin);
		newProfileError.setColor(Color.RED);
		Button newProfileOKBtn = new TextButton(Config.UI_NEW_PROFILE_OK, skin);
		Button newProfileCancelBtn = new TextButton(Config.UI_NEW_PROFILE_CANCEL, skin);
		
		newProfileOKBtn.setName(Config.UI_NEW_PROFILE_OK);
		newProfileCancelBtn.setName(Config.UI_NEW_PROFILE_CANCEL);
		
		newProfileOKBtn.addListener(controller);
		newProfileCancelBtn.addListener(controller);
		
		newPlayerForm.pad(25, 25, 25, 25);
		newPlayerForm.setFillParent(true);
		newPlayerForm.add(newProfileError).colspan(2).expandX();
		newPlayerForm.row();
		newPlayerForm.add(playerNameLbl);
		newPlayerForm.add(playerNameInput).expandX();
		newPlayerForm.row();
		newPlayerForm.add(newProfileCancelBtn);
		newPlayerForm.add(newProfileOKBtn);
		//newPlayerForm.debug();
		
		this.newProfileDialogue = new NewProfileDialogue("Create New Profile", skin);
		
		// create the select profile form
		chooseProfile = new Table();
		profileList = new List<String>(skin);
		profileScrollPane = new ScrollPane(profileList, skin);
		profileError = new Label("No profiles found!", skin);
		
		loadProfileBtn = new TextButton("Load", skin);
		loadProfileBtn.setName(Config.UI_PROFILE_LOAD);
		loadProfileBtn.addListener(controller);
		
		cancelProfileBtn = new TextButton("Cancel", skin);		
		cancelProfileBtn.setName(Config.UI_PROFILE_CANCEL);
		cancelProfileBtn.addListener(controller);
				
		this.stage.addActor(chooseProfile);		
		
		// create the options menu
		
		// exit dialogue
		this.exitDialogue = new ExitDialogue("Exit Stargem", skin);
		
		// set starting locations
		this.setStartingLocations();
		
		// show the main menu
		this.transitionInMainMenu();
	}
	
	
	
	@Override
	public void hide() {
		AudioManager.getInstance().stopMusic();
	}
	
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void dispose() {		
		GameManager.getInstance().removeInputProcessor(this.stage);		
		this.stage.dispose();
	}

	private void setStartingLocations() {
		// starting location of logo
		logoImage.setPosition(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - logoImage.getHeight() - 50);
		
		// main menu
		mainMenu.setPosition(Gdx.graphics.getWidth(), 160);
		
		// profile menu
		profileMenu.setPosition(Gdx.graphics.getWidth(), 160);
		
		// new profile form
		
		// choose profile form
		chooseProfile.setPosition(Gdx.graphics.getWidth(), 50);
		
		// options menu
	}
	
	public void transitionInMainMenu() {		
		mainMenu.addAction(sequence(delay(1f), moveBy(-(mainMenu.getWidth() + 15), 0, 0.2f)));
		logoImage.addAction(sequence(delay(0.5f), moveBy(-logoImage.getWidth(), 0, 0.2f)));
	}
	
	public void transitionOutMainMenu() {
		mainMenu.addAction(sequence(delay(0.5f), moveBy(mainMenu.getWidth() + 15, 0, 0.2f)));
		logoImage.addAction(sequence(delay(0.5f), moveBy(logoImage.getWidth(), 0, 0.2f)));
	}

	public void transitionInProfileMenu() {		
		profileMenu.addAction(sequence(delay(1f), moveBy(-(profileMenu.getWidth() + 15), 0, 0.2f)));
		logoImage.addAction(sequence(delay(0.5f), moveBy(-logoImage.getWidth(), 0, 0.2f)));
	}
	
	public void transitionOutProfileMenu() {
		profileMenu.addAction(sequence(delay(0.5f), moveBy(profileMenu.getWidth() + 15, 0, 0.2f)));
		logoImage.addAction(sequence(delay(0.5f), moveBy(logoImage.getWidth(), 0, 0.2f)));
	}
	
	public void transitionOutProfileMenuAndPlay() {
		logoImage.addAction(sequence(delay(0.5f), moveBy(logoImage.getWidth(), 0, 0.2f)));
		profileMenu.addAction(sequence(delay(1f), moveBy(profileMenu.getWidth() + 15, 0, 0.2f), new Action() {
			@Override
			public boolean act(float delta) {
				GameManager.getInstance().loadGame();
				return true;
			}
		}));
	}
	
	public void transitionInChooseProfile() {
		
		logoImage.addAction(sequence(delay(0.5f), moveBy(-logoImage.getWidth(), 0, 0.2f)));
		
		// populate the list of profiles
		profileList.getItems().clear();
		chooseProfile.clear();
		String path = Config.PROFILE_DATABASE_PATH;
		FileHandle[] directoryContents = Gdx.files.internal(path).list();
		if(directoryContents.length == 0) {
			chooseProfile.add(profileError);
		}
		else {			
			for(FileHandle file : directoryContents) {									
				String databaseName = file.nameWithoutExtension();
				String profileName = PersistenceManager.getInstance().getProfilePersistence().getProfileName(databaseName);
				profileList.getItems().add(profileName);				
			}
			chooseProfile.add(profileScrollPane).expand().fill().colspan(2).left().bottom().pad(0);
		}
		chooseProfile.row();
		chooseProfile.add(cancelProfileBtn).expandX();
		chooseProfile.add(loadProfileBtn).expandX();
		chooseProfile.setSize(430, 400);
		chooseProfile.addAction(sequence(delay(1f), moveBy(-(chooseProfile.getWidth() + 15), 0, 0.2f)));
		
	}

	public void transitionOutChooseProfile() {
		logoImage.addAction(sequence(delay(0.5f), moveBy(logoImage.getWidth(), 0, 0.2f)));
		chooseProfile.addAction(sequence(delay(0.5f), moveBy(chooseProfile.getWidth() + 15, 0, 0.2f)));
	}
	
	
	public void transitionInOptionsMenu() {
	}
	
	public void transitionOutOptionsMenu() {
	}
		
	public void showExitDialogue() {
		this.exitDialogue.show(stage);
	}
	
	public void showNewProfileDialogue() {
		this.newProfileDialogue.show(stage);
	}
	
	public void hideNewProfileDialogue() {
		this.newProfileDialogue.hide();
	}
	
	public class ExitDialogue extends Dialog {

		public ExitDialogue(String title, Skin skin) {
			super(title, skin);			
			text("Exit Stargem now?");
			button("Yes", "Yes");
			button("No", "No");
		}
		
		@Override
		protected void result(Object o) {
			if(o.equals("Yes")) {
				Gdx.app.exit();
			}
		}		
	}
	
	protected class NewProfileDialogue extends Dialog {
		public NewProfileDialogue(String title, Skin skin) {
			super(title, skin);			
			this.getContentTable().add(newPlayerForm);
		}
		
		@Override
		public Dialog show(Stage stage) {
			super.show(stage);
			this.setWidth(400);
			return this;
		}
		
	}
}