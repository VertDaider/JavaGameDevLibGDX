package ru.serioussem.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import ru.serioussem.BaseGame;
import ru.serioussem.StarfishGame;
import ru.serioussem.actors.*;

import java.util.ArrayList;

public class LevelScreen extends BaseScreen {
    private final int MAX_COUNT_OBJECTS = 40;
    private final int WORLD_HEIGHT = 1800;
    private final int WORLD_WIDTH = 2400;
    private final String classRock = "ru.serioussem.actors.Rock";
    private final String classStarfish = "ru.serioussem.actors.Starfish";

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

        ButtonStyle buttonStyle = new ButtonStyle();
        Texture buttonTex = new Texture(Gdx.files.internal("undo.png"));
        TextureRegion buttonRegion = new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable(buttonRegion);
        Button restartButton = new Button(buttonStyle);
        restartButton.setColor(Color.CYAN);
        restartButton.setPosition(1120, 820);
        uiStage.addActor(restartButton);

        turtle = new Turtle((float) WORLD_WIDTH / 2, (float) WORLD_HEIGHT / 2, mainStage);

        restartButton.addListener(
                (Event e) ->
                {
                    InputEvent ie = (InputEvent) e;
                    if (ie.getType().equals(Type.touchDown))
                        StarfishGame.setActiveScreen(new LevelScreen());
                    return false;
                }
        );

        createObjectsRandom();
//        createObjectsFromCoord();

        win = false;
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
        for (BaseActor rockActor : BaseActor.getList(mainStage, classRock)) {
            turtle.preventOverlap(rockActor);
//        the turtle is pushing the rock!
//        rock.preventOverlap(turtle);
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
            BaseActor continueMessage = new BaseActor(0, 0, uiStage);
            continueMessage.loadTexture("message-continue.png");
            continueMessage.centerAtPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
            continueMessage.setOpacity(0);
            continueMessage.addAction(Actions.delay(1));
            continueMessage.addAction(Actions.after(Actions.fadeIn(1)));
            if (Gdx.input.isKeyPressed(Input.Keys.C)) {
                StarfishGame.setActiveScreen(new LevelScreen2());
            }
        }

        starfishLabel.setText("Starfish left: " + BaseActor.count(mainStage, classStarfish));
    }

    @Override
    public void resize(int width, int height) {

    }
}