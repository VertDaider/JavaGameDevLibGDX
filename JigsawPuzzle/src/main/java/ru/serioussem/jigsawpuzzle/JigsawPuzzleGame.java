package ru.serioussem.jigsawpuzzle;

import ru.serioussem.gdx.base.game.BaseGame;
import ru.serioussem.jigsawpuzzle.screens.LevelScreen;

public class JigsawPuzzleGame extends BaseGame {
    public void create() {
        super.create();
        setActiveScreen(new LevelScreen());
    }
}