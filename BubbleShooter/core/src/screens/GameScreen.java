package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import units.Player;

public class GameScreen extends AbstractScreen {
    private SpriteBatch batch;
    private Player player;
    private TextureAtlas atlas;

    private Vector2 mousePosition;


    public GameScreen(SpriteBatch batch) {
        this.batch = batch;
        mousePosition = new Vector2();
    }

    public Vector2 getMousePosition() {
        return mousePosition;
    }

    @Override
    public void show() {
//        atlas = new TextureAtlas("bubble.pack");
        player = new Player(this);
//        отрисовка системного курсора
//        Gdx.input.setCursorCatched(true);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        player.render();
    }



    public void update(float delta) {
        mousePosition.set(Gdx.input.getX(), Gdx.input.getY());
        ScreenManager.getInstance().getViewport().unproject(mousePosition);
        player.getCircle().setPosition(mousePosition);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
