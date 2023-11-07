package ru.serioussem.missinghomework.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.serioussem.missinghomework.HomeworkGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 600;
        config.samples = 3;
        config.title = "The Missing Homework";
        new LwjglApplication(new HomeworkGame(), config);
    }
}
