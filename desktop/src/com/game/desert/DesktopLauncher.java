package com.game.desert;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;


public class DesktopLauncher {
	public static void main (String[] arg) {
		//Farklı platformlar için ekran ayarları.
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		Lwjgl3Graphics.Lwjgl3DisplayMode displayMode = (Lwjgl3Graphics.Lwjgl3DisplayMode) Lwjgl3ApplicationConfiguration.getDisplayMode();
		config.setFullscreenMode(displayMode);
		config.setForegroundFPS(60);
		config.setTitle("Desert Game");
		new Lwjgl3Application(new DesertGame(), config);
	}
}
