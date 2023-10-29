package ru.serioussem.starfish;

import ru.serioussem.gdx.base.game.BaseGame;
import ru.serioussem.starfish.screens.MenuScreen;

public class StarfishGame extends BaseGame {
    @Override
    public void create() {
        super.create();
        setActiveScreen(new MenuScreen());
    }
}