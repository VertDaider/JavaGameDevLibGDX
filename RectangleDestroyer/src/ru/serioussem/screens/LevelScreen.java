package ru.serioussem.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import ru.serioussem.BaseGame;
import ru.serioussem.actors.*;

public class LevelScreen extends BaseScreen {
    Paddle paddle;
    Ball ball;
    private static final String classWall = "ru.serioussem.actors.Wall";
    private static final String classBrick = "ru.serioussem.actors.Brick";
    private static final String classItem = "ru.serioussem.actors.Item";
    int score;
    int balls;
    Label scoreLabel;
    Label ballsLabel;
    Label messageLabel;
    boolean gameOver;

    public void initialize() {
        BaseActor background = new BaseActor(0, 0, mainStage);
        background.loadTexture("assets/space.png");
        BaseActor.setWorldBounds(background);
        paddle = new Paddle(520, 32, mainStage);

        new Wall(0, 0, 20, 900, mainStage); // left wall
        new Wall(1180, 0, 20, 900, mainStage); // right wall
        new Wall(0, 850, 1200, 50, mainStage); //top wall

        setUpGridBricks();

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
        for (BaseActor wall : BaseActor.getList(mainStage, classWall)) {
            if (ball.overlaps(wall)) {
                ball.bounceOff(wall);
            }
        }

        //bouncing off the bricks
        for (BaseActor brick : BaseActor.getList(mainStage, classBrick)) {
            Brick br = (Brick) brick;
            if (ball.overlaps(brick)) {
                ball.bounceOff(brick);
                br.setHp(br.getHp() - 1);
                br.setColor(Color.CORAL);
                if (br.getHp() == 0) {
                    brick.remove();
                    score += 1;
                    scoreLabel.setText("Score: " + score);

                    //items
                    float spawnProbability = 20;
                    if (MathUtils.random(0, 100) < spawnProbability) {
                        Item i = new Item(0, 0, mainStage);
                        i.centerAtActor(brick);
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
        }

        if (BaseActor.count(mainStage, classBrick) == 0) {
            messageLabel.setText("You win!");
            messageLabel.setColor(Color.LIME);
            messageLabel.setVisible(true);
            gameOver = true;
        }

        //UI
        if (ball.getY() < -50 && BaseActor.count(mainStage, classBrick) > 0) {
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
        for (BaseActor item : BaseActor.getList(mainStage, classItem)) {
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