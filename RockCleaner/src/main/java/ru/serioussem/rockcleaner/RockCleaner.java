package ru.serioussem.rockcleaner;

import ru.serioussem.gdx.base.game.BaseGame;
import ru.serioussem.rockcleaner.screen.LevelScreen;

public class RockCleaner extends BaseGame {

    public void create() {
        super.create();
        setActiveScreen(new LevelScreen());
    }
}