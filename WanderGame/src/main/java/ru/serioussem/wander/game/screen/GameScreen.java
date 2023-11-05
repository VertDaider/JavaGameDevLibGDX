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
    int activePlayerIndex;
    Player currentPlayer;
    Cube cube;
    List<Player> players;
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

        TilemapActor tma = new TilemapActor(1400, 1000, "assets/image/map.tmx", mainStage);
        players = getPlayers();
        activePlayerIndex = 0;
        createObjectsFromTileMap(tma);

        messageLabel = new Label("...", BaseGame.labelStyle);
        messageLabel.setColor(Color.LIME);
        messageLabel.setVisible(false);

        uiTable.pad(10);
//        uiTable.add(starfishLabel).top();
        uiTable.add().expandX().expandY();
        uiTable.add(messageLabel).expand(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        uiTable.add(restartButton).top();
    }

    private List<Player> getPlayers() {
        if (players == null)
            return new ArrayList<>();
        return players;
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
            players.add(new Player((float) mp.get("x"), (float) mp.get("y"), mainStage, (String) mp.get("color")));
        }
        currentPlayer = players.get(activePlayerIndex);
        currentPlayer.setDraggable(true);

        ArrayList<MapObject> cubeObj = tma.getRectangleList("cube");
        cube = new Cube((float) cubeObj.get(0).getProperties().get("x"), (float) cubeObj.get(0).getProperties().get("y"), mainStage);
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
            switch (cell.getType()) {
                case BLUE:
                    cube.setCurrentEdge(0);
                    cube.setActive(true);
                    currentPlayer.clearCell();
                    currentPlayer.setSkipNextMove(true);
                    currentPlayer = getActivePlayer();
                    currentPlayer.setDraggable(true);
                    break;
                case GREEN:
                    currentPlayer.setDraggable(true);
                    messageLabel.setText(currentPlayer.getColorPlayer() + " player turn again. Roll cube!");
                    cube.setCurrentEdge(0);
                    cube.setActive(true);
                    break;
                case WHITE:
                    cube.setCurrentEdge(0);
                    cube.setActive(true);
                    currentPlayer.clearCell();
                    currentPlayer = getActivePlayer();
                    if (currentPlayer.isSkipNextMove()) {
                        currentPlayer.setSkipNextMove(false);
                        currentPlayer = getActivePlayer();
                    }
                    currentPlayer.setDraggable(true);
                    break;
                default:
                    currentPlayer.setDraggable(true);
                    currentPlayer.setTargetPosition(cell.getTarget());
                    messageLabel.setText(currentPlayer.getColorPlayer() + " player turn on task");
                    break;
            }
        }
    }

    private void checkTurn() {
        for (Player player : getPlayers()) {
            if (player.isDraggable()) {
                if (cube.isActive()) {
                    messageLabel.setText(player.getColorPlayer() + " player roll cube");
                } else {
                    messageLabel.setText(player.getColorPlayer() + " player moves to " + cube.getCurrentEdge() + " squares");
                    player.setTargetPosition(Math.min(player.getCurrentPosition() + cube.getCurrentEdge(), FINISH_CELL_POSITION));
                    player.toFront();
                }
                messageLabel.setVisible(true);
            }
            if (player.getCurrentPosition() == FINISH_CELL_POSITION) {
                messageLabel.setText("Game over. Player " + player.getColorPlayer() + " win!");
                cube.setActive(false);
                messageLabel.setVisible(true);
            }
        }
    }

    public void switchToNextPlayer() {
        activePlayerIndex++;
        if (activePlayerIndex >= players.size()) {
            activePlayerIndex = 0;
        }
    }

    public Player getActivePlayer() {
        activePlayerIndex++;
        if (activePlayerIndex >= players.size()) {
            activePlayerIndex = 0;
        }
        return players.get(activePlayerIndex);
    }
}
