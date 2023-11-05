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
import ru.serioussem.gdx.base.actor.TilemapActor;
import ru.serioussem.gdx.base.game.BaseGame;
import ru.serioussem.gdx.base.screen.BaseScreen;
import ru.serioussem.wander.game.WanderGame;
import ru.serioussem.wander.game.actor.Cell;
import ru.serioussem.wander.game.actor.Cube;
import ru.serioussem.wander.game.actor.Player;
import ru.serioussem.wander.game.constants.TypeCell;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends BaseScreen {
    private static final int FINISH_CELL_POSITION = 50;
    private static final int COUNT_PLAYERS = 2;
    int currentIndexPlayer;
    Player currentPlayer;
    Cube cube;
    List<Player> playerList;
    private Label messageLabel;

    @Override
    public void initialize() {

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

        TilemapActor tma = new TilemapActor(1400, 1000,"assets/image/map.tmx", mainStage);
        playerList = getPlayerList();
        currentIndexPlayer = 0;
        createObjectsFromTileMap(tma);

        messageLabel = new Label("...", BaseGame.labelStyle);
        messageLabel.setColor(Color.LIME);
        messageLabel.setVisible(false);

        uiTable.pad(10);
//        uiTable.add(starfishLabel).top();
        uiTable.add().expandX().expandY();
        uiTable.add(messageLabel).expand( Gdx.graphics.getWidth() / 2,Gdx.graphics.getHeight() / 2);
        uiTable.add(restartButton).top();
    }

    private List<Player> getPlayerList() {
        if (playerList == null)
            return new ArrayList<>();
        return playerList;
    }

    @Override
    public void update(float dt) {
        checkTurn();
        checkPostTurn();
    }

    private void createObjectsFromTileMap(TilemapActor tma) {
        ArrayList<MapObject> playersObj = tma.getRectangleList("player");
        for (int i = 0; i < COUNT_PLAYERS; i++) {
            MapProperties mp = playersObj.get(i).getProperties();
            playerList.add(new Player((float) mp.get("x"), (float) mp.get("y"), mainStage,  (String) mp.get("color")));
        }
        currentPlayer = playerList.get(currentIndexPlayer);
        currentPlayer.setDraggable(true);

        ArrayList<MapObject> cubeObj = tma.getRectangleList("cube");
        cube = new Cube((float) cubeObj.get(0).getProperties().get("x"),(float) cubeObj.get(0).getProperties().get("y"), mainStage);
//        cube.setActive(true);

        ArrayList<MapObject> mapObjects = tma.getRectangleList("cell");
//        System.out.println("Objects count: "+mapObjects.size());
        for (MapObject mapObject : mapObjects) {
            MapProperties mp = mapObject.getProperties();
            int pos = (int) mp.get("position");
            int target = (int) mp.get("target");
            String type = (String) mp.get("type");
            new Cell((float) mp.get("x"), (float) mp.get("y"), pos, target, type, mainStage);
        }
    }

    private void checkPostTurn() {
        if (currentPlayer.hasCell()) {
            Cell cell = currentPlayer.getCell();
            if (cell.getTarget()> 0) {
                currentPlayer.setDraggable(true);
                currentPlayer.setTargetPosition(cell.getTarget());
                messageLabel.setText(currentPlayer.getColorPlayer()+" player turn on task");
            } else if (cell.getType() == TypeCell.BLUE) {
                cube.setCurrentEdge(0);
                cube.setActive(true);
                currentPlayer = getNextPlayer();
                currentPlayer.setDraggable(true);

                // TODO: 05.11.2023 пропуск
            } else if (cell.getType() == TypeCell.GREEN) {
                currentPlayer.setDraggable(true);
                messageLabel.setText(currentPlayer.getColorPlayer()+" player turn again. Roll cube!");
                cube.setActive(true);
            } else {
                cube.setCurrentEdge(0);
                cube.setActive(true);
                currentPlayer = getNextPlayer();
                currentPlayer.setDraggable(true);
            }
            System.out.println(currentPlayer.getColorPlayer());
        }
    }

    private void checkTurn() {
        for(Player player : getPlayerList()) {
            if (player.isDraggable()) {
                if (cube.isActive()) {
                    messageLabel.setText(player.getColorPlayer()+" player turn cube");
                } else {
                    messageLabel.setText(player.getColorPlayer() + " player moves to "+cube.getCurrentEdge()+" squares");
                    player.setTargetPosition(player.getCurrentPosition()+ cube.getCurrentEdge());
                }
                messageLabel.setVisible(true);
            } else
            if (player.getCurrentPosition() == FINISH_CELL_POSITION) {
                messageLabel.setText("Game over");
                messageLabel.setVisible(true);
            }
        }
    }

    private Player getNextPlayer() {
        System.out.println(currentIndexPlayer);
        currentIndexPlayer++;
        System.out.println(currentIndexPlayer);
        if (currentIndexPlayer == playerList.size() - 1) {
            setCurrentIndexPlayer(0);
            return playerList.get(currentIndexPlayer);
        }
        else {
            return playerList.get(currentIndexPlayer + 1);
        }
    }

    public int getCurrentIndexPlayer() {
        return currentIndexPlayer;
    }

    public void setCurrentIndexPlayer(int currentIndexPlayer) {
        this.currentIndexPlayer = currentIndexPlayer;
    }
}
