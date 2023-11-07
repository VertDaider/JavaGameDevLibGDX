package ru.serioussem.rockcleaner.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.serioussem.gdx.base.actor.BaseActor;

public class Whirlpool extends BaseActor {
    public Whirlpool(float x, float y, Stage s) {
        super(x, y, s);
        loadAnimationFromSheet("assets/whirlpool.png", 2, 5, 0.1f, true);
        setBoundaryPolygon(8);
    }
}

