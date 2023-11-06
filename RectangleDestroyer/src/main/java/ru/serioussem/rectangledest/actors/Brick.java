package ru.serioussem.rectangledest.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.serioussem.gdx.base.actor.BaseActor;

public class Brick extends BaseActor {
    private int hp;

    public Brick(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("assets/brick-gray.png");
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public void setColor(int hp) {
        switch (hp) {
            case 1:
                this.setColor(Color.RED);
                break;
            case 2:
                this.setColor(Color.ORANGE);
                break;
            case 3:
                this.setColor(Color.YELLOW);
                break;
            case 4:
                this.setColor(Color.GREEN);
                break;
            case 5:
                this.setColor(Color.BLUE);
                break;
            case 6:
                this.setColor(Color.PURPLE);
                break;
            case 7:
                this.setColor(Color.WHITE);
                break;
            case 8:
                this.setColor(Color.GRAY);
                break;
        }
    }
}