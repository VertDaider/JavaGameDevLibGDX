package ru.serioussem.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Koala extends BaseActor {
    private Animation stand;
    private Animation walk;
    private Animation jump;
    private float jumpSpeed;
    private BaseActor belowSensor;
    private float walkAcceleration;
    private float walkDeceleration;
    private float maxHorizontalSpeed;
    private float gravity;
    private float maxVerticalSpeed;

    public Koala(float x, float y, Stage s) {
        super(x, y, s);

        stand = loadTexture("assets/koala/stand.png");
        jump = loadTexture("assets/koala/jump.png");

        String[] walkFileNames = {
                "assets/koala/walk-1.png",
                "assets/koala/walk-2.png",
                "assets/koala/walk-3.png",
                "assets/koala/walk-2.png"
        };

        walk = loadAnimationFromFiles(walkFileNames, 0.2f, true);

        maxHorizontalSpeed = 100;
        walkAcceleration = 200;
        walkDeceleration = 200;
        gravity = 700;
        maxVerticalSpeed = 1000;
        jumpSpeed = 450;

        setBoundaryPolygon(6);

        belowSensor = new BaseActor(0, 0, s);
        belowSensor.loadTexture("assets/white.png");
        belowSensor.setSize(this.getWidth() - 8, 8);
        belowSensor.setBoundaryRectangle();
        belowSensor.setVisible(false); //sensor for test available jumping
    }

    public boolean belowOverlaps(BaseActor actor) {
        return belowSensor.overlaps(actor);
    }

    public boolean isOnSolid() {
        for (BaseActor actor : BaseActor.getList(getStage(), "ru.serioussem.actors.Solid")) {
            Solid solid = (Solid) actor;
            if (belowOverlaps(solid) && solid.isEnabled()) return true;
        }
        return false;
    }

    public boolean isFalling() {
        return (velocityVec.y < 0);
    }

    public void spring() {
        velocityVec.y = 1.5f * jumpSpeed;
    }

    public void jump() {
        velocityVec.y = jumpSpeed;
    }

    public boolean isJumping() {
        return (velocityVec.y > 0);
    }

    @Override
    public void act(float dt) {
        super.act(dt);

        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            accelerationVec.add(-walkAcceleration, 0);
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            accelerationVec.add(walkAcceleration, 0);
        }

        accelerationVec.add(0, -gravity);
        velocityVec.add(accelerationVec.x * dt, accelerationVec.y * dt);

        if (!Gdx.input.isKeyPressed(Keys.RIGHT) && !Gdx.input.isKeyPressed(Keys.LEFT)) {
            float decelerationAmount = walkDeceleration * dt;
            float walkDirection;

            if (velocityVec.x > 0) walkDirection = 1;
            else walkDirection = -1;

            float walkSpeed = Math.abs(velocityVec.x);
            walkSpeed -= decelerationAmount;

            if (walkSpeed < 0) walkSpeed = 0;
            velocityVec.x = walkSpeed * walkDirection;
        }

        velocityVec.x = MathUtils.clamp(velocityVec.x, -maxHorizontalSpeed, maxHorizontalSpeed);
        velocityVec.y = MathUtils.clamp(velocityVec.y, -maxVerticalSpeed, maxVerticalSpeed);

        moveBy(velocityVec.x * dt, velocityVec.y * dt);
        accelerationVec.set(0, 0);

        belowSensor.setPosition(getX() + 4, getY() - 8);

        alignCamera();
        boundToWorld();

        if (this.isOnSolid()) {
            belowSensor.setColor(Color.GREEN);
            if (velocityVec.x == 0) setAnimation(stand);
            else setAnimation(walk);
        } else {
            belowSensor.setColor(Color.RED);
            setAnimation(jump);
        }

        if (velocityVec.x > 0) setScaleX(1);     //face right
        if (velocityVec.x < 0) setScaleX(-1);    //face left
    }
}