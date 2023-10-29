package ru.serioussem.starfish.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import ru.serioussem.gdx.base.actor.BaseActor;
import ru.serioussem.gdx.base.actor.TilemapActor;
import ru.serioussem.gdx.base.game.BaseGame;
import ru.serioussem.gdx.base.screen.BaseScreen;
import ru.serioussem.starfish.StarfishGame;
import ru.serioussem.starfish.actors.*;

import java.util.ArrayList;

public class LevelScreen extends BaseScreen {
    private final int MAX_COUNT_OBJECTS = 40;
    private final int WORLD_WIDTH = 2400;
    private final int WORLD_HEIGHT = 1800;
    private final String classRock = "ru.serioussem.starfish.actors.Rock";
    private final String classStarfish = "ru.serioussem.starfish.actors.Starfish";
    private final String classSign = "ru.serioussem.starfish.actors.Sign";

    private Label starfishLabel;
    private DialogBox dialogBox;
    private Turtle turtle;
    private boolean win;

    private float audioVolume;
    private float audioVolumeBegin;
    private Sound waterDrop;
    private Music instrumental;
    private Music oceanSurf;

    @Override
    public void initialize() {
        win = false;
        TilemapActor tma = new TilemapActor(1200,900,"assets/map.tmx", mainStage);

//        BaseActor ocean = new BaseActor(0, 0, mainStage);
//        ocean.loadTexture("assets/water-border.jpg");
//        ocean.setSize(WORLD_WIDTH, WORLD_HEIGHT);
//        BaseActor.setWorldBounds(ocean);

//        turtle = new Turtle((float) WORLD_WIDTH / 2, (float) WORLD_HEIGHT / 2, mainStage);

        starfishLabel = new Label("Starfish left: ", BaseGame.labelStyle);
        starfishLabel.setColor(Color.CYAN);

        ButtonStyle buttonStyle = new ButtonStyle();
        Texture buttonTex = new Texture(Gdx.files.internal("assets/undo.png"));
        TextureRegion buttonRegion = new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable(buttonRegion);

        Button restartButton = new Button(buttonStyle);
        restartButton.setColor(Color.CYAN);

        restartButton.addListener((Event e) -> {
            if (!isTouchDownEvent(e)) {
                return false;
            }
            instrumental.dispose();
            oceanSurf.dispose();
            StarfishGame.setActiveScreen(new LevelScreen());
            return true;
        });

        ButtonStyle buttonStyle2 = new ButtonStyle();
        Texture buttonTex2 = new Texture(Gdx.files.internal("assets/audio.png"));
        TextureRegion buttonRegion2 = new TextureRegion(buttonTex2);
        buttonStyle2.up = new TextureRegionDrawable(buttonRegion2);

        Button muteButton = new Button(buttonStyle2);
        muteButton.setColor(Color.CYAN);

        muteButton.addListener(
                (Event e) ->
                {
                    if (!isTouchDownEvent(e)) {
                        return false;
                    }
                    if (audioVolume != 0) {
                        audioVolume = 0;
                    } else {
                        audioVolume = audioVolumeBegin;
                    }
                    instrumental.setVolume(audioVolume);
                    oceanSurf.setVolume(audioVolume);
                    return true;
                }
        );

//        createObjectsRandom();
        createObjectsFromTileMap(tma);

        waterDrop = Gdx.audio.newSound(Gdx.files.internal("assets/Water_Drop.ogg"));
        instrumental = Gdx.audio.newMusic(Gdx.files.internal("assets/Master_of_the_Feast.ogg"));
        oceanSurf = Gdx.audio.newMusic(Gdx.files.internal("assets/Ocean_Waves.ogg"));

        audioVolume = 0.2f;
        audioVolumeBegin = 0.2f;
        instrumental.setLooping(true);
        instrumental.setVolume(audioVolume);
        instrumental.play();
        oceanSurf.setLooping(true);
        oceanSurf.setVolume(audioVolume);
        oceanSurf.play();

//        Sign sign1 = new Sign(100, 500, mainStage);
//        sign1.setText("West Starfish Bay");
//        Sign sign2 = new Sign(2000, 300, mainStage);
//        sign2.setText("East Starfish Bay");
//        Sign sign3 = new Sign(1200, 1600, mainStage);
//        sign3.setText("North Starfish Bay");

        dialogBox = new DialogBox(0, 0, uiStage);
        dialogBox.setBackgroundColor(Color.TAN);
        dialogBox.setFontColor(Color.BROWN);
        dialogBox.setDialogSize(1100, 100);
        dialogBox.setFontScale(0.8f);
        dialogBox.alignCenter();
        dialogBox.setVisible(false);

        uiTable.pad(10);
        uiTable.add(starfishLabel).top();
        uiTable.add().expandX().expandY();
        uiTable.add(restartButton).top();
        uiTable.add(muteButton).top();
        uiTable.row();
        uiTable.add(dialogBox).colspan(4);
    }

