package ru.serioussem.jumpingjack.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.serioussem.gdx.base.actor.BaseActor;

public class Coin extends BaseActor {
    public Coin(float x, float y, Stage s) {
        super(x, y, s);
        loadAnimationFromSheet("assets/items/coin.png", 1, 6, 0.1f, true);
    }
}