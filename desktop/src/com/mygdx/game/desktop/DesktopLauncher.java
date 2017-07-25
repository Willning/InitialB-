package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import gameLogic.ObseleteGame;
import gameLogic.GameManager;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=720;
		config.height=480;
		config.backgroundFPS=30;
		config.foregroundFPS=40;
		new LwjglApplication(new GameManager(), config);
	}
}
