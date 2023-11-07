package ru.serioussem.spacerocks.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.serioussem.spacerocks.SpaceGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1200;
        config.height = 900;
        config.samples = 3;
        config.title = "Space Rocks";
        new LwjglApplication(new SpaceGame(), config);
    }
}