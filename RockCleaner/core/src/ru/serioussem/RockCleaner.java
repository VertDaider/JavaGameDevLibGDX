package ru.serioussem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import ru.serioussem.actors.*;

public class RockCleaner extends GameBeta {
    private Turtle turtle;
    private boolean win;

    public void initialize() {
        BaseActor ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("water.jpg");
        ocean.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        BaseActor.setWorldBounds(ocean);
        win = false;

        turtle = new Turtle(20, 20, mainStage);

        new Whirlpool(580, 80, mainStage);
        new Whirlpool(220, 580, mainStage);
        new Whirlpool(900, 420, mainStage);
        new Whirlpool(600, 800, mainStage);

        new Rock(200, 200, mainStage);
        new Rock(300, 700, mainStage);
        new Rock(700, 200, mainStage);
        new Rock(1000, 200, mainStage);
        new Rock(900, 800, mainStage);
        new Rock(550, 450, mainStage);
    }

    public void update(float dt) {
        for (BaseActor rockActor : BaseActor.getList(mainStage, "ru.serioussem.actors.Rock")) {
            rockActor.preventOverlap(turtle);
            Rock rock = (Rock) rockActor;
            for (BaseActor whirlpoolActor : BaseActor.getList(mainStage, "ru.serioussem.actors.Whirlpool")) {
                Whirlpool whirlpool = (Whirlpool) whirlpoolActor;
                if (rock.overlaps(whirlpool) && !rock.cleared) {
                    rock.cleared = true;
                    rock.clearActions();
                    rock.addAction(Actions.fadeOut(0.3f));
                    rock.addAction(Actions.after(Actions.removeActor()));
                }
            }
        }

        if (BaseActor.count(mainStage, "ru.serioussem.actors.Rock") == 0 && !win) {
            win = true;
            BaseActor youWinMessage = new BaseActor(0, 0, mainStage);
            youWinMessage.loadTexture("you-win.png");
            youWinMessage.centerAtPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
            youWinMessage.setOpacity(0);
            youWinMessage.addAction(Actions.delay(1));
            youWinMessage.addAction(Actions.after(Actions.fadeIn(1)));
        }
    }
}