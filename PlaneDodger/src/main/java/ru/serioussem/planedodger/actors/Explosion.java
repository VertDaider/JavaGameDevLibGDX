package ru.serioussem.planedodger.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.serioussem.gdx.base.actor.BaseActor;

public class Explosion extends BaseActor {
    public Explosion(float x, float y, Stage s) {
        super(x, y, s);
        loadAnimationFromSheet("assets/explosion.png", 6, 6, 0.02f, false);
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        if (isAnimationFinished()) remove();
    }
}