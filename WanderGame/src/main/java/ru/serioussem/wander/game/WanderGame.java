package ru.serioussem.wander.game;

import ru.serioussem.gdx.base.game.BaseGame;
import ru.serioussem.wander.game.screen.MenuScreen;

public class WanderGame extends BaseGame {
	@Override
	public void create() {
		super.create();
		setActiveScreen(new MenuScreen());
	}
}