    @Override
    public void update(float dt) {
        checkCollision();
        checkWin();
        checkSign();

        starfishLabel.setText("Starfish left: " + BaseActor.count(mainStage, classStarfish));
    }

    private void checkSign() {
        for (BaseActor signActor : BaseActor.getList(mainStage, classSign)) {
            Sign sign = (Sign) signActor;
            turtle.preventOverlap(sign);
            boolean nearby = turtle.isWithinDistance(4, sign);

            if (nearby && !sign.isViewing()) {
                dialogBox.setText(sign.getText());
                dialogBox.setVisible(true);
                sign.setViewing(true);
            }

            if (sign.isViewing() && !nearby) {
                dialogBox.setText(" ");
                dialogBox.setVisible(false);
                sign.setViewing(false);
            }
        }
    }

    private void checkWin() {
        if (BaseActor.count(mainStage, classStarfish) == 0 && !win) {
            BaseActor continueMessage = new BaseActor(0, 0, uiStage);
            continueMessage.loadTexture("assets/message-continue.png");
            continueMessage.centerAtPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
            continueMessage.setOpacity(0);
            continueMessage.addAction(Actions.delay(1));
            continueMessage.addAction(Actions.after(Actions.fadeIn(1)));
            if (Gdx.input.isKeyPressed(Input.Keys.C)) {
                instrumental.dispose();
                oceanSurf.dispose();
                StarfishGame.setActiveScreen(new LevelScreen2());
            }
        }
    }

    private void checkCollision() {
        for (BaseActor rockActor : BaseActor.getList(mainStage, classRock)) {
            turtle.preventOverlap(rockActor);
        }

        for (BaseActor starfishActor : BaseActor.getList(mainStage, classStarfish)) {
            Starfish starfish = (Starfish) starfishActor;
            if (turtle.overlaps(starfish) && !starfish.collected) {
                starfish.collected = true;
                waterDrop.play(audioVolume);
                starfish.clearActions();
                starfish.addAction(Actions.fadeOut(1));
                starfish.addAction(Actions.after(Actions.removeActor()));

                Whirlpool whirl = new Whirlpool(0, 0, mainStage);
                whirl.centerAtActor(starfish);
                whirl.setOpacity(0.25f);
            }
        }
    }

    private void createObjectsFromTileMap(TilemapActor tma) {
        for (MapObject obj : tma.getTileList("Starfish")) {
            MapProperties props = obj.getProperties();
            new Starfish((float) props.get("x"), (float) props.get("y"), mainStage);
        }
        for (MapObject obj : tma.getTileList("Rock")) {
            MapProperties props = obj.getProperties();
            new Rock((float) props.get("x"), (float) props.get("y"), mainStage);
        }
        for (MapObject obj : tma.getTileList("Sign")) {
            MapProperties props = obj.getProperties();
            Sign s = new Sign((float) props.get("x"), (float) props.get("y"), mainStage);
            s.setText((String) props.get("message"));
        }

        MapObject startPoint = tma.getRectangleList("start").get(0);
        MapProperties props = startPoint.getProperties();
        turtle = new Turtle((float) props.get("x"), (float) props.get("y"), mainStage);
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
    public void resize(int width, int height) {

    }
}