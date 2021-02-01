package ru.serioussem.screens;

import com.badlogic.gdx.Gdx;
import ru.serioussem.actors.BaseActor;
import ru.serioussem.actors.Spaceship;

public class LevelScreen extends BaseScreen{
    private Spaceship spaceship;

    @Override
    public void initialize() {
        BaseActor space = new BaseActor(0, 0,mainStage);
        space.loadTexture("space.png");
        space.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        BaseActor.setWorldBounds(space);

        spaceship = new Spaceship(400, 300, mainStage);
    }

    @Override
    public void update(float dt) {

    }
}