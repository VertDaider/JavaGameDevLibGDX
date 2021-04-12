package ru.serioussem.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Rock extends BaseActor {
    public boolean cleared;

    public Rock(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("rock.png");
        setBoundaryPolygon(8);
        cleared = false;
    }

    public void act(float dt) {
        super.act(dt);
        applyPhysics(dt);
        boundToWorld();
    }
}
