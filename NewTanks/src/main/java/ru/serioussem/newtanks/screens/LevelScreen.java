package ru.serioussem.newtanks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import ru.serioussem.gdx.base.actor.BaseActor;
import ru.serioussem.gdx.base.screen.BaseScreen;
import ru.serioussem.newtanks.actors.Player;

public class LevelScreen extends BaseScreen {
    private Label label;
    private Player player;

    @Override
    public void initialize() {
        BaseActor back = new BaseActor(0, 0, mainStage);
        back.loadTexture("assets/background.jpg");
        back.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        BaseActor.setWorldBounds(back);

        player = new Player(100,100,mainStage);
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
