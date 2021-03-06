package ru.serioussem.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class DropTargetActor extends BaseActor {
    private boolean targetable;

    public DropTargetActor(float x, float y, Stage s) {
        super(x, y, s);
        targetable = true;
    }

    public boolean isTargetable() {
        return targetable;
    }

    public void setTargetable(boolean t) {
        targetable = t;
    }
}
