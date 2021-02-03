package ru.serioussem.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import ru.serioussem.BaseGame;
import ru.serioussem.actors.*;

import java.util.ArrayList;

public class LevelScreen extends BaseScreen {
    private final int MAX_COUNT_OBJECTS = 40;
    private final int WORLD_HEIGHT = 1800;
    private final int WORLD_WIDTH = 2400;

    private Label starfishLabel;
    private Turtle turtle;
    private boolean win;

    @Override
    public void initialize() {
        BaseActor ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("water-border.jpg");
        ocean.setSize(WORLD_WIDTH, WORLD_HEIGHT);
        BaseActor.setWorldBounds(ocean);

        starfishLabel = new Label("Starfish left: ", BaseGame.labelStyle);
        starfishLabel.setColor(Color.CYAN);
        starfishLabel.setPosition(20, 830);
        uiStage.addActor(starfishLabel);

        createObjectsRandom();


// TODO: 02.02.2021 сделать проверку дублирования объектов в одном месте

//        createObjectsFromCoord();

        turtle = new Turtle((float) WORLD_WIDTH / 2, (float) WORLD_HEIGHT / 2, mainStage);
        win = false;
    }

    private void createObjectsRandom() {
        ArrayList<Rectangle> rectangles = new ArrayList<>();
        //создаем прямоугольник с рандом коорднатами, шириной 60 чтобы не выходил за край
        while (rectangles.size() != MAX_COUNT_OBJECTS) {
            int x = MathUtils.random(WORLD_WIDTH - 60);
            int y = MathUtils.random(WORLD_HEIGHT - 60);
            rectangles.add(new Rectangle(x, y, 60, 60));
            // удаляем если есть наложение
            for (int i = 0; i < rectangles.size() - 1; i++) {
                for (int j = i + 1; j < rectangles.size(); j++) {
                    if (rectangles.get(i).overlaps(rectangles.get(j))) {
                        rectangles.remove(i);
                    }
                }
            }
        }
        // создаем объекты по листу
        for (int i = 0; i < rectangles.size(); i++) {
            if (i < rectangles.size() / 2) {
                new Rock(rectangles.get(i).x, rectangles.get(i).y, mainStage);
            } else {
                new Starfish(rectangles.get(i).x, rectangles.get(i).y, mainStage);
            }
        }
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

        starfishLabel.setText("Starfish left: " + BaseActor.count(mainStage, "ru.serioussem.actors.Starfish"));
    }

    @Override
    public void resize(int width, int height) {

    }
}