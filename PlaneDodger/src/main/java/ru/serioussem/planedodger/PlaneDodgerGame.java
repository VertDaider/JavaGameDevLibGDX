package ru.serioussem.planedodger;

import ru.serioussem.gdx.base.game.BaseGame;
import ru.serioussem.planedodger.screens.LevelScreen;

public class PlaneDodgerGame extends BaseGame {
    public void create() {
        super.create();
        setActiveScreen(new LevelScreen());
    }
}