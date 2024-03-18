package com.patricio.drop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.patricio.drop.DropGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("dropgame");
		config.setWindowedMode(800,400);
		config.useVsync(true);
		config.setForegroundFPS(60);
		new Lwjgl3Application(new DropGame(), config);
	}
}
