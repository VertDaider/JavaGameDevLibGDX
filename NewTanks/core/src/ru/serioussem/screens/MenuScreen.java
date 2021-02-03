package ru.serioussem.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import ru.serioussem.BaseGame;
import ru.serioussem.NewTanks;
import ru.serioussem.actors.BaseActor;

public class MenuScreen extends BaseScreen {
    private Label label;

    @Override
    public void initialize() {
        BaseActor back = new BaseActor(0, 0, mainStage);
        back.loadTexture("background.jpg");
        back.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        label = new Label("New Tanks", BaseGame.labelStyle);
        label.setColor(Color.FOREST);
        label.setPosition(400, 550);
        uiStage.addActor(label);

//        BaseActor title = new BaseActor(0, 0, mainStage);
//        title.loadTexture("starfish-collector.png");
//        title.centerAtPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
//        title.moveBy(0, 100);
//
//        BaseActor start = new BaseActor(0, 0, mainStage);
//        start.loadTexture("message-start.png");
//        start.centerAtPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
//        start.moveBy(0, -100);
    }

    @Override
    public void update(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            NewTanks.setActiveScreen(new LevelScreen());
        }
    }

    @Override
    public void resize(int width, int height) {

    }
}