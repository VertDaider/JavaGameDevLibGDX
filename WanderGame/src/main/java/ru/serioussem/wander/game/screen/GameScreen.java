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
import ru.serioussem.wander.game.BaseGame;
import ru.serioussem.wander.game.WanderGame;
import ru.serioussem.wander.game.actor.BaseActor;
import ru.serioussem.wander.game.actor.Cell;
import ru.serioussem.wander.game.actor.Player;
import ru.serioussem.wander.game.actor.TilemapActor;
import ru.serioussem.wander.game.constants.TypeCell;

import java.util.ArrayList;

public class GameScreen extends BaseScreen {
    Player player1;
    Player player2;
    private Label messageLabel;
    Cell finishCell;
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

        ArrayList<MapObject> calls = tma.getEllipseList("cell");
        for (MapObject mapObject : calls) {
            MapProperties mp = mapObject.getProperties();
            int pos = (int) mp.get("position");
            int move = (int) mp.get("move");
            String type = (String) mp.get("type");
            if (pos == 1) {
                player1 = new Player((float) mp.get("x"), (float) mp.get("y"), mainStage, "red");
                player1.setTargetPosition(50);
                player2 = new Player((float) mp.get("x") + 20, (float) mp.get("y") - 20, mainStage, "green");
            }
            // TODO: 24.10.2023 в карте добавить целевую ячейку для желтых
            // TODO: 24.10.2023  добавить ходы ячейки для красных
            if (type.equals("finish"))
                finishCell = new Cell((float) mp.get("x"), (float) mp.get("y"), pos, 0, move, type, mainStage);
            else
                new Cell((float) mp.get("x"), (float) mp.get("y"), pos, 0, move, type, mainStage);
        }
    }

    private void checkCollision() {
//        for (BaseActor cellActor: BaseActor.getList(mainStage, Cell.class.getName())) {
//            Cell cell = (Cell) cellActor;
//            if (player1.overlaps(cell)) {
//                player1.centerAtActor(finishCell);
//                System.out.println(cell.getPosition());
//            }
//        }
    }

    private void checkWin() {
        boolean win = true;
        for (BaseActor playerActor: BaseActor.getList(mainStage, Player.class.getName())) {
            Player player = (Player) playerActor;
            if (player.getCell() != null && player.getCell().getPosition() == finishCell.getPosition())
                win = true;
        }
        Cell finishCell = null;
        for (BaseActor cellActor: BaseActor.getList(mainStage, Cell.class.getName())) {
            Cell cell = (Cell) cellActor;
            if (cell.getType() == TypeCell.FINISH) {
                finishCell = cell;
                break;
            }
        }
        if (finishCell != null) {
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
