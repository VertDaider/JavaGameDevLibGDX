package ru.serioussem.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import ru.serioussem.BaseGame;
import ru.serioussem.StarfishGame;
import ru.serioussem.actors.*;

import java.util.ArrayList;

public class LevelScreen2 extends BaseScreen {
    private final int MAX_COUNT_OBJECTS = 50;
    private final int WORLD_HEIGHT = 1800;
    private final int WORLD_WIDTH = 2400;
    private final String classRock = "ru.serioussem.actors.Rock";
    private final String classStarfish = "ru.serioussem.actors.Starfish";
    private final String classShark = "ru.serioussem.actors.Shark";

    private Label starfishLabel;
    private Turtle turtle;
    private boolean win;
    private boolean gameOver;

    @Override
    public void initialize() {
        BaseActor ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("water-border.jpg");
        ocean.setSize(WORLD_WIDTH, WORLD_HEIGHT);
        BaseActor.setWorldBounds(ocean);
        win = false;
        gameOver = false;
        turtle = new Turtle((float) WORLD_WIDTH / 2, (float) WORLD_HEIGHT / 2, mainStage);

        starfishLabel = new Label("Starfish left: ", BaseGame.labelStyle);
        starfishLabel.setColor(Color.CYAN);

        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        Texture buttonTex = new Texture(Gdx.files.internal("undo.png"));
        TextureRegion buttonRegion = new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable(buttonRegion);
        Button restartButton = new Button(buttonStyle);
        restartButton.setColor(Color.CYAN);

        restartButton.addListener(
                (Event e) ->
                {
                    InputEvent ie = (InputEvent) e;
                    if (ie.getType().equals(InputEvent.Type.touchDown))
                        StarfishGame.setActiveScreen(new LevelScreen());
                    return false;
                }
        );

        createObjectsRandom();

        uiTable.pad(10);
        uiTable.add(starfishLabel).top();
        uiTable.add().expandX().expandY();
        uiTable.add(restartButton).top();
    }

    @Override
    public void update(float dt) {
        for (BaseActor rockActor : BaseActor.getList(mainStage, classRock)) {
            turtle.preventOverlap(rockActor);
        }

        for (BaseActor sharkActor : BaseActor.getList(mainStage, classShark)) {
            Shark shark = (Shark) sharkActor;
            if (turtle.overlaps(shark)) {
                gameOver = true;
                turtle.clearActions();
                turtle.addAction(Actions.fadeOut(1));
            }
        }

        for (BaseActor starfishActor : BaseActor.getList(mainStage, classStarfish)) {
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

        if (BaseActor.count(mainStage, classStarfish) == 0 && !win) {
            win = true;
            BaseActor youWinMessage = new BaseActor(0, 0, uiStage);
            youWinMessage.loadTexture("you-win.png");
            youWinMessage.centerAtPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
            youWinMessage.setOpacity(0);
            youWinMessage.addAction(Actions.delay(1));
            youWinMessage.addAction(Actions.after(Actions.fadeIn(1)));
        }

        if (gameOver) {
            BaseActor gameOverMessage = new BaseActor(0, 0, uiStage);
            gameOverMessage.loadTexture("game-over.png");
            gameOverMessage.centerAtPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
            gameOverMessage.setOpacity(0);
            gameOverMessage.addAction(Actions.delay(1));
            gameOverMessage.addAction(Actions.after(Actions.fadeIn(1)));
        }

        starfishLabel.setText("Starfish left: " + BaseActor.count(mainStage, classStarfish));
    }

    private void createObjectsRandom() {
        ArrayList<Rectangle> rectangles = new ArrayList<>();
        //создаем прямоугольник с рандом коорднатами, шириной 60 чтобы не выходил за край
        Rectangle positionTurtle = new Rectangle(turtle.getX(), turtle.getY(), turtle.getWidth(), turtle.getHeight());
        while (rectangles.size() != MAX_COUNT_OBJECTS) {
            int x = MathUtils.random(WORLD_WIDTH - 60);
            int y = MathUtils.random(WORLD_HEIGHT - 60);
            rectangles.add(new Rectangle(x, y, 100, 100));
            // удаляем если есть наложение
            for (int i = 0; i < rectangles.size() - 1; i++) {
                for (int j = i + 1; j < rectangles.size(); j++) {
                    if (rectangles.get(i).overlaps(rectangles.get(j)) || rectangles.get(i).overlaps(positionTurtle)) {
                        rectangles.remove(i);
                    }
                }
            }
        }
        // создаем объекты по листу
        int countShark = 10;
        int countStarAndRock = MAX_COUNT_OBJECTS - countShark;
        for (int i = 0; i < rectangles.size(); i++) {
            if (i < countStarAndRock / 2) {
                new Rock(rectangles.get(i).x, rectangles.get(i).y, mainStage);
            } else if (i > countStarAndRock / 2 && i <= countStarAndRock) {
                new Starfish(rectangles.get(i).x, rectangles.get(i).y, mainStage);
            } else {
                new Shark(rectangles.get(i).x, rectangles.get(i).y, mainStage);
            }
        }
    }
}