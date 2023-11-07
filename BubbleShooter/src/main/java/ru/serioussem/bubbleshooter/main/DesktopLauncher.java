package ru.serioussem.bubbleshooter.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.serioussem.bubbleshooter.BubbleGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 900;
		config.height = 900;
		config.title = "Bubble Shooter";
		config.samples=3;    //  antialiasing
		new LwjglApplication(new BubbleGame(), config);
	}
}
