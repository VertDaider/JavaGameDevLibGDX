package ru.serioussem;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import ru.serioussem.actors.*;

public class StarfishCollector extends GameBeta {
    private Turtle turtle;
    private Rock rock;
    private Starfish starfish;
    private Starfish starfish2;
    private Starfish starfish3;
    private Starfish starfish4;
    private Starfish starfish5;
    private Starfish starfish6;

    public void initialize() {
        BaseActor ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("water.jpg");
        ocean.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        starfish = new Starfish(580, 180, mainStage);
        starfish2 = new Starfish(220, 580, mainStage);
        starfish3 = new Starfish(700, 420, mainStage);
        starfish4 = new Starfish(600, 800, mainStage);
        starfish5 = new Starfish(1000, 800, mainStage);
        starfish6 = new Starfish(1100, 200, mainStage);
        turtle = new Turtle(20, 20, mainStage);
        rock = new Rock(200, 200, mainStage);
    }

    public void update(float dt) {
//        turtle.preventOverlap(rock);
//        the turtle is pushing the rock!
        rock.preventOverlap(turtle);
        collectedStarfish(starfish);
        collectedStarfish(starfish2);
        collectedStarfish(starfish3);
        collectedStarfish(starfish4);
        collectedStarfish(starfish5);
        collectedStarfish(starfish6);

//            BaseActor youWinMessage = new BaseActor(0, 0, mainStage);
//            youWinMessage.loadTexture("you-win.png");
//            youWinMessage.centerAtPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
//            youWinMessage.setOpacity(0);
//            youWinMessage.addAction(Actions.delay(1));
//            youWinMessage.addAction(Actions.after(Actions.fadeIn(1)));


    }

    private void collectedStarfish(Starfish starfish) {
        if (turtle.overlaps(starfish) && !starfish.isCollected()) {
            starfish.collect();
            Whirlpool whirl = new Whirlpool(0, 0, mainStage);
            whirl.centerAtActor(starfish);
            whirl.setOpacity(0.25f);
        }
    }
}