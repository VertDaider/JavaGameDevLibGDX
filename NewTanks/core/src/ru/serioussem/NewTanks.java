package ru.serioussem;

import ru.serioussem.screens.MenuScreen;

public class NewTanks extends BaseGame {
    @Override
    public void create() {
        setActiveScreen(new MenuScreen());
    }
}