package ru.serioussem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import ru.serioussem.actors.*;

import java.util.ArrayList;

public class StarfishCollector extends GameBeta {
    private Turtle turtle;
    private boolean win;
    private ArrayList<BaseActor> starfishList;
    private ArrayList<BaseActor> rockList;

    public void initialize() {
        BaseActor ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("water.jpg");
        ocean.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        starfishList = new ArrayList<>();
        rockList = new ArrayList<>();
        starfishList.add(new Starfish(580, 180, mainStage));
        starfishList.add(new Starfish(220, 580, mainStage));
        starfishList.add(new Starfish(700, 420, mainStage));
        starfishList.add(new Starfish(600, 800, mainStage));
        starfishList.add(new Starfish(1000, 800, mainStage));
        starfishList.add(new Starfish(1100, 200, mainStage));

        rockList.add(new Rock(700, 650, mainStage));
        rockList.add(new Rock(100, 300, mainStage));
        rockList.add(new Rock(600, 450, mainStage));
        rockList.add(new Rock(800, 250, mainStage));

        turtle = new Turtle(20, 20, mainStage);
        win = false;
    }

    public void update(float dt) {
        for (BaseActor rockActor : rockList) {
            turtle.preventOverlap(rockActor);
//        the turtle is pushing the rock!
//        rock.preventOverlap(turtle);
        }

        for (int i = 0; i < starfishList.size(); i++) {
            Starfish starfish = (Starfish) starfishList.get(i);
            if (turtle.overlaps(starfish) && !starfish.collected) {
                starfish.collected = true;
                starfish.clearActions();
                starfish.addAction(Actions.fadeOut(1));
                starfish.addAction(Actions.after(Actions.removeActor()));
                starfishList.remove(starfish);

                Whirlpool whirl = new Whirlpool(0, 0, mainStage);
                whirl.centerAtActor(starfish);
                whirl.setOpacity(0.25f);
            }
        }

        if (starfishList.size() == 0 && !win) {
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