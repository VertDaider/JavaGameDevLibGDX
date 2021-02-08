package ru.serioussem.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Turret extends BaseActor {
    public Turret(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("turret.png");
    }

    public void act (float dt) {
        super.act(dt);
        applyPhysics(dt);
    }
}