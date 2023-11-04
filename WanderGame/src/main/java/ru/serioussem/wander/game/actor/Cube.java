package ru.serioussem.wander.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import ru.serioussem.gdx.base.actor.BaseActor;

public class Cube extends BaseActor {
    public Cube(float x, float y, Stage s) {
        super(x, y, s);
        loadAnimationFromSheet("assets/image/cube.png", 6, 4, 0.1f, true);
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        RepeatAction action = Actions.forever( Actions.rotateBy(90, 1) );
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            this.addAction( action );
        } else {
           this.removeAction(action);
        }
        applyPhysics(dt);
    }
}
