package ru.serioussem.mazerunman.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.serioussem.gdx.base.actor.BaseActor;

public class Wall extends BaseActor {
    public Wall(float x ,float y, float w,  float h, Stage s) {
        super(x, y, s);
        loadTexture("assets/square.jpg");
        setSize(w, h);
        setBoundaryRectangle();
    }
}
