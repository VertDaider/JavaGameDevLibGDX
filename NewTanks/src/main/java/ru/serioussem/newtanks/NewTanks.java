package ru.serioussem.newtanks;

import ru.serioussem.gdx.base.game.BaseGame;
import ru.serioussem.newtanks.screens.MenuScreen;

public class NewTanks extends BaseGame {
    @Override
    public void create() {
        super.create();
        setActiveScreen(new MenuScreen());
    }
}