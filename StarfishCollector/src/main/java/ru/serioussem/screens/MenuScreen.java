package ru.serioussem.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import ru.serioussem.BaseGame;
import ru.serioussem.StarfishGame;
import ru.serioussem.actors.BaseActor;

public class MenuScreen extends BaseScreen {
    @Override
    public void initialize() {
        BaseActor ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("assets/water.jpg");
        ocean.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        BaseActor title = new BaseActor(0, 0, mainStage);
        title.loadTexture("assets/starfish-collector.png");

        TextButton startLevelButton = new TextButton("Level 1", BaseGame.textButtonStyle);
        uiStage.addActor(startLevelButton);
        TextButton startLevelTwoButton = new TextButton("Level 2", BaseGame.textButtonStyle);
        uiStage.addActor(startLevelTwoButton);
        TextButton quitButton = new TextButton("Quit", BaseGame.textButtonStyle);
        uiStage.addActor(quitButton);

        startLevelButton.addListener(
                (Event e) ->
                {
                    InputEvent ie = (InputEvent) e;
                    if (ie.getType().equals(InputEvent.Type.touchDown)) {
                        StarfishGame.setActiveScreen(new StoryScreen());
                    }
                    return false;
                }
        );

        startLevelTwoButton.addListener(
                (Event e) ->
                {
                    InputEvent ie = (InputEvent) e;
                    if (ie.getType().equals(InputEvent.Type.touchDown)) {
                        StarfishGame.setActiveScreen(new LevelScreen2());
                    }
                    return false;
                }
        );

        quitButton.addListener(
                (Event e) ->
                {
                    InputEvent ie = (InputEvent) e;
                    if (ie.getType().equals(InputEvent.Type.touchDown)) {
                        Gdx.app.exit();
                    }
                    return false;
                }
        );

        uiTable.add(title).colspan(3);
        uiTable.row();
        uiTable.add(startLevelButton);
        uiTable.add(startLevelTwoButton);
        uiTable.add(quitButton);
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void resize(int width, int height) {

    }

    public boolean keyDown(int keyCode) {
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            StarfishGame.setActiveScreen(new LevelScreen());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        return false;
    }
}