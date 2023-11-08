package ru.serioussem.mazerunman.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.serioussem.mazerunman.MazeGame;

public class Launcher {
    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1280;
        config.height = 1084;
        config.samples = 3;
        config.title = "Maze Runman";
        new LwjglApplication(new MazeGame(), config);
    }
}
