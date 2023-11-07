package ru.serioussem.jumpingjack.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.serioussem.gdx.base.actor.BaseActor;

public class Springboard extends BaseActor {
    public Springboard(float x, float y, Stage s) {
        super(x, y, s);
        loadAnimationFromSheet("assets/items/springboard.png", 1, 3, 0.2f, true);
    }
}