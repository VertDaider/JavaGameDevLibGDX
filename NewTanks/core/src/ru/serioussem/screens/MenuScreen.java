package ru.serioussem.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import ru.serioussem.NewTanks;
import ru.serioussem.actors.BaseActor;

public class MenuScreen extends BaseScreen {
    @Override
    public void initialize() {
        BaseActor ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("background.jpg");
        ocean.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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