package ru.serioussem.starfish.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.serioussem.starfish.StarfishGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1600;
        config.height = 1200;
        config.samples = 3;
        config.title = "Starfish Collector";
        new LwjglApplication(new StarfishGame(), config);
    }
}