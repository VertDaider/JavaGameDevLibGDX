package ru.serioussem.wander.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.serioussem.gdx.base.actor.BaseActor;

public class Cube extends BaseActor {
    boolean isActive;
    Animation<TextureRegion> textureRegion;
    private int currentEdge;
    private float elapsedTime;

    public Cube(float x, float y, Stage s) {
        super(x, y, s);
        this.textureRegion = loadAnimationFromSheet("assets/image/cube.png", 6, 4, 0.005f, true);
        this.isActive = true;
    }

    @Override
    public void act(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && isActive()) {
            super.act(dt);
            elapsedTime += dt;
            setCurrentEdge((textureRegion.getKeyFrameIndex(elapsedTime) / 4) + 1);
        } else {
            if (getCurrentEdge() > 0)
                setActive(false);
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getCurrentEdge() {
        return currentEdge;
    }

    public void setCurrentEdge(int currentEdge) {
        this.currentEdge = currentEdge;
    }

}
