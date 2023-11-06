package ru.serioussem.rectangledest;

import ru.serioussem.gdx.base.game.BaseGame;
import ru.serioussem.rectangledest.screens.LevelScreen;

public class RectangleGame extends BaseGame {
    public void create() {
        super.create();
        setActiveScreen(new LevelScreen());
    }
}