package ru.serioussem.planedodger.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import ru.serioussem.gdx.base.actor.BaseActor;
import ru.serioussem.gdx.base.game.BaseGame;
import ru.serioussem.gdx.base.screen.BaseScreen;
import ru.serioussem.planedodger.actors.*;

public class LevelScreen extends BaseScreen {
    Plane plane;
    float starTimer;
    float starSpawnInterval;
    int score;
    Label scoreLabel;
    float enemyTimer;
    float enemySpawnInterval;
    float enemySpeed;
    boolean gameOver;
    BaseActor gameOverMessage;
    Music backgroundMusic;
    Sound sparkleSound;
    Sound explosionSound;


    public void initialize() {
        new Sky(0, 0, mainStage);
        new Sky(Gdx.graphics.getWidth(), 0, mainStage);
        new Ground(0, 0, mainStage);
        new Ground(Gdx.graphics.getWidth(), 0, mainStage);

        plane = new Plane(100, 500, mainStage);
        BaseActor.setWorldBounds(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/Prelude-and-Action.mp3"));
        sparkleSound = Gdx.audio.newSound(Gdx.files.internal("assets/sparkle.mp3"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("assets/explosion.wav"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.30f);
        backgroundMusic.play();

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

        for (BaseActor star : BaseActor.getList(mainStage, Star.class.getName())) {
            if (plane.overlaps(star)) {
                star.remove();
                score++;
                scoreLabel.setText(Integer.toString(score));
                Sparkle sp = new Sparkle(0, 0, mainStage);
                sp.centerAtActor(star);
                sparkleSound.play();
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

        for (BaseActor enemy : BaseActor.getList(mainStage, Enemy.class.getName())) {
            if (plane.overlaps(enemy)) {
                Explosion ex = new Explosion(0, 0, mainStage);
                ex.centerAtActor(plane);
                ex.setScale(3);
                explosionSound.play();
                backgroundMusic.stop();

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