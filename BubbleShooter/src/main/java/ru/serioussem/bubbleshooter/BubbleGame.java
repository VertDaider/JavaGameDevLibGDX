package ru.serioussem.bubbleshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.serioussem.bubbleshooter.screens.ScreenManager;

public class BubbleGame extends Game {
	SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		ScreenManager.getInstance().init(this, batch);
		ScreenManager.getInstance().setScreen(ScreenManager.ScreenType.MENU);
	}

	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();
		getScreen().render(dt);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
