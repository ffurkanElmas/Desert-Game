package com.game.desert;



import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.desert.Screens.*;

public class DesertGame extends Game {
	//Ekranların nesnelerini oluşturup onlara bir sıra atıyoruz.
	private LoadingScreen loadingScreen;
	private PreferencesScreen preferencesScreen;
	private MenuScreen menuScreen;
	private PlayScreen playScreen;
	private PauseScreen pauseScreen;
	private AppPreferences preferences;
	//Müzik ve oyun skalası için gerekli değerler
	private Music music;
	public static AssetManager manager;
	//Ekran sıraları
	public final static int MENU = 0;
	public final static int PREFERENCES = 1;
	public final static int APPLICATION = 2;
	public static final int PAUSE = 3;
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final float PPM = 100;
	public SpriteBatch batch;



	@Override
	public void create() {
		//Oyun açılırken gelen ekran
		batch = new SpriteBatch();
		loadingScreen = new LoadingScreen(this);
		preferences = new AppPreferences();

		manager = new AssetManager();
		manager.load("Music/arkaplan_1.ogg", Music.class);
		manager.load("Music/yurume_sesi_1.ogg", Sound.class);
		manager.finishLoading();

		setScreen(loadingScreen);
	}
	public void changeScreen(int screen){
		//Ekran değişimlerini sağlayan method
		switch(screen){
			case MENU:
				if(menuScreen == null) menuScreen = new MenuScreen(this);
				this.setScreen(menuScreen);
				break;
			case PREFERENCES:
				if(preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
				this.setScreen(preferencesScreen);
				break;
			case APPLICATION:
				if(playScreen == null) playScreen = new PlayScreen(this);
				this.setScreen(playScreen);
				break;
			case PAUSE:
				if(pauseScreen == null) pauseScreen = new PauseScreen(this);
				this.setScreen(pauseScreen);
				break;
		}
	}
	public AppPreferences getPreferences(){
		//Ayarların kaydedilmesini sağlar.
		return this.preferences;
	}

	@Override
	public void render(){
		//Müziğin çalınması için gerekli ayarlar.
		if(getPreferences().isMusicEnabled()){
			music = DesertGame.manager.get("Music/arkaplan_1.ogg", Music.class);
			music.setLooping(true);
			music.setVolume(music.getVolume());
			music.play();
		}
		else{
			music = DesertGame.manager.get("Music/arkaplan_1.ogg", Music.class);
			music.setLooping(true);
			music.setVolume(music.getVolume());
			music.stop();
		}
		super.render();
		manager.update();
	}

	@Override
	public void dispose(){
		batch.dispose();
	}


}
