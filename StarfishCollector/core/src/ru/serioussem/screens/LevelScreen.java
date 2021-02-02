package ru.serioussem.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import ru.serioussem.actors.*;

public class LevelScreen extends BaseScreen {
    private Turtle turtle;
    private boolean win;

    @Override
    public void initialize() {
        BaseActor ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("water-border.jpg");
        ocean.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        BaseActor.setWorldBounds(ocean);

        int MAX_COUNT_STARFISH = 10;
        for (int i = 0; i < MAX_COUNT_STARFISH; i++) {
            int x = MathUtils.random(Gdx.graphics.getWidth() - 60);
            int y = MathUtils.random(Gdx.graphics.getHeight() - 60);
            createStarfish(x, y);
        }
        int MAX_COUNT_ROCKS = 6;
        for (int j = 0; j < MAX_COUNT_ROCKS; j++) {
            int x = MathUtils.random(Gdx.graphics.getWidth() - 60);
            int y = MathUtils.random(Gdx.graphics.getHeight() - 60);
            createRock(x, y);
        }
// TODO: 02.02.2021 сделать проверку дублирования объектов в одном месте

//        createObjectsFromCoord();

        turtle = new Turtle(20, 20, mainStage);
        win = false;
    }

    private void createRock(int random, int random1) {
        new Rock(random, random1, mainStage);
    }

    private void createStarfish(int random, int random1) {
        new Starfish(random, random1, mainStage);
    }

    private void createObjectsFromCoord() {
        new Starfish(1020, 580, mainStage);
        new Starfish(700, 420, mainStage);
        new Starfish(600, 800, mainStage);
        new Starfish(1000, 800, mainStage);
        new Starfish(1100, 270, mainStage);
        new Starfish(900, 240, mainStage);
        new Starfish(100, 250, mainStage);
        new Starfish(150, 530, mainStage);
        new Starfish(330, 630, mainStage);
        new Starfish(420, 510, mainStage);

        new Rock(700, 650, mainStage);
        new Rock(100, 300, mainStage);
        new Rock(600, 450, mainStage);
        new Rock(450, 250, mainStage);
        new Rock(400, 550, mainStage);
        new Rock(100, 650, mainStage);
        new Rock(900, 150, mainStage);
    }

    @Override
    public void update(float dt) {
        for (BaseActor rockActor : BaseActor.getList(mainStage, "ru.serioussem.actors.Rock")) {
            turtle.preventOverlap(rockActor);
//        the turtle is pushing the rock!
//        rock.preventOverlap(turtle);
        }

        for (BaseActor starfishActor : BaseActor.getList(mainStage, "ru.serioussem.actors.Starfish")) {
            Starfish starfish = (Starfish) starfishActor;
            if (turtle.overlaps(starfish) && !starfish.collected) {
                starfish.collected = true;
                starfish.clearActions();
                starfish.addAction(Actions.fadeOut(1));
                starfish.addAction(Actions.after(Actions.removeActor()));

                Whirlpool whirl = new Whirlpool(0, 0, mainStage);
                whirl.centerAtActor(starfish);
                whirl.setOpacity(0.25f);
            }
        }

        if (BaseActor.count(mainStage, "ru.serioussem.actors.Starfish") == 0 && !win) {
            win = true;
            BaseActor youWinMessage = new BaseActor(0, 0, uiStage);
            youWinMessage.loadTexture("you-win.png");
            youWinMessage.centerAtPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
            youWinMessage.setOpacity(0);
            youWinMessage.addAction(Actions.delay(1));
            youWinMessage.addAction(Actions.after(Actions.fadeIn(1)));
        }
    }

    @Override
    public void resize(int width, int height) {

    }
}