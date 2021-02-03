package ru.serioussem.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import ru.serioussem.BaseGame;
import ru.serioussem.actors.BaseActor;

public class LevelScreen extends BaseScreen {
    private Label label;

    @Override
    public void initialize() {
        BaseActor back = new BaseActor(0, 0, mainStage);
        back.loadTexture("background.jpg");
        back.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void resize(int width, int height) {

    }
}
