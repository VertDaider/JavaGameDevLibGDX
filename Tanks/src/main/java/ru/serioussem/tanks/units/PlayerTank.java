package ru.serioussem.tanks.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import ru.serioussem.tanks.Item;
import ru.serioussem.tanks.Map;
import ru.serioussem.tanks.Weapon;
import ru.serioussem.tanks.screens.GameScreen;
import ru.serioussem.tanks.utils.*;

public class PlayerTank extends Tank {
    public static final int MAX_LIVES = 5;

    int score;
    int lives;
    int countShots;
    float countHits;
    int countMedkitPickUp;
    int countShieldPickUp;
    Sound soundLiveDown;
    GameType gameType;

    KeysControl keysControl;
    StringBuilder tmpString;
    Map map;

    public PlayerTank(Map map, GameScreen game, KeysControl keysControl, TextureAtlas atlas) {
        super(game);
        this.ownerType = TankOwner.PLAYER;
        this.map = map;
        this.keysControl = keysControl;
        this.gameType = game.getGameType();
        this.weapon = new Weapon(atlas);
        this.texture = atlas.findRegion("playerTankBase");
        this.textureHP = atlas.findRegion("bar");
        this.position = setStartPositionPlayer();
        this.speed = 100.0f;
        this.width = texture.getRegionWidth();
        this.height = texture.getRegionHeight();
        this.hpMax = 10;
        this.hp = this.hpMax;
        this.circle = new Circle(position.x, position.y, (float) (width + height) / 2);
        this.lives = MAX_LIVES;
        this.tmpString = new StringBuilder();
        soundLiveDown = Gdx.audio.newSound(Gdx.files.internal("assets/liveMinus.wav"));
    }

    //старт игрока в случайном месте на карте
    public Vector2 setStartPositionPlayer() {
        float coordX = 0, coordY = 0;
        Vector2 tmpVector = new Vector2(coordX, coordY);
        int SIZE_IMAGE = 20;
        do {
            coordX = MathUtils.random(SIZE_IMAGE, Gdx.graphics.getWidth() - SIZE_IMAGE);
            coordY = MathUtils.random(SIZE_IMAGE, Gdx.graphics.getHeight() - SIZE_IMAGE);
        } while (!map.isAreaClear(coordX, coordY, SIZE_IMAGE));
        return tmpVector.set(coordX, coordY);
    }

    public void addScore(int amount) {
        score += amount;
    }

    public int getScore() {
        return score;
    }

    public int getCountShots() {
        return countShots;
    }

    public void setCountShots(int countShots) {
        this.countShots = countShots;
    }

    public float getCountHits() {
        return countHits;
    }

    public void addCountHits(int x) {
        countHits += x;
    }

    public float getGiveDamage() {
        return countHits * weapon.getDamage();
    }

    public int getCountMedkitPickUp() {
        return countMedkitPickUp;
    }

    public int getCountShieldPickUp() {
        return countShieldPickUp;
    }

    public boolean isDead() {
        return lives == 0;
    }

    public void setLives(int x) {
        lives = x;
    }

    public void checkMovement(float dt) {
        if (Gdx.input.isKeyPressed(keysControl.getLeft())) {
            move(Direction.LEFT, dt);
        } else if (Gdx.input.isKeyPressed(keysControl.getRight())) {
            move(Direction.RIGHT, dt);
        } else if (Gdx.input.isKeyPressed(keysControl.getUp())) {
            move(Direction.UP, dt);
        } else if (Gdx.input.isKeyPressed(keysControl.getDown())) {
            move(Direction.DOWN, dt);
        }
    }

    @Override
    public void consumePowerUp(Item item) {
        switch (item.getType()) {
            case MEDKIT:
                countMedkitPickUp++;
                hp += 4;
                if (hp > hpMax) {
                    hp = hpMax;
                }
                break;
            case SHIELD:
                countShieldPickUp++;
                addArmor(3);
                break;
        }
    }

    public void renderHUD(SpriteBatch batch, BitmapFont font24) {
        tmpString.setLength(0);
        tmpString.append("Score: ").append(score);
        if (gameType != GameType.TIME) {
            tmpString.append("\nLives: ").append(lives);
        }
        font24.draw(batch, tmpString, 20, 720);
    }

    @Override
    public void update(float dt) {
        checkMovement(dt);
        if (keysControl.getTargeting() == KeysControl.Targeting.MOUSE) {
            rotateTurretToPoint(gameScreen.getMousePosition().x, gameScreen.getMousePosition().y, dt);
            if (Gdx.input.isTouched()) {
                fire();
            }

        } else {    // поворот башки и огонь если управление не мышкой
            if (Gdx.input.isKeyPressed(keysControl.getRotateTurretLeft())) {
                turretAngle = Utils.makeRotation(turretAngle, turretAngle + 30, 270.0f, dt);
                turretAngle = Utils.angleToFromNegPiToPosPi(turretAngle);
            }
            if (Gdx.input.isKeyPressed(keysControl.getRotateTurretRight())) {
                turretAngle = Utils.makeRotation(turretAngle, turretAngle - 30, 270.0f, dt);
                turretAngle = Utils.angleToFromNegPiToPosPi(turretAngle);
            }
            if (Gdx.input.isKeyPressed(keysControl.getFire())) {
                fire();
            }
        }
        super.update(dt);
    }

    @Override
    public void destroy() {
        lives--;
        if (lives >= 1) {
            soundLiveDown.play();
        }
        hp = hpMax;
        position.set(setStartPositionPlayer());  //респаун
    }
}