package ru.serioussem.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Sky extends BaseActor {
    public Sky(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("assets/sky.png");
        setSpeed(25);
        setMotionAngle(180);
    }

    public void act(float dt) {
        super.act(dt);
        applyPhysics(dt);

        if (getX() + getWidth() < 0) {
            moveBy(2 * getWidth(), 0);
        }
    }
}
