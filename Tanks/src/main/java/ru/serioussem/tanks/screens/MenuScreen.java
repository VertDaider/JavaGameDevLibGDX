package ru.serioussem.tanks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ru.serioussem.tanks.utils.GameType;

public class MenuScreen extends AbstractScreen {

    private TextureAtlas atlas;
    private BitmapFont font24;
    private Stage stage;

    public MenuScreen() {
    }

    @Override
    public void show() {
        atlas = new TextureAtlas("assets/game.pack");
        font24 = new BitmapFont(Gdx.files.internal("assets/font24.fnt"));
        stage = new Stage();

        Skin skin = new Skin();
        skin.add("simpleButton", new TextureRegion(atlas.findRegion("SimpleButton")));
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("simpleButton");
        textButtonStyle.font = font24;

        Group group = new Group();
        final TextButton startButton = new TextButton("Normal", textButtonStyle);
        final TextButton startTimeTypeButton = new TextButton("Time mode", textButtonStyle);
        final TextButton testButton = new TextButton("Nightmare", textButtonStyle);
        final TextButton helpButton = new TextButton("Help", textButtonStyle);
        final TextButton exitButton = new TextButton("Exit", textButtonStyle);
        final TextButton fullScreenButton = new TextButton("FullScreen OFF", textButtonStyle);

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().setScreen(ScreenManager.ScreenType.GAME, GameType.NORMAL);
            }
        });
        startTimeTypeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().setScreen(ScreenManager.ScreenType.GAME, GameType.TIME);
            }
        });
        testButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().setScreen(ScreenManager.ScreenType.GAME, GameType.NIGHTMARE);
            }
        });
        helpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().setScreen(ScreenManager.ScreenType.HELP);
            }
        });
        fullScreenButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Gdx.graphics.isFullscreen()) {
                    Gdx.graphics.setWindowedMode(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                }
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        startButton.setPosition(0, 200);
        startTimeTypeButton.setPosition(0, 160);
        testButton.setPosition(0, 120);
        helpButton.setPosition(0, 80);
        fullScreenButton.setPosition(0, 40);
        exitButton.setPosition(0, 0);

        group.addActor(startButton);
        group.addActor(startTimeTypeButton);
        group.addActor(testButton);
        group.addActor(helpButton);
        group.addActor(fullScreenButton);
        group.addActor(exitButton);
        group.setPosition(540, 300);
        stage.addActor(group);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0.0f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();

    }

    @Override
    public void dispose() {
        atlas.dispose();
        font24.dispose();
        stage.dispose();
    }

    public void update(float dt) {
        stage.act(dt);
    }
}