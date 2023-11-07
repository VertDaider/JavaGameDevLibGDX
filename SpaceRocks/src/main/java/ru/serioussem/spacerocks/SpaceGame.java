package ru.serioussem.spacerocks;

import ru.serioussem.gdx.base.game.BaseGame;
import ru.serioussem.spacerocks.screens.LevelScreen;

public class SpaceGame extends BaseGame {

    @Override
    public void create() {
        super.create();
        setActiveScreen(new LevelScreen());
    }
}