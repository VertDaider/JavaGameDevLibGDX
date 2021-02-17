package ru.serioussem;

import ru.serioussem.screens.RecorderScreen;

public class RecorderGame extends BaseGame {
    public void create() {
        super.create();
        setActiveScreen(new RecorderScreen());
    }
}