/**
 * 
 */
package com.stargem.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.stargem.GameManager;
import com.stargem.audio.AudioManager;
import com.stargem.entity.Entity;
import com.stargem.entity.EntityManager;
import com.stargem.entity.components.Health;
import com.stargem.entity.components.Inventory;
import com.stargem.entity.components.Weapon;
import com.stargem.utils.AssetList;
import com.stargem.views.MenuView.ExitDialogue;


/**
 * HUDView.java
 *
 * @author 	Chris B
 * @date	6 May 2014
 * @version	1.0
 */
public class HUDView implements View {

	private final AssetManager assetManager;
	private final AssetList assetList;
	private final Stage stage;	
	private final Table root;
	
	// asset paths
	private final String crosshairPath = "data/screens/play/crosshair_1.png";
	private final String hudPath = "data/screens/play/hud.pack";
	
	// drawbales
	private Image crosshairImage;
	private HealthBar healthbar;
	private HealthBar heatbar;
	
	// the entity whose statistics are shown on the HUD
	private Entity entity;
	
	// inventory
	private final StringBuilder cores = new StringBuilder();
	private final StringBuilder specials = new StringBuilder();
	private final StringBuilder gems = new StringBuilder();
	private Label coresLabel;
	private Label specialsLabel;
	private Label gemsLabel;
	
	// fps display
	private final StringBuilder fps = new StringBuilder();
	private Label fpsLabel;
	private ExitDialogue exitDialog;
	
	// in game music
	private Music musicTrack;
	
	
	public HUDView(AssetManager assetManager){
		
		this.assetManager = assetManager;
		this.assetList = new AssetList(this.assetManager);
		this.stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		this.root = new Table();
				
		// add assets to the list so they can be loaded by the manager
		this.assetList.add(crosshairPath, Texture.class);
		this.assetList.add(hudPath, TextureAtlas.class);
		this.assetList.load();
	}
	
	@Override
	public void render(float delta) {
		EntityManager em = EntityManager.getInstance();
		Health health = em.getComponent(entity, Health.class);
		Weapon weapon = em.getComponent(entity, Weapon.class);
		
		this.healthbar.setHealth(health.currentHealth, health.maxHealth);
		this.heatbar.setHealth(weapon.currentHeat, weapon.maxHeat);
		
		Inventory inventory = em.getComponent(entity, Inventory.class);
		
		if(inventory != null) { 
		
			this.cores.setLength(0);
			this.cores.append("Cores: ");
			this.cores.append(inventory.cores);
			this.coresLabel.setText(cores);
			
			this.specials.setLength(0);
			this.specials.append("Specials: ");
			this.specials.append(inventory.specials);
			this.specialsLabel.setText(specials);
			
			this.gems.setLength(0);
			this.gems.append("Gems: ");
			this.gems.append(inventory.gems);
			this.gemsLabel.setText(gems);
		}
		
		this.fps.setLength(0);
		this.fps.append(Gdx.graphics.getFramesPerSecond());
		this.fpsLabel.setText(fps);
		
		this.stage.act(delta);		
		this.stage.draw();
		Table.drawDebug(stage);
	}

	@Override
	public void show() {
		
		// register as the game hud
		GameManager.getInstance().setHUD(this);
		
		// crosshair image
		crosshairImage = new Image(new TextureRegion(assetManager.get(crosshairPath, Texture.class)));
		crosshairImage.getColor().a = 1;	
		
		// fps label
		Skin skin = new Skin(Gdx.files.internal("holo/Holo-dark-hdpi.json"));
		this.fpsLabel = new Label(fps, skin);		
		
		// inventory
		this.coresLabel = new Label(cores, skin);
		this.specialsLabel = new Label(specials, skin);
		this.gemsLabel = new Label(gems, skin);
		
		// health bar
		TextureAtlas hudAtlas = assetManager.get(hudPath, TextureAtlas.class);
		Image background = new Image(hudAtlas.findRegion("bar-bg"));
		Image healthEmptyBar = new Image(hudAtlas.findRegion("unhealthy"));
		Image healthFullBar = new Image(hudAtlas.findRegion("healthy"));
		Image healthIcon = new Image(hudAtlas.findRegion("health-icon"));
		healthbar = new HealthBar(background, healthEmptyBar, healthFullBar, healthIcon);
		
		stage.addActor(healthbar);
		healthbar.setX(Gdx.graphics.getWidth() - background.getWidth() - 25);
		healthbar.setY(25);		
		
		// heat bar for weapon
		Image heatEmptyBar = new Image(hudAtlas.findRegion("cool"));
		Image heatFullBar = new Image(hudAtlas.findRegion("hot"));
		Image heatIcon = new Image(hudAtlas.findRegion("flame-icon"));
		heatbar = new HealthBar(background, heatEmptyBar, heatFullBar, heatIcon);
		
		stage.addActor(heatbar);
		heatbar.setX(Gdx.graphics.getWidth() - background.getWidth() - 25);
		heatbar.setY(35 + background.getHeight());	
		
		// always centered crosshair
		this.stage.addActor(crosshairImage);
		crosshairImage.setX((Gdx.graphics.getWidth() / 2) - (crosshairImage.getWidth() / 2));
		crosshairImage.setY((Gdx.graphics.getHeight() / 2) - (crosshairImage.getHeight() / 2));
		
		// table layout for the rest of the ui
		root.setFillParent(true);
		root.add().center().expand();
		root.row();
		root.add(coresLabel).bottom().left();
		root.row();
		root.add(specialsLabel).bottom().left();
		root.row();
		root.add(gemsLabel).bottom().left();
		root.row();
		root.add(fpsLabel).bottom().left();
		
		this.stage.addActor(root);
		
		if(musicTrack != null) {
			AudioManager.getInstance().playMusic(musicTrack);
		}
	}
	
	@Override
	public void hide() {
		GameManager.getInstance().setHUD(null);
		AudioManager.getInstance().stopMusic();
	}
	
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}
	
	@Override
	public void dispose() {
		this.stage.dispose();
		this.assetList.unload();
	}
	
	public void setShootingMode(boolean isShooting) {
		if(isShooting) {
			crosshairImage.getColor().a = 1;
		}
		else {
			crosshairImage.getColor().a = 0;
		}
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public void setGameMusic(Music musicTrack) {
		this.musicTrack = musicTrack;
	}
	
}