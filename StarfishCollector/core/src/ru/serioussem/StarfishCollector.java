package ru.serioussem;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.serioussem.actors.BaseActor;
import ru.serioussem.actors.Starfish;
import ru.serioussem.actors.Turtle;

public class StarfishCollector extends GameBeta {
	private Turtle turtle;
	private Starfish starfish;
	private BaseActor ocean;

	public void initialize() {
		ocean = new BaseActor(0, 0, mainStage);
		ocean.loadTexture("water.jpg");
		ocean.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		starfish = new Starfish(380,380,mainStage);
		turtle = new Turtle(20,20,mainStage);
	}

	public void update(float dt) {

	}
}
