package ru.serioussem.wander.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import ru.serioussem.wander.game.BaseGame;
import ru.serioussem.wander.game.WanderGame;
import ru.serioussem.wander.game.actor.BaseActor;

public class MenuScreen extends BaseScreen {
    @Override
    public void initialize() {
        BaseActor map = new BaseActor(0, 0, mainStage);
        map.loadTexture("core/assets/map.jpg");
        map.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

//        BaseActor title = new BaseActor(0, 0, mainStage);
//        title.loadTexture("assets/starfish-collector.png");

        TextButton startLevelButton = new TextButton("Start game", BaseGame.textButtonStyle);
        uiStage.addActor(startLevelButton);
//        TextButton startLevelTwoButton = new TextButton("Level 2", BaseGame.textButtonStyle);
//        uiStage.addActor(startLevelTwoButton);
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

//        uiTable.add(title).colspan(3);
        uiTable.row();
        uiTable.add(startLevelButton);
//        uiTable.add(startLevelTwoButton);
        uiTable.add(quitButton);
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void resize(int width, int height) {

    }

    public boolean keyDown(int keyCode) {
//        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
//            WanderGame.setActiveScreen(new LevelScreen());
//        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        return false;
    }
}
