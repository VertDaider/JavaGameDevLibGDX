package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.*;
import com.mygdx.game.units.BotEmitter;
import com.mygdx.game.units.BotTank;
import com.mygdx.game.units.PlayerTank;
import com.mygdx.game.units.Tank;
import com.mygdx.game.utils.GameType;
import com.mygdx.game.utils.KeysControl;
import com.mygdx.game.utils.TankOwner;

public class GameScreen extends AbstractScreen {
    public static final boolean FRIENDLY_FIRE = true;
    public static final Integer GAME_TIMER = 300;

    private SpriteBatch batch;
    private BitmapFont font24;
    private TextureAtlas atlas;
    private Map map;
    private GameType gameType;
    private PlayerTank player;
    private BulletEmitter bulletEmitter;
    private ItemsEmitter itemsEmitter;
    private BotEmitter botEmitter;
    private Vector2 mousePosition;
    private TextureRegion cursor;
    private Label countdownLabel;
    private Stage stage;

    private Sound soundGameOver;
    private Sound soundPickUp;
    private Sound soundHitInPlayer;
    //    private Music music;

    private float botTimer;
    private Integer worldTimer;
    private float timeCount;
    boolean isGameOver;
    private boolean paused;

    public GameScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    public Vector2 getMousePosition() {
        return mousePosition;
    }

    public PlayerTank getPlayer() {
        return player;
    }

    public BulletEmitter getBulletEmitter() {
        return bulletEmitter;
    }

    public Map getMap() {
        return map;
    }

    public ItemsEmitter getItemsEmitter() {
        return itemsEmitter;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public GameType getGameType() {
        return gameType;
    }

    @Override
    public void show() {
        worldTimer = GAME_TIMER;
        timeCount = 0;

        soundPickUp = Gdx.audio.newSound(Gdx.files.internal("powerup5.wav"));
        soundGameOver = Gdx.audio.newSound(Gdx.files.internal("gameOver.wav"));
        soundHitInPlayer = Gdx.audio.newSound(Gdx.files.internal("hitInPlayer.wav"));
        atlas = new TextureAtlas("game.pack");
        font24 = new BitmapFont(Gdx.files.internal("font24.fnt"));
        cursor = new TextureRegion(atlas.findRegion("cursor"));
        batch = new SpriteBatch();
        map = new Map(atlas);
        player = new PlayerTank(map, this, KeysControl.createStandardControl1(), atlas);
        if (gameType == GameType.NIGHTMARE) {
            player.setLives(1);
        }
        if (gameType == GameType.TIME) {
            player.setLives(999);
        }
        bulletEmitter = new BulletEmitter(atlas);
        itemsEmitter = new ItemsEmitter(atlas);
        botEmitter = new BotEmitter(this, atlas);
        botTimer = 100.0f;
        stage = new Stage();
        mousePosition = new Vector2();
        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(font24, Color.WHITE));

        Skin skin = new Skin();
        skin.add("simpleButton", new TextureRegion(atlas.findRegion("SimpleButton")));
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("simpleButton");
        textButtonStyle.font = font24;

        Group group = new Group();
        TextButton pauseButton = new TextButton("Pause", textButtonStyle);
        TextButton exitButton = new TextButton("Exit", textButtonStyle);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                paused = !paused;
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().setScreen(ScreenManager.ScreenType.MENU);
            }
        });
        pauseButton.setPosition(0, 40);
        exitButton.setPosition(0, -640);
        countdownLabel.setPosition(-1085, 0);  // TODO: 01.05.2020 подправить HUD
        group.addActor(pauseButton);
        group.addActor(exitButton);
        if (gameType == GameType.TIME) {
            group.addActor(countdownLabel);
        }
        group.setPosition(1100, 640);
        stage.addActor(group);
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCursorCatched(true); // hide system cursor
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

