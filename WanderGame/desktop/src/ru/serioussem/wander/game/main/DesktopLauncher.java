package ru.serioussem.wander.game.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.serioussem.wander.game.WanderGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1400;
		config.height = 1000;
		new LwjglApplication(new WanderGame(), config);
	}
}
