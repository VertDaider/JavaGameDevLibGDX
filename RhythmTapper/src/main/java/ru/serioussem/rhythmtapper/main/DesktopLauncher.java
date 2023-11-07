package ru.serioussem.rhythmtapper.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.serioussem.rhythmtapper.RhythmGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 600;
        config.samples = 3;
        config.title = "Rhythm Tapper";
        new LwjglApplication(new RhythmGame(), config);
    }
}