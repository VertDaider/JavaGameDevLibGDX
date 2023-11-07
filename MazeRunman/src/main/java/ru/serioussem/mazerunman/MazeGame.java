package ru.serioussem.mazerunman;

import ru.serioussem.gdx.base.game.BaseGame;
import ru.serioussem.mazerunman.screen.LevelScreen;

public class MazeGame extends BaseGame {
    public void create() {
        super.create();
        setActiveScreen(new LevelScreen());
    }
}
