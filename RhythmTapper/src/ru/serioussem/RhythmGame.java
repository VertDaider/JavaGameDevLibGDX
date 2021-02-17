package ru.serioussem;

import ru.serioussem.screens.RhythmScreen;

public class RhythmGame extends BaseGame {

	@Override
	public void create () {
		super.create();
        	setActiveScreen( new RhythmScreen() );
	}

}