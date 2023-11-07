package ru.serioussem.spacerocks.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.serioussem.gdx.base.actor.BaseActor;

public class Thrusters extends BaseActor {
    public Thrusters(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("assets/fire.png");
    }
}