// follow for player
//        ScreenManager.getInstance().getCamera().position.set(player.getPosition().x, player.getPosition().y, 0);
//        ScreenManager.getInstance().getCamera().update();

        batch.setProjectionMatrix(ScreenManager.getInstance().getCamera().combined);
        batch.begin();
        map.render(batch);
        player.render(batch);
        botEmitter.render(batch);
        bulletEmitter.render(batch);
        itemsEmitter.render(batch);
        player.renderHUD(batch, font24);
        batch.end();
        stage.draw();

        batch.begin();
        batch.draw(cursor, mousePosition.x - (float) cursor.getRegionWidth() / 2, mousePosition.y - (float) cursor.getRegionHeight() / 2,
                (float) cursor.getRegionWidth() / 2, (float) cursor.getRegionHeight() / 2,
                cursor.getRegionWidth(), cursor.getRegionHeight(), 1, 1, 0);
        batch.end();
    }

    public void update(float dt) {
        mousePosition.set(Gdx.input.getX(), Gdx.input.getY());
        ScreenManager.getInstance().getViewport().unproject(mousePosition);

        if (!paused) {
            gameProcess(dt);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            paused = !paused;
        }
        stage.act(dt);
    }

    private void gameProcess(float dt) {
        // таймер игры и отрисовка
        if (gameType == GameType.TIME) {
            timeCount += dt;
            if (timeCount >= 1) {
                if (worldTimer <= 0) {
                    player.setLives(1);
                }
                worldTimer--;
                countdownLabel.setText(String.format("Time: %02d:%02d", worldTimer / 60, worldTimer % 60));
                if (worldTimer < 0) {
                    countdownLabel.setColor(Color.RED);
                    countdownLabel.setText(String.format("Time: %02d:%02d", Math.abs(worldTimer / 60), Math.abs(worldTimer % 60)));
                }
                timeCount = 0;
            }
        }

        // таймер создания ботов
        botTimer += dt;
        if (botTimer > 15.0f) {
            botTimer = 0.0f;

            for (int i = 0; i < 4; i++) {
                float coordX, coordY;
                int SIZE_IMAGE = 20;
                do {
                    coordX = MathUtils.random(SIZE_IMAGE, Gdx.graphics.getWidth() - SIZE_IMAGE);
                    coordY = MathUtils.random(SIZE_IMAGE, Gdx.graphics.getHeight() - SIZE_IMAGE);
                } while (!map.isAreaClear(coordX, coordY, SIZE_IMAGE));

                botEmitter.activate(coordX, coordY);
            }
        }

        player.update(dt);
        botEmitter.update(dt);
        itemsEmitter.update(dt);
        bulletEmitter.update(dt);
        checkCollisions();

        isGameOver = player.isDead();
        if (isGameOver) {
            soundGameOver.play();
            ScreenManager.getInstance().setScreen(ScreenManager.ScreenType.GAME_OVER, player, (GAME_TIMER - worldTimer));
        }
    }

    public void checkCollisions() {
        bulletCollisions();
        itemCollisions();
//        botCollisions();  // TODO: 08.05.2020 метод столкновений ботов
        player.setCountShots(bulletEmitter.getPlayerShots());
    }

    private void itemCollisions() {
        for (int i = 0; i < itemsEmitter.getItems().length; i++) {
            if (itemsEmitter.getItems()[i].isActive()) {
                Item item = itemsEmitter.getItems()[i];
                if (player.getCircle().contains(item.getPosition())) {
                    player.consumePowerUp(item);
                    soundPickUp.play(3.0f);
                    item.deactivate();
                    break;
                }
                for (int j = 0; j < botEmitter.getBots().length; j++) {
                    BotTank bot = botEmitter.getBots()[j];
                    if (bot.isActive()) {
                        if (bot.getCircle().contains(item.getPosition())) {
                            bot.consumePowerUp(item);
                            soundPickUp.play(3.0f);
                            item.deactivate();
                            break;
                        }
                    }
                }
            }
        }
    }

    private void bulletCollisions() {
        for (int i = 0; i < bulletEmitter.getBullets().length; i++) {
            Bullet bullet = bulletEmitter.getBullets()[i];
            if (bullet.isActive()) {
                //столкновение пуль и ботов
                for (int j = 0; j < botEmitter.getBots().length; j++) {
                    BotTank bot = botEmitter.getBots()[j];
                    if (bot.isActive()) {
                        if (checkBulletAndTank(bot, bullet) && bot.getCircle().contains(bullet.getPosition())) {
                            if (bullet.getOwner().getOwnerType() == TankOwner.PLAYER) {
                                player.addCountHits(1);
                            }
                            bullet.deactivate();
                            bot.takeDamage(bullet.getDamage());
                            if (!bot.isActive() && bullet.getOwner().getOwnerType() == TankOwner.PLAYER) {
                                player.addScore(1);
                            }
                            break;
                        }
                    }
                }

                //столкновение пуль и игрока
                if (checkBulletAndTank(player, bullet) && player.getCircle().contains(bullet.getPosition())) {
                    bullet.deactivate();
                    player.takeDamage(bullet.getDamage());
                    soundHitInPlayer.play(0.25f, 0.75f, 0);
                }
                // с объектами карты
                map.checkWallAndBulletCollision(bullet);
            }
        }
    }

    public boolean checkBulletAndTank(Tank tank, Bullet bullet) {
        if (!FRIENDLY_FIRE) {
            return tank.getOwnerType() != bullet.getOwner().getOwnerType();
        } else {
            return tank != bullet.getOwner();
        }
    }

    @Override
    public void dispose() {
        font24.dispose();
        atlas.dispose();
    }
}