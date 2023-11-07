package ru.serioussem.jumpingjack;

import ru.serioussem.gdx.base.game.BaseGame;
import ru.serioussem.jumpingjack.screens.LevelScreen;

public class JumpingJackGame extends BaseGame {
    public void create() {
        super.create();
        setActiveScreen(new LevelScreen());
    }
}