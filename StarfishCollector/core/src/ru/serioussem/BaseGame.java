package ru.serioussem;

import com.badlogic.gdx.Game;
import ru.serioussem.screens.BaseScreen;

public abstract class BaseGame extends Game {
    private static BaseGame game;

    public BaseGame() {
        game = this;
    }

    public static void setActiveScreen(BaseScreen s) {
        game.setScreen(s);
    }
}