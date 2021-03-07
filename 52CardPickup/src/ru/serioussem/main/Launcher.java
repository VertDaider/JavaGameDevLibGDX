package ru.serioussem.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.serioussem.PickupGame;

public class Launcher {
    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1200;
        config.height = 900;
        config.samples = 3;
        config.title = "52 Card Pickup";
        new LwjglApplication(new PickupGame(), config);
    }
}