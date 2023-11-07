package ru.serioussem.missinghomework;

import ru.serioussem.gdx.base.game.BaseGame;
import ru.serioussem.missinghomework.screens.MenuScreen;

public class HomeworkGame extends BaseGame {

	@Override
	public void create () {
		super.create();
		setActiveScreen(new MenuScreen());
	}
}