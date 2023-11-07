package ru.serioussem.jumpingjack.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.serioussem.gdx.base.actor.BaseActor;

public class Solid extends BaseActor {
    private boolean enabled;

    public Solid(float x, float y, float width, float height, Stage s) {
        super(x, y, s);
        setSize(width, height);
        setBoundaryRectangle();
        enabled = true;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}