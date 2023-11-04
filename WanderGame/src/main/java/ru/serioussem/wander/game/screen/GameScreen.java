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

import java.util.ArrayList;
import java.util.List;

// TODO: 25.10.2023 сделать модуль с Base классами
public class GameScreen extends BaseScreen {
    private static final int FINISH_CELL_POSITION = 50;
    private static final int countPlayers = 2;
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
        createObjectsFromTileMap(tma);

        cube = new Cube((float) Gdx.graphics.getWidth() / 3,(float) Gdx.graphics.getHeight() / 2, mainStage);

        currentPlayer.setDraggable(true);
        currentPlayer.setTargetPosition(50);

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
        checkWin();
    }

    private void createObjectsFromTileMap(TilemapActor tma) {
        ArrayList<MapObject> playersObj = tma.getRectangleList("player");
        for (int i = 0; i < countPlayers; i++) {
            MapProperties mp = playersObj.get(i).getProperties();

            String color = (String) mp.get("color");
            playerList.add(new Player((float) mp.get("x"), (float) mp.get("y"), mainStage, color));
        }
        playerList.get(0).setActive(true);
        currentPlayer = playerList.get(0);

        ArrayList<MapObject> mapObjects = tma.getRectangleList("cell");
        System.out.println("Objects count: "+mapObjects.size());
        for (MapObject mapObject : mapObjects) {
            MapProperties mp = mapObject.getProperties();
            int pos = (int) mp.get("position");
            int target = (int) mp.get("target");
            String type = (String) mp.get("type");
            new Cell((float) mp.get("x"), (float) mp.get("y"), pos, target, type, mainStage);
        }
    }

    private void checkTurn() {
//         TODO: 28.10.2023 доделать проверочку
//        player2.setDraggable(!player1.isDraggable());
    }

    private void checkWin() {
        for(Player player : getPlayerList()) {
//            if (player.isActive())
//                System.out.println(player.getX() + " ' " + player.getY());
            if (player.getCurrentPosition() == FINISH_CELL_POSITION) {
                messageLabel.setText("Game over");
                messageLabel.setVisible(true);
            }
        }
    }
}
