package ru.serioussem;

public class RhythmTapper extends BaseGame {

	@Override
	public void create () {
		super.create();
        	setActiveScreen( new RhythmScreen() );
	}

}