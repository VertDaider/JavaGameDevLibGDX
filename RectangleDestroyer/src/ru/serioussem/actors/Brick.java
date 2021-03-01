package ru.serioussem.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Brick extends BaseActor {
    private int hp;

    public Brick(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("assets/brick-gray.png");
        hp = 2;
        setColor(Color.TAN);
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }
}