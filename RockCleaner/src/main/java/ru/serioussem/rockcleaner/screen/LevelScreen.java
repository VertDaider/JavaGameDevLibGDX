package ru.serioussem.rockcleaner.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import ru.serioussem.gdx.base.actor.BaseActor;
import ru.serioussem.gdx.base.screen.BaseScreen;
import ru.serioussem.rockcleaner.actors.Rock;
import ru.serioussem.rockcleaner.actors.Turtle;
import ru.serioussem.rockcleaner.actors.Whirlpool;

public class LevelScreen extends BaseScreen {
    private Turtle turtle;
    private boolean win;

    @Override
    public void initialize() {
        BaseActor ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("assets/water.jpg");
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

    @Override
    public void update(float v) {
        for (BaseActor rockActor : BaseActor.getList(mainStage, Rock.class.getName())) {
            rockActor.preventOverlap(turtle);
            Rock rock = (Rock) rockActor;
            for (BaseActor whirlpoolActor : BaseActor.getList(mainStage, Whirlpool.class.getName())) {
                Whirlpool whirlpool = (Whirlpool) whirlpoolActor;
                if (rock.overlaps(whirlpool) && !rock.cleared) {
                    rock.cleared = true;
                    rock.clearActions();
                    rock.addAction(Actions.fadeOut(0.3f));
                    rock.addAction(Actions.after(Actions.removeActor()));
                }
            }
        }

        if (BaseActor.count(mainStage, Rock.class.getName()) == 0 && !win) {
            win = true;
            BaseActor youWinMessage = new BaseActor(0, 0, mainStage);
            youWinMessage.loadTexture("assets/you-win.png");
            youWinMessage.centerAtPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
            youWinMessage.setOpacity(0);
            youWinMessage.addAction(Actions.delay(1));
            youWinMessage.addAction(Actions.after(Actions.fadeIn(1)));
        }
    }
}
