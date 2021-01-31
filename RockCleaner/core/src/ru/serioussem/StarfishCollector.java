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
    private Whirlpool whirlpool;

    public void initialize() {
        BaseActor ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("water.jpg");
        ocean.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        whirlpool= new Whirlpool(580, 180, mainStage);
//        starfish2 = new Starfish(220, 580, mainStage);
//        starfish3 = new Starfish(700, 420, mainStage);
//        starfish4 = new Starfish(600, 800, mainStage);
//        starfish5 = new Starfish(1000, 800, mainStage);
//        starfish6 = new Starfish(1100, 200, mainStage);
        turtle = new Turtle(20, 20, mainStage);
        rock = new Rock(200, 200, mainStage);
    }

    public void update(float dt) {
        rock.preventOverlap(turtle);
        clearedRock(whirlpool);

//            BaseActor youWinMessage = new BaseActor(0, 0, mainStage);
//            youWinMessage.loadTexture("you-win.png");
//            youWinMessage.centerAtPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
//            youWinMessage.setOpacity(0);
//            youWinMessage.addAction(Actions.delay(1));
//            youWinMessage.addAction(Actions.after(Actions.fadeIn(1)));


    }

    private void clearedRock(Whirlpool whirlpool) {
        if (rock.overlaps(whirlpool) && !rock.isCleared()) {
            rock.removeRock();
        }
    }

    private void collectedStarfish(Starfish starfish) {
        if (turtle.overlaps(starfish) && !starfish.isCollected()) {

        }
    }
}