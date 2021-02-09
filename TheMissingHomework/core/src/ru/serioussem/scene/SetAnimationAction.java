package ru.serioussem.scene;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Action;
import ru.serioussem.actors.BaseActor;

public class SetAnimationAction extends Action {
    protected Animation animationToDisplay;

    public SetAnimationAction(Animation a) {
        animationToDisplay = a;
    }

    @Override
    public boolean act(float delta) {
        BaseActor ba = (BaseActor) target;
        ba.setAnimation(animationToDisplay);
        return true;
    }
}
