package ru.serioussem.jigsawpuzzle.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.serioussem.jigsawpuzzle.JigsawPuzzleGame;

public class Launcher {
    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1200;
        config.height = 900;
        config.samples = 3;
        config.title = "Jigsaw Puzzle Game";
        new LwjglApplication(new JigsawPuzzleGame(), config);
    }
}