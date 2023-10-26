package ru.serioussem.wander.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import ru.serioussem.gdx.base.actor.BaseActor;
import ru.serioussem.gdx.base.actor.TilemapActor;
import ru.serioussem.gdx.base.game.BaseGame;
import ru.serioussem.gdx.base.screen.BaseScreen;
import ru.serioussem.wander.game.WanderGame;
import ru.serioussem.wander.game.actor.Cell;
import ru.serioussem.wander.game.actor.Player;
import ru.serioussem.wander.game.constants.TypeCell;

import java.util.ArrayList;
import java.util.Iterator;

// TODO: 25.10.2023 сделать модуль с Base классами
public class GameScreen extends BaseScreen {
    Player player1;
    Player player2;
    private Label messageLabel;
    Cell finishCell;
    Cell tempCell;
//    boolean win;

    @Override
    public void initialize() {
//        win = false;

        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        Texture buttonTex = new Texture(Gdx.files.internal("assets/image/undo.png"));
        TextureRegion buttonRegion = new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable(buttonRegion);

        Button restartButton = new Button(buttonStyle);
        restartButton.setColor(Color.CYAN);

        restartButton.addListener((Event e) -> {
            if (!isTouchDownEvent(e)) {
                return false;
            }
//            instrumental.dispose();
//            oceanSurf.dispose();
            WanderGame.setActiveScreen(new MenuScreen());
            return true;
        });

        TilemapActor tma = new TilemapActor("assets/image/map.tmx", mainStage);
        createObjectsFromTileMap(tma);

        player1.setCell(finishCell);
        player1.setTargetPosition(50);

        messageLabel = new Label("...", BaseGame.labelStyle);
        messageLabel.setColor(Color.CYAN);


        uiTable.pad(10);
//        uiTable.add(starfishLabel).top();
        uiTable.add().expandX().expandY();
        uiTable.add(messageLabel).top().expandX().expandY().bottom().pad(50);
        messageLabel.setVisible(false);
        uiTable.add(restartButton).top();
    }

    @Override
    public void update(float dt) {
        checkCollision();
        checkWin();
    }

    private void createObjectsFromTileMap(TilemapActor tma) {
        ArrayList<MapObject> mapObjects = tma.getRectangleList("cell");
        System.out.println(mapObjects.size());
        for (MapObject mapObject : mapObjects) {
            MapProperties mp = mapObject.getProperties();
            int pos = (int) mp.get("position");
            int move = (int) mp.get("move");
            String type = (String) mp.get("type");
            if (pos == 1) {
                player1 = new Player((float) mp.get("x") - 40, (float) mp.get("y") + 40, mainStage, "red");
                float player1x = player1.getX();
                float player1y = player1.getY();
                player2 = new Player(player1x-50, player1y, mainStage, "green");
//                System.out.println(player1.getWidth());
            }
            // TODO: 24.10.2023 в карте добавить целевую ячейку для желтых

            if (type.equals("finish")) {
                finishCell = new Cell((float) mp.get("x"), (float) mp.get("y"), pos, 0, move, type, mainStage);
                finishCell.setSize(110, 64);
//                System.out.println(finishCell.getWidth());
            }
            else
                new Cell((float) mp.get("x"), (float) mp.get("y"), pos, 0, move, type, mainStage);
        }
    }

    private void checkCollision() {
        for (BaseActor cellActor: BaseActor.getList(mainStage, Cell.class.getName())) {
           player1.preventOverlap(cellActor);
        }
//        System.out.println(player1.getX() + " " + player1.getY());
//        System.out.println(player1.overlaps(testCell));
//        System.out.println(player1.getX());
//        System.out.println(finishCell.getX());
//        System.out.println(player1.overlaps(finishCell));
    }

    private void checkWin() {
        // TODO: 25.10.2023 тут переделать всё
        boolean win = false;
        win = player1.isCorrectTarget();
        if (win) {
            if (player1.overlaps(finishCell)) {
                messageLabel.setText("Player 1 win!");
                System.out.println("Player 1 win!");
                messageLabel.setVisible(true);
            }
            if (player2.overlaps(finishCell)) {
                messageLabel.setText("Player 2 win!");
                System.out.println("Player 2 win!");
                messageLabel.setVisible(true);
            }
        }
    }
}
