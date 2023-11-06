package ru.serioussem.rectangledest.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.serioussem.rectangledest.RectangleGame;

public class Launcher {
    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1280;
        config.height = 960;
        config.samples = 3;
        config.title = "Rectangle Destroyer";
        new LwjglApplication(new RectangleGame(), config);
    }
}