package ru.serioussem.rectangledest.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.serioussem.gdx.base.actor.BaseActor;

public class Paddle extends BaseActor {
    public Paddle(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("assets/paddle.png");
    }
}