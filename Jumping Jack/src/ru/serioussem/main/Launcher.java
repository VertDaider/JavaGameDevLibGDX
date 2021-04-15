package ru.serioussem.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.serioussem.JumpingJackGame;

public class Launcher {
    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1200;
        config.height = 896;
        config.samples = 3;
        config.title = "Jumping Jack";
        new LwjglApplication(new JumpingJackGame(), config);
    }
}