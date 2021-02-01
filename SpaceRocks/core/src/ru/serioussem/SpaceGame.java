package ru.serioussem;

import ru.serioussem.screens.LevelScreen;

public class SpaceGame extends BaseGame {
	
	@Override
	public void create () {
		super.create();
		setActiveScreen(new LevelScreen());
	}
}