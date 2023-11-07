package ru.serioussem.rhythmtapper;

import ru.serioussem.gdx.base.game.BaseGame;
import ru.serioussem.rhythmtapper.screens.RhythmScreen;

public class RhythmGame extends BaseGame {

	@Override
	public void create () {
		super.create();
        	setActiveScreen( new RhythmScreen() );
	}

}