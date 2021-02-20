package ru.serioussem.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

class Message extends BaseActor {
    public Animation perfect;
    public Animation great;
    public Animation good;
    public Animation almost;
    public Animation miss;

    public Message(float x, float y, Stage s) {
        super(x, y, s);
        perfect = loadTexture("assets/perfect.png");
        great = loadTexture("assets/great.png");
        good = loadTexture("assets/good.png");
        almost = loadTexture("assets/almost.png");
        miss = loadTexture("assets/miss.png");
    }

    public void pulseFade() {
        setOpacity(1);
        clearActions();
        Action pulseFade = Actions.sequence(
                Actions.scaleTo(1.1f, 1.1f, 0.05f),
                Actions.scaleTo(1.0f, 1.0f, 0.05f),
                Actions.delay(1), Actions.fadeOut(0.5f));
        addAction(pulseFade);
    }
}
