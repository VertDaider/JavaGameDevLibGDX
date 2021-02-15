package ru.serioussem;

import ru.serioussem.screens.RhythmScreen;

public class RhythmTapper extends BaseGame {

	@Override
	public void create () {
		super.create();
        	setActiveScreen( new RhythmScreen() );
	}

}