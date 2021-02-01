package ru.serioussem.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Spaceship extends BaseActor {
    private Thrusters thrusters;

    public Spaceship(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("spaceship.png");
        setBoundaryPolygon(8);

        setAcceleration(200);
        setMaxSpeed(100);
        setDeceleration(10);

        thrusters = new Thrusters(0, 0, s);
        addActor(thrusters);
        thrusters.setPosition(-thrusters.getWidth(), getHeight() / 2 - thrusters.getHeight() / 2);
    }

    @Override
    public void act(float dt) {
        super.act(dt);

        float degreesPerSecond = 120;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            rotateBy(degreesPerSecond * dt);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            rotateBy(-degreesPerSecond * dt);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            accelerateAtAngle(getRotation());
            thrusters.setVisible(true);
        } else {
            thrusters.setVisible(false);
        }

        applyPhysics(dt);
        wrapAroundWorld();
    }
}
