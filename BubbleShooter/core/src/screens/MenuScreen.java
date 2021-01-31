package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen extends AbstractScreen{
    private SpriteBatch batch;
    private TextureAtlas atlas;
    private BitmapFont font24;
    private Stage stage;

    public MenuScreen(SpriteBatch batch) {

    }

    @Override
    public void show() {
        atlas = new TextureAtlas("textures.pack");
        font24 = new BitmapFont(Gdx.files.internal("jedi24.fnt"));
        stage = new Stage();
        batch = new SpriteBatch();

        Skin skin = new Skin();
        skin.add("button", new TextureRegion(atlas.findRegion("button")));
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button");
        textButtonStyle.font = font24;

        Group group = new Group();
        final TextButton startButton = new TextButton("Start", textButtonStyle);
        final TextButton exitButton = new TextButton("Exit", textButtonStyle);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().setScreen(ScreenManager.ScreenType.GAME);
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        startButton.setPosition(0, 80);
        exitButton.setPosition(0, 0);
        group.addActor(startButton);
        group.addActor(exitButton);
        // ставим меню по центру
        group.setPosition((float) Gdx.graphics.getWidth() / 2 - startButton.getWidth() / 2,
                            (float) Gdx.graphics.getHeight() / 2 - startButton.getHeight() / 2);
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

    private void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void dispose() {
        atlas.dispose();
        font24.dispose();
        stage.dispose();
    }


}