package ru.serioussem.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import ru.serioussem.BaseGame;
import ru.serioussem.HomeworkGame;
import ru.serioussem.actors.BaseActor;

public class MenuScreen extends BaseScreen{
    @Override
    public void initialize() {
        BaseActor background = new BaseActor(0,0,mainStage);
        background.loadTexture("notebook.jpg");
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        BaseActor title = new BaseActor(0,0,mainStage);
        title.loadTexture("missing-homework.png");

        TextButton startButton = new TextButton("Start", BaseGame.textButtonStyle);

        startButton.addListener(
                (Event e) ->
                {
                    InputEvent ie = (InputEvent) e;
                    if (ie.getType().equals(InputEvent.Type.touchDown)) {
                        BaseGame.setActiveScreen(new StoryScreen());
                    }
                    return false;
                }
        );

        TextButton quitButton = new TextButton("Quit", BaseGame.textButtonStyle);

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

        uiTable.add(title).colspan(2);
        uiTable.row();
        uiTable.add(startButton);
        uiTable.add(quitButton);
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public boolean keyDown(int keyCode) {
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            BaseGame.setActiveScreen(new StoryScreen());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        return false;
    }
}
