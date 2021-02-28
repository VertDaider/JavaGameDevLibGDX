package ru.serioussem.screens;

import com.badlogic.gdx.Gdx;
import ru.serioussem.actors.*;

public class LevelScreen extends BaseScreen {
    Paddle paddle;
    Ball ball;

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
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (ball.isPaused()) {
            ball.setPaused(false);
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