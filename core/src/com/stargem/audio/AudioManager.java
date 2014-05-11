/**
 * 
 */
package com.stargem.audio;

import com.badlogic.gdx.audio.Music;
import com.stargem.Preferences;


/**
 * AudioManager.java
 *
 * @author 	Chris B
 * @date	5 Mar 2014
 * @version	1.0
 */
public class AudioManager {

	private Music ambientTrack;
	private Music currentMusic;
	private Music mixMusic;
	
	private final static AudioManager instance = new AudioManager();
	public static AudioManager getInstance() {
		return instance;
	}
	private AudioManager() {}
	
	/**
	 * Stop the current music and start the given music.
	 * 
	 * @param music
	 */
	public void playMusic(Music music) {
		
		if(!Preferences.PLAY_MUSIC) {
			return;
		}
		
		if(this.currentMusic != null) {
			this.currentMusic.stop();
		}
		this.currentMusic = music;
		this.currentMusic.setVolume(Preferences.MUSIC_VOLUME);
		this.currentMusic.setLooping(true);
		this.currentMusic.play();
	}
	
	/**
	 * Stop any music from playing. 
	 * This also interrupts and cancels any mixing operations
	 */
	public void stopMusic() {
		if(this.currentMusic != null) {
			this.currentMusic.stop();
		}
		if(this.mixMusic != null) {
			this.mixMusic.stop();
		}
		if(this.ambientTrack != null) {
			this.ambientTrack.stop();
		}
	}
	
	/**
	 * Fade out the current music whilst fading in the new music
	 * 
	 * @param music
	 */
	public void mixInMusic(Music music) {
		if(!Preferences.PLAY_MUSIC) {
			return;
		}
	}
	
}