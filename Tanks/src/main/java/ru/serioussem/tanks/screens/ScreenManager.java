package ru.serioussem.tanks.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ru.serioussem.tanks.units.PlayerTank;
import ru.serioussem.tanks.utils.GameType;

public class ScreenManager {
    public static final int WORLD_WIDTH = Gdx.graphics.getWidth();
    public static final int WORLD_HEIGHT = Gdx.graphics.getHeight();

    public enum ScreenType {
        MENU, GAME, GAME_OVER, HELP
    }

    private static ScreenManager ourInstance = new ScreenManager();

    public static ScreenManager getInstance() {
        return ourInstance;
    }

    private ScreenManager() {
    }

    private Game game;
    private GameScreen gameScreen;
    private MenuScreen menuScreen;
    private HelpScreen helpScreen;
    private GameOverScreen gameOverScreen;
    private Viewport viewport;
    private Camera camera;

    public Viewport getViewport() {
        return viewport;
    }

    public Camera getCamera() {
        return camera;
    }

    public void init(Game game, SpriteBatch batch) {
        this.game = game;
        this.camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        this.camera.position.set((float) WORLD_WIDTH / 2, (float) WORLD_HEIGHT / 2, 0);
        this.camera.update();
        this.viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        this.gameScreen = new GameScreen(batch);
        this.menuScreen = new MenuScreen();
        this.helpScreen = new HelpScreen(batch);
        this.gameOverScreen = new GameOverScreen(batch);
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
        viewport.apply();
    }

    public void setScreen(ScreenType screenType, Object... args) {
        Gdx.input.setCursorCatched(false);
        Screen currentScreen = game.getScreen();
        switch (screenType) {
            case GAME:
                gameScreen.setGameType((GameType) args[0]);
                game.setScreen(gameScreen);
                break;
            case HELP:
                game.setScreen(helpScreen);
                break;
            case MENU:
                game.setScreen(menuScreen);
                break;
            case GAME_OVER:
                GameType gameType = gameScreen.getGameType();
                gameOverScreen.setPlayerInfo((PlayerTank) args[0], (Integer) args[1], gameType);
                game.setScreen(gameOverScreen);
                break;
        }
        if (currentScreen != null) {
            currentScreen.dispose();
        }
    }
}
