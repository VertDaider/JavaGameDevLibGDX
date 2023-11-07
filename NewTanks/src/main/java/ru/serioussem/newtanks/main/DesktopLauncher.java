package ru.serioussem.newtanks.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.serioussem.newtanks.NewTanks;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1440;
        config.height = 900;
        config.samples = 3;
        config.title = "New Tanks";
        new LwjglApplication(new NewTanks(), config);
    }
}
