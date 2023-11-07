package ru.serioussem.newtanks.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.serioussem.gdx.base.actor.BaseActor;

public class Turret extends BaseActor {
    public Turret(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("assets/turret.png");
    }

    public void act (float dt) {
        super.act(dt);
        applyPhysics(dt);
    }
}