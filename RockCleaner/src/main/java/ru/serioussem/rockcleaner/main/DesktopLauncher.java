package ru.serioussem.rockcleaner.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.serioussem.rockcleaner.RockCleaner;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1200;
        config.height = 900;
        config.samples = 3;
//		config.fullscreen = true;
        config.title = "Rock Cleaner";
        new LwjglApplication(new RockCleaner(), config);
    }
}
