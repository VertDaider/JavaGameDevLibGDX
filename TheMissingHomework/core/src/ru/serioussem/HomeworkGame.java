package ru.serioussem;

import ru.serioussem.screens.MenuScreen;

public class HomeworkGame extends BaseGame {

	@Override
	public void create () {
		super.create();
		setActiveScreen(new MenuScreen());
	}
}