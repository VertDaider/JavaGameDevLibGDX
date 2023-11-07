package ru.serioussem.tanks.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.serioussem.tanks.TanksRpgGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		config.samples=3;
		config.fullscreen = true;
		config.vSyncEnabled = true;
		new LwjglApplication(new TanksRpgGame(), config);
	}
}
