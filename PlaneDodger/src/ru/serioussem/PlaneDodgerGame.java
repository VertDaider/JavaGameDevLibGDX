package ru.serioussem;

import ru.serioussem.screens.LevelScreen;

public class PlaneDodgerGame extends BaseGame {
    public void create() {
        super.create();
        setActiveScreen(new LevelScreen());
    }
}