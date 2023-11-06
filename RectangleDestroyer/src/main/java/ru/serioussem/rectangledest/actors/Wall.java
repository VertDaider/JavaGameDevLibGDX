package ru.serioussem.rectangledest.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.serioussem.gdx.base.actor.BaseActor;

public class Wall extends BaseActor {
    public Wall(float x, float y, float width, float height, Stage s) {
        super(x, y, s);
        loadTexture("assets/white-square.png");
        setSize(width, height);
        setColor(Color.SLATE);
        setBoundaryRectangle();
    }
}