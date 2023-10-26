package ru.serioussem.cardpickup;

import ru.serioussem.cardpickup.screens.LevelScreen;
import ru.serioussem.gdx.base.game.BaseGame;

public class PickupGame extends BaseGame {
    public void create() {
        super.create();
        setActiveScreen(new LevelScreen());
    }
}