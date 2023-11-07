package ru.serioussem.rhythmtapper;

import ru.serioussem.gdx.base.game.BaseGame;
import ru.serioussem.rhythmtapper.screens.RecorderScreen;

public class RecorderGame extends BaseGame {
    public void create() {
        super.create();
        setActiveScreen(new RecorderScreen());
    }
}