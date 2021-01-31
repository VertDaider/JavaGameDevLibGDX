package ru.serioussem;

import ru.serioussem.screens.MenuScreen;

public class StarfishGame extends BaseGame {
    @Override
    public void create() {
        setActiveScreen(new MenuScreen());
    }
}