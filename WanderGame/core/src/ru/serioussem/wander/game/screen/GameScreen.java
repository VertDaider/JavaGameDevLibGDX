package ru.serioussem.wander.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import ru.serioussem.wander.game.WanderGame;
import ru.serioussem.wander.game.actor.*;

import java.util.ArrayList;

public class GameScreen extends BaseScreen {
    Player player1;
    Player player2;
    Cell finishCell;
    boolean win;

    @Override
    public void initialize() {
        win = false;

        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        Texture buttonTex = new Texture(Gdx.files.internal("core/assets/image/undo.png"));
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

        TilemapActor tma = new TilemapActor("core/assets/image/map.tmx", mainStage);
        createObjectsFromTileMap(tma);

        uiTable.pad(10);
//        uiTable.add(starfishLabel).top();
        uiTable.add().expandX().expandY();
        uiTable.add(restartButton).top();
    }

    @Override
    public void update(float dt) {
        checkCollision();
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
                player2 = new Player((float) mp.get("x") + 20, (float) mp.get("y") - 20, mainStage, "green");
            }
            if (type.equals("red"))
                // TODO: 24.10.2023  возможно убрать и проверять потом просто по типу
                new RedCell((float) mp.get("x"), (float) mp.get("y"), pos, move, mainStage);
            else
                new Cell((float) mp.get("x"), (float) mp.get("y"), pos, type, mainStage);
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
}
