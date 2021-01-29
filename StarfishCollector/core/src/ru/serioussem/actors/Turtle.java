package ru.serioussem.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Turtle extends BaseActor {
    public Turtle(float x, float y, Stage s) {
        super(x, y, s);

        String[] filenames = {
                "turtle-1.png",
                "turtle-2.png",
                "turtle-3.png",
                "turtle-4.png",
                "turtle-5.png",
                "turtle-6.png"};

        loadAnimationFromFiles(filenames, 0.1f, true);
    }
}
