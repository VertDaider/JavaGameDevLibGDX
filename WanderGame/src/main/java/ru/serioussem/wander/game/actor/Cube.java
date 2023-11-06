package ru.serioussem.wander.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.serioussem.gdx.base.actor.BaseActor;

public class Cube extends BaseActor {
    boolean isActive;
    Animation<TextureRegion> textureRegion;
    private int currentEdge;
    private float elapsedTime;
    private final Sound dice;
    private boolean isPlayedSound;

    public Cube(float x, float y, Stage s) {
        super(x, y, s);
        this.textureRegion = loadAnimationFromSheet("assets/image/cube.png", 6, 4, 0.005f, true);
        this.isActive = true;
        setSize(90f, 90f);
        dice = Gdx.audio.newSound(Gdx.files.internal("assets/sound/dice-roll-sound-2.ogg"));
    }

    @Override
    public void act(float dt) {
        if ((Gdx.input.isKeyPressed(Input.Keys.SPACE) || isClickOnCube()) && isActive()) {
            if (!isPlayedSound) {
                dice.play(0.4f);
                isPlayedSound = true;
            }
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

    public void reset() {
        setCurrentEdge(0);
        setActive(true);
        isPlayedSound = false;
    }

    private boolean isClickOnCube() {
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        return mouseX > this.getX() && mouseX < this.getX() + this.getHeight() && mouseY > this.getY() && mouseY < this.getY() + this.getWidth() && Gdx.input.isTouched(Input.Buttons.LEFT);
    }

}
