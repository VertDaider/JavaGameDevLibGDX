package ru.serioussem.wander.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import ru.serioussem.wander.game.WanderGame;
import ru.serioussem.wander.game.actor.BaseActor;
import ru.serioussem.wander.game.actor.Player;

public class GameScreen extends BaseScreen{
    Player player1;
    Player player2;

    @Override
    public void initialize() {
        BaseActor map = new BaseActor(0, 0, mainStage);
        map.loadTexture("core/assets/map.jpg");
        map.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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
            WanderGame.setActiveScreen(new GameScreen());
            return true;
        });

        player1 = new Player(100, 100, uiStage, "red");
        player2 = new Player(150,150, uiStage, "green");

        uiTable.pad(10);
//        uiTable.add(starfishLabel).top();
        uiTable.add().expandX().expandY();
        uiTable.add(restartButton).top();
    }

    @Override
    public void update(float dt) {

    }
}
