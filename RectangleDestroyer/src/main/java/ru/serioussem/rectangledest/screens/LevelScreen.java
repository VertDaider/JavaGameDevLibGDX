package ru.serioussem.rectangledest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import ru.serioussem.gdx.base.actor.BaseActor;
import ru.serioussem.gdx.base.actor.TilemapActor;
import ru.serioussem.gdx.base.game.BaseGame;
import ru.serioussem.gdx.base.screen.BaseScreen;
import ru.serioussem.rectangledest.actors.*;

public class LevelScreen extends BaseScreen {
    Paddle paddle;
    Ball ball;
    int score;
    int balls;
    Label scoreLabel;
    Label ballsLabel;
    Label messageLabel;
    boolean gameOver;

    Sound bounceSound;
    Sound brickBumpSound;
    Sound wallBumpSound;
    Sound itemAppearSound;
    Sound itemCollectSound;
    Music backgroundMusic;

    public void initialize() {
        setUpTileMap();

        ball = new Ball(0, 0, mainStage);
        gameOver = false;

        score = 0;
        balls = 3;
        scoreLabel = new Label("Score: " + score, BaseGame.labelStyle);
        ballsLabel = new Label("Balls: " + balls, BaseGame.labelStyle);
        messageLabel = new Label("click to start", BaseGame.labelStyle);
        messageLabel.setColor(Color.CYAN);

        uiTable.pad(5);
        uiTable.add(scoreLabel);
        uiTable.add().expandX();
        uiTable.add(ballsLabel);
        uiTable.row();
        uiTable.add(messageLabel).colspan(3).expandY();

        bounceSound = Gdx.audio.newSound(Gdx.files.internal("assets/boing.wav"));
        brickBumpSound = Gdx.audio.newSound(Gdx.files.internal("assets/bump.wav"));
        wallBumpSound = Gdx.audio.newSound(Gdx.files.internal("assets/bump-low.wav"));
        itemAppearSound = Gdx.audio.newSound(Gdx.files.internal("assets/swoosh.wav"));
        itemCollectSound = Gdx.audio.newSound(Gdx.files.internal("assets/pop.wav"));

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/Rollin-at-5.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.4f);
        backgroundMusic.play();
    }

    private void setUpTileMap() {

        TilemapActor tma = new TilemapActor(1280, 960, "assets/map.tmx", mainStage);

        for (MapObject obj : tma.getTileList("Wall")) {
            MapProperties props = obj.getProperties();
            new Wall((float) props.get("x"), (float) props.get("y"),
                    (float) props.get("width"), (float) props.get("height"), mainStage);
        }

        for (MapObject obj : tma.getTileList("Brick")) {
            MapProperties props = obj.getProperties();
            Brick b = new Brick((float) props.get("x"), (float) props.get("y"), mainStage);
            b.setSize((float) props.get("width"), (float) props.get("height"));
            b.setBoundaryRectangle();

            String colorName = (String) props.get("color");
            switch (colorName) {
                case "red":
                    b.setColor(Color.RED);
                    break;
                case "orange":
                    b.setColor(Color.ORANGE);
                    break;
                case "yellow":
                    b.setColor(Color.YELLOW);
                    break;
                case "green":
                    b.setColor(Color.GREEN);
                    break;
                case "blue":
                    b.setColor(Color.BLUE);
                    break;
                case "purple":
                    b.setColor(Color.PURPLE);
                    break;
                case "white":
                    b.setColor(Color.WHITE);
                    break;
                case "gray":
                    b.setColor(Color.GRAY);
                    break;
            }

            int hp = (int) props.get("hp");
            b.setHp(hp);
        }

        MapObject startPoint = tma.getRectangleList("start").get(0);
        MapProperties props = startPoint.getProperties();
        paddle = new Paddle((float) props.get("x"), (float) props.get("y"), mainStage);
    }

    public void update(float dt) {
        // plugging mouse
        float mouseX = Gdx.input.getX();
        paddle.setX(mouseX - paddle.getWidth() / 2);
        paddle.boundToWorld();

        //to lock ball above of the paddle
        if (ball.isPaused()) {
            ball.setX(paddle.getX() + paddle.getWidth() / 2 - ball.getWidth() / 2);
            ball.setY(paddle.getY() + paddle.getHeight() / 2 + ball.getHeight() / 2);
        }

        //bouncing off the walls
        for (BaseActor wall : BaseActor.getList(mainStage, Wall.class.getName())) {
            if (ball.overlaps(wall)) {
                ball.bounceOff(wall);
                wallBumpSound.play();
            }
        }

        //bouncing off the bricks
        for (BaseActor brick : BaseActor.getList(mainStage, Brick.class.getName())) {
            Brick br = (Brick) brick;
            if (ball.overlaps(brick)) {
                brickBumpSound.play();
                ball.bounceOff(brick);
                score += 1;
                scoreLabel.setText("Score: " + score);
                br.setHp(br.getHp() - 1);
                br.setColor(br.getHp());
                if (br.getHp() == 0) {
                    brick.remove();
                    score += 9;
                    scoreLabel.setText("Score: " + score);

                    //items
                    float spawnProbability = 15;
                    if (MathUtils.random(0, 100) < spawnProbability) {
                        Item i = new Item(0, 0, mainStage);
                        i.centerAtActor(brick);
                        itemAppearSound.play();
                    }
                }
            }
        }

        //bouncing off the paddle
        if (ball.overlaps(paddle)) {
            float ballCenterX = ball.getX() + ball.getWidth() / 2;
            float paddlePercentHit = (ballCenterX - paddle.getX()) / paddle.getWidth();
            float bounceAngle = MathUtils.lerp(150, 30, paddlePercentHit);
            ball.setMotionAngle(bounceAngle);
            bounceSound.play();
        }

        if (BaseActor.count(mainStage, Brick.class.getName()) == 0) {
            messageLabel.setText("You win!");
            messageLabel.setColor(Color.LIME);
            messageLabel.setVisible(true);
            gameOver = true;
        }

        //UI
        if (ball.getY() < -50 && BaseActor.count(mainStage, Brick.class.getName()) > 0) {
            ball.remove();
            if (balls > 0) {
                balls -= 1;
                ballsLabel.setText("Balls: " + balls);
                ball = new Ball(0, 0, mainStage);

                messageLabel.setText("Click to start");
                messageLabel.setColor(Color.CYAN);
            } else {
                messageLabel.setText("Game Over");
                messageLabel.setColor(Color.RED);
                gameOver = true;
            }
            messageLabel.setVisible(true);
        }

        //checkItems
        for (BaseActor item : BaseActor.getList(mainStage, Item.class.getName())) {
            if (paddle.overlaps(item)) {
                Item realItem = (Item) item;

                if (realItem.getType() == Item.Type.PADDLE_EXPAND)
                    paddle.setWidth(paddle.getWidth() * 1.25f);
                else if (realItem.getType() == Item.Type.PADDLE_SHRINK)
                    paddle.setWidth(paddle.getWidth() * 0.80f);
                else if (realItem.getType() == Item.Type.BALL_SPEED_UP)
                    ball.setSpeed(ball.getSpeed() * 1.50f);
                else if (realItem.getType() == Item.Type.BALL_SPEED_DOWN)
                    ball.setSpeed(ball.getSpeed() * 0.90f);

                paddle.setBoundaryRectangle();
                item.remove();
                itemCollectSound.play();
            }
        }

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (ball.isPaused()) {
            ball.setPaused(false);
            messageLabel.setVisible(false);
        }
        if (gameOver) {
            Gdx.app.exit();
        }
        return false;
    }

    private void setUpGridBricks() {
        Brick tempBrick = new Brick(0, 0, mainStage);
        float brickWidth = tempBrick.getWidth() + 3;
        float brickHeight = tempBrick.getHeight() + 3;
        tempBrick.remove();

        int totalRows = 16;
        int totalCols = 15;
        float marginX = (1200 - totalCols * brickWidth) / 2;
        float marginY = (900 - totalRows * brickHeight) - 120;

        for (int rowNum = 0; rowNum < totalRows; rowNum++) {
            for (int colNum = 0; colNum < totalCols; colNum++) {
                float x = marginX + brickWidth * colNum;
                float y = marginY + brickHeight * rowNum;
                new Brick(x, y, mainStage);
            }
        }
    }
}