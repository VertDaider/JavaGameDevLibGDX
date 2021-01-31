package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.units.PlayerTank;
import com.mygdx.game.utils.GameScore;
import com.mygdx.game.utils.GameType;

import java.io.IOException;

public class GameOverScreen extends AbstractScreen {
    private final SpriteBatch batch;
    private BitmapFont font24;
    private PlayerTank playerInfo;
    private final StringBuilder tmpString;
    private TextureAtlas atlas;
    private Stage stage;
    private Integer gameTime;
    private GameType gameType;

    // данные для статистики
    public void setPlayerInfo(PlayerTank playerInfo, Integer gameTime, GameType arg) {
        this.playerInfo = playerInfo;
        this.gameTime = gameTime;
        this.gameType = arg;
    }

    public GameOverScreen(SpriteBatch batch) {
        this.batch = batch;
        this.tmpString = new StringBuilder();
    }

    public boolean isHighScoreGame() throws IOException {
        return GameScore.isNewRecord(gameType, playerInfo.getScore());
    }

    @Override
    public void show() {
        font24 = new BitmapFont(Gdx.files.internal("font24.fnt"));
        atlas = new TextureAtlas("game.pack");
        stage = new Stage();

        Skin skin = new Skin();
        skin.add("simpleButton", new TextureRegion(atlas.findRegion("SimpleButton")));
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("simpleButton");
        textButtonStyle.font = font24;

        Group group = new Group();
        final TextButton restartButton = new TextButton("Restart", textButtonStyle);
        final TextButton exitButton = new TextButton("Exit", textButtonStyle);

        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().setScreen(ScreenManager.ScreenType.MENU);
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        restartButton.setPosition(0, 80);
        exitButton.setPosition(0, 0);
        group.addActor(restartButton);
        group.addActor(exitButton);
        group.setPosition(540, 100);
        stage.addActor(group);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        update();
        Gdx.gl.glClearColor(0, 0, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        tmpString.setLength(0);  // TODO: 01.05.2020 добавить рекорд времени и количесвто смертей в time mode
        tmpString.append("Frags: ").append(playerInfo.getScore());
        tmpString.append("\nTime: ").append(gameTime / 60).append(":").append(gameTime % 60);
        tmpString.append("\nShots: ").append(playerInfo.getCountShots());
        tmpString.append("\nAccuracy: ").append(Math.round(playerInfo.getCountHits() * 100 / playerInfo.getCountShots())).append("%");
        tmpString.append("\nTake damage: ").append(playerInfo.getTakeDamagePlayer());         // Получил урона
        tmpString.append("\nGive damage: ").append((int) playerInfo.getGiveDamage());   //Нанёс урон
        tmpString.append("\nPick up Shields: ").append(playerInfo.getCountShieldPickUp());
        tmpString.append("\nPick up Medkits: ").append(playerInfo.getCountMedkitPickUp());
        font24.draw(batch, tmpString, 400, 500);
        font24.draw(batch, "Game Over", 0, 600, 1280, 1, false);
        try {
            if (isHighScoreGame()) {
                font24.draw(batch, "NEW RECORD!", 0, 650, 1280, 1, false); // TODO: 11.05.2020 не работает почемуто
                GameScore.writeToFile(tmpString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        batch.end();
        stage.draw();
    }

    public void update() {
        stage.act();
    }

    @Override
    public void dispose() {
        atlas.dispose();
        font24.dispose();
        stage.dispose();
    }
}