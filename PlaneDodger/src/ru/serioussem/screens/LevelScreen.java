package ru.serioussem.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import ru.serioussem.BaseGame;
import ru.serioussem.actors.*;

public class LevelScreen extends BaseScreen {
    Plane plane;
    float starTimer;
    float starSpawnInterval;
    int score;
    Label scoreLabel;
    private final String classStar = "ru.serioussem.actors.Star";
    private final String classEnemy = "ru.serioussem.actors.Enemy";
    float enemyTimer;
    float enemySpawnInterval;
    float enemySpeed;
    boolean gameOver;
    BaseActor gameOverMessage;

    public void initialize() {
        new Sky(0, 0, mainStage);
        new Sky(Gdx.graphics.getWidth(), 0, mainStage);
        new Ground(0, 0, mainStage);
        new Ground(Gdx.graphics.getWidth(), 0, mainStage);

        plane = new Plane(100, 500, mainStage);
        BaseActor.setWorldBounds(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        starTimer = 0;

        score = 0;
        scoreLabel = new Label(Integer.toString(score), BaseGame.labelStyle);
        uiTable.pad(10);
        uiTable.add(scoreLabel);
        uiTable.row();
        uiTable.add().expandY();

        enemyTimer = 0;
        enemySpeed = 100;
        enemySpawnInterval = 3;

        gameOver = false;
        gameOverMessage = new BaseActor(0, 0, mainStage);
        gameOverMessage.loadTexture("assets/game-over.png");
        gameOverMessage.setVisible(false);
        uiTable.add(gameOverMessage).expandY();

    }

    public void update(float dt) {
        if (gameOver) return;

        starUpdate(dt);

        enemyUpdate(dt);
    }

    private void starUpdate(float dt) {
        starTimer += dt;
        starSpawnInterval = MathUtils.random(2, 5);
        if (starTimer > starSpawnInterval) {
            new Star(Gdx.graphics.getWidth(), MathUtils.random(100, 800), mainStage);
            starTimer = 0;
        }

        for (BaseActor star : BaseActor.getList(mainStage, classStar)) {
            if (plane.overlaps(star)) {
                star.remove();
                score++;
                scoreLabel.setText(Integer.toString(score));
            }
        }
    }

    private void enemyUpdate(float dt) {
        enemyTimer += dt;
        if (enemyTimer > enemySpawnInterval) {
            Enemy enemy = new Enemy(Gdx.graphics.getWidth(), MathUtils.random(100, 800), mainStage);
            enemy.setSpeed(enemySpeed);

            enemyTimer = 0;
            enemySpawnInterval -= 0.1f;
            enemySpeed += 10;

            if (enemySpawnInterval < 0.5f) enemySpawnInterval = 0.5f;
            if (enemySpeed > 400) enemySpeed = 400;
        }

        for (BaseActor enemy : BaseActor.getList(mainStage, classEnemy)) {
            if (plane.overlaps(enemy)) {
                plane.remove();
                gameOver = true;
                gameOverMessage.setVisible(true);
            }

            if (enemy.getX() + enemy.getWidth() < 0) {
                score++;
                scoreLabel.setText(Integer.toString(score));
                enemy.remove();
            }
        }
    }

    public boolean keyDown(int keycode) {
        if (keycode == Keys.SPACE) {
            plane.boost();
        }
        return true;
    }
}