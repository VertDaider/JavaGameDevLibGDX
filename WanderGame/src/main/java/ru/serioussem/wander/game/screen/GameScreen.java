package ru.serioussem.wander.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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

public class GameScreen extends BaseScreen {
    private static final int FINISH_CELL_POSITION = 50;
    private static final int COUNT_PLAYERS = 3;
    int activePlayerIndex;
    private Label cellLabel;
    Player currentPlayer;
    Cube cube;
    List<Player> players;
    private Label messageLabel;

    private float audioVolume;
    private float audioVolumeBegin;
    private Music backgroundMusic;

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
            backgroundMusic.dispose();
            WanderGame.setActiveScreen(new MenuScreen());
            return true;
        });

        Button.ButtonStyle buttonStyle2 = new Button.ButtonStyle();
        Texture buttonTex2 = new Texture(Gdx.files.internal("assets/image/audio.png"));
        TextureRegion buttonRegion2 = new TextureRegion(buttonTex2);
        buttonStyle2.up = new TextureRegionDrawable(buttonRegion2);

        Button muteButton = new Button(buttonStyle2);
        muteButton.setColor(Color.CYAN);

        muteButton.addListener(
                (Event e) ->
                {
                    if (!isTouchDownEvent(e)) {
                        return false;
                    }
                    if (audioVolume != 0) {
                        audioVolume = 0;
                    } else {
                        audioVolume = audioVolumeBegin;
                    }
                    backgroundMusic.setVolume(audioVolume);
                    return true;
                }
        );

        cellLabel = new Label("  ", BaseGame.labelStyle);
        cellLabel.setColor(Color.PURPLE);

        TilemapActor tma = new TilemapActor(1400, 1000, "assets/image/map.tmx", mainStage);
        players = getPlayers();
        activePlayerIndex = 0;
        createObjectsFromTileMap(tma);

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/sound/ghostsound.ogg"));

        audioVolume = 0.2f;
        audioVolumeBegin = 0.2f;
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(audioVolume);
        backgroundMusic.play();

        messageLabel = new Label("...", BaseGame.labelStyle);
        messageLabel.setColor(Color.LIME);
        messageLabel.setVisible(false);

        uiTable.pad(10);
        uiTable.add(cellLabel).top();
        uiTable.add().expandX().expandY();
        uiTable.add(messageLabel).top();
        uiTable.add(restartButton).top();
        uiTable.add(muteButton).top();
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
        cellLabel.setText(String.format("До финиша: %d", FINISH_CELL_POSITION - getMaxPosition()));
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

        ArrayList<MapObject> mapObjects = tma.getRectangleList("cell");
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
                    cube.reset();
                    currentPlayer.clearCell();
                    currentPlayer.setSkipNextMove(true);
                    currentPlayer = getActivePlayer();
                    break;
                case GREEN:
                    cube.reset();
                    currentPlayer.clearCell();
                    break;
                case WHITE:
                    cube.reset();
                    currentPlayer.clearCell();
                    currentPlayer = getActivePlayer();
                    if (currentPlayer.isSkipNextMove()) {
                        currentPlayer.setSkipNextMove(false);
                        currentPlayer = getActivePlayer();
                    }
                    break;
                default:
                    currentPlayer.setTargetPosition(cell.getTarget());
                    messageLabel.setText(currentPlayer.getRusColor() + " игрок ходит");
                    break;
            }
            currentPlayer.setDraggable(true);
        }
    }

    private void checkTurn() {
        for (Player player : getPlayers()) {
            if (player.isDraggable()) {
                if (cube.isActive()) {
                    messageLabel.setText(player.getRusColor() + " игрок бросает кубик");
                } else {
                    messageLabel.setText(player.getRusColor() + " игрок ходит на " + cube.getCurrentEdge() + " клеток");
                    player.setTargetPosition(Math.min(player.getCurrentPosition() + cube.getCurrentEdge(), FINISH_CELL_POSITION));
                    player.toFront();
                }
                messageLabel.setVisible(true);
            }
            if (player.getCurrentPosition() == FINISH_CELL_POSITION) {
                messageLabel.setText("Game over. Победил " + player.getRusColor() + " игрок!");
                cube.setActive(false);
                messageLabel.setVisible(true);
            }
        }
    }

    public Player getActivePlayer() {
        activePlayerIndex++;
        if (activePlayerIndex >= players.size()) {
            activePlayerIndex = 0;
        }
        return players.get(activePlayerIndex);
    }

    private int getMaxPosition() {
        int max = 0;
        for (Player p: getPlayers()) {
            if (p.getCurrentPosition() > max)
                max = p.getCurrentPosition();
        }
        return max;
    }
}
