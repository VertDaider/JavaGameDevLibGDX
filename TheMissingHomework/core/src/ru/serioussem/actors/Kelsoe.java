package ru.serioussem.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Kelsoe extends BaseActor {
    public Animation normal;
    public Animation sad;
    public Animation lookLeft;
    public Animation lookRight;

    public Kelsoe(float x, float y, Stage s) {
        super(x, y, s);
        normal = loadTexture("kelsoe-normal.png");
        sad = loadTexture("kelsoe-sad.png");
        lookLeft = loadTexture("kelsoe-look-left.png");
        lookRight = loadTexture("kelsoe-look-right.png");
    }
}
