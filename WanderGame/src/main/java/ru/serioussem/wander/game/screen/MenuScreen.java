package ru.serioussem.wander.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import ru.serioussem.gdx.base.actor.BaseActor;
import ru.serioussem.gdx.base.game.BaseGame;
import ru.serioussem.gdx.base.screen.BaseScreen;
import ru.serioussem.wander.game.WanderGame;

public class MenuScreen extends BaseScreen {
    @Override
    public void initialize() {
        BaseActor map = new BaseActor(0, 0, mainStage);
        map.loadTexture("assets/map.jpg");
        map.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        TextButton startLevelButton = new TextButton("Start game", BaseGame.textButtonStyle);
        uiStage.addActor(startLevelButton);
        TextButton quitButton = new TextButton("Quit", BaseGame.textButtonStyle);
        uiStage.addActor(quitButton);

        startLevelButton.addListener(
                (Event e) ->
                {
                    InputEvent ie = (InputEvent) e;
                    if (ie.getType().equals(InputEvent.Type.touchDown)) {
                        WanderGame.setActiveScreen(new GameScreen());
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

        uiTable.row();
        uiTable.add(startLevelButton);
        uiTable.add(quitButton);
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void resize(int width, int height) {

    }

    public boolean keyDown(int keyCode) {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        return false;
    }
}
