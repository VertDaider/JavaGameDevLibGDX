package ru.serioussem.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Sign extends BaseActor {
    private String text;
    private boolean viewing;

    public Sign(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("sign.png");
        text = " ";
        viewing = false;
        setBoundaryPolygon(8);
    }

    public void setText(String t) {
        text = t;
    }

    public String getText() {
        return text;
    }

    public void setViewing(boolean v) {
        viewing = v;
    }

    public boolean isViewing() {
        return viewing;
    }
}