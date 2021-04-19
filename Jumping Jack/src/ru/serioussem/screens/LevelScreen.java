package ru.serioussem.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import ru.serioussem.BaseGame;
import ru.serioussem.actors.*;

public class LevelScreen extends BaseScreen {
    Koala jack;
    boolean gameOver;
    int coins;
    float time;
    Label coinLabel;
    Table keyTable;
    Label timeLabel;
    Label messageLabel;

    public void initialize() {

        initTiles();

        gameOver = false;
        coins = 0;
        time = 60;
        coinLabel = new Label("Coins: " + coins, BaseGame.labelStyle);
        coinLabel.setColor(Color.GOLD);
        keyTable = new Table();
        timeLabel = new Label("Time: " + (int) time, BaseGame.labelStyle);
        timeLabel.setColor(Color.LIGHT_GRAY);
        messageLabel = new Label("Message", BaseGame.labelStyle);
        messageLabel.setVisible(false);

        uiTable.pad(20);
        uiTable.add(coinLabel);
        uiTable.add(keyTable).expandX();
        uiTable.add(timeLabel);
        uiTable.row();
        uiTable.add(messageLabel).colspan(3).expandY();
    }

    private void initTiles() {
        TilemapActor tma = new TilemapActor("assets/map.tmx", mainStage);

        for (MapObject obj : tma.getRectangleList("Solid")) {
            MapProperties props = obj.getProperties();
            new Solid((float) props.get("x"), (float) props.get("y"),
                    (float) props.get("width"), (float) props.get("height"), mainStage);
        }

        for (MapObject obj : tma.getTileList("Flag")) {
            MapProperties props = obj.getProperties();
            new Flag((float) props.get("x"), (float) props.get("y"), mainStage);
        }

        for (MapObject obj : tma.getTileList("Coin")) {
            MapProperties props = obj.getProperties();
            new Coin((float) props.get("x"), (float) props.get("y"), mainStage);
        }

        for (MapObject obj : tma.getTileList("Timer")) {
            MapProperties props = obj.getProperties();
            new Timer((float) props.get("x"), (float) props.get("y"), mainStage);
        }

        MapObject startPoint = tma.getRectangleList("Start").get(0);
        MapProperties startProps = startPoint.getProperties();
        jack = new Koala((float) startProps.get("x"), (float) startProps.get("y"), mainStage);
    }

    public void update(float dt) {

        if (gameOver) return;

        for (BaseActor flag : BaseActor.getList(mainStage, "ru.serioussem.actors.Flag")) {
            if (jack.overlaps(flag)) {
                messageLabel.setText("You Win!");
                messageLabel.setColor(Color.LIME);
                messageLabel.setVisible(true);
                jack.remove();
                gameOver = true;
            }
        }

        for (BaseActor coin : BaseActor.getList(mainStage, "ru.serioussem.actors.Coin")) {
            if (jack.overlaps(coin)) {
                coins++;
                coinLabel.setText("Coins " + coins);
                coin.remove();
            }
        }

        time -= dt;
        timeLabel.setText("Time: " + (int) time);

        for (BaseActor timer : BaseActor.getList(mainStage, "ru.serioussem.actors.Timer")) {
            if (jack.overlaps(timer)) {
                time += 20;
                timer.remove();
            }
        }

        if (time <= 0) {
            messageLabel.setText("Time Up - Game Over");
            messageLabel.setColor(Color.RED);
            messageLabel.setVisible(true);
            jack.remove();
            gameOver = true;
        }

        for (BaseActor actor : BaseActor.getList(mainStage, "ru.serioussem.actors.Solid")) {
            Solid solid = (Solid) actor;

            if (jack.overlaps(solid) && solid.isEnabled()) {
                Vector2 offset = jack.preventOverlap(solid);

                if (offset != null) {
                    //collided in X direction
                    if (Math.abs(offset.x) > Math.abs(offset.y)) jack.velocityVec.x = 0;
                    else //collided in Y direction
                        jack.velocityVec.y = 0;
                }
            }
        }
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.SPACE) {
            if (jack.isOnSolid()) jack.jump();
        }
        return false;
    }
}