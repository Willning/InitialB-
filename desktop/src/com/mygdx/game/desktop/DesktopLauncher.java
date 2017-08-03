package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import gameLogic.GameManager;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=720;
		config.height=480;
		config.backgroundFPS=30;
		config.foregroundFPS=30;
		config.resizable=false;
		new LwjglApplication(new GameManager(), config);
	}
}
