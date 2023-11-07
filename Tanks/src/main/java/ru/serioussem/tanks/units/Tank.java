package ru.serioussem.tanks.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import ru.serioussem.tanks.screens.GameScreen;
import ru.serioussem.tanks.Item;
import ru.serioussem.tanks.Weapon;
import ru.serioussem.tanks.utils.Direction;
import ru.serioussem.tanks.utils.TankOwner;
import ru.serioussem.tanks.utils.Utils;

public abstract class Tank {
    GameScreen gameScreen;
    TankOwner ownerType;
    Weapon weapon;
    TextureRegion texture;
    TextureRegion textureHP;
    Vector2 position;
    Vector2 tmp;
    Circle circle; // для проверки столкновений

    int hp;
    int hpMax;
    int armor;
    int armorMax;

    float speed;
    float angle;

    float turretAngle;
    float fireTimer;

    int width;
    int height;
    int takeDamagePlayer;

    public Tank(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.tmp = new Vector2(0, 0);
        this.armor = 0;
        this.armorMax = 10;
    }

    public int getTakeDamagePlayer() {
        return takeDamagePlayer;
    }

    public Circle getCircle() {
        return circle;
    }

    public TankOwner getOwnerType() {
        return ownerType;
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - (float) width / 2, position.y -(float) height / 2, (float)width / 2, (float)height / 2, width, height, 1, 1, angle);
        batch.draw(weapon.getTexture(), position.x - (float)width / 2, position.y - (float)width / 2, (float)width / 2, (float)height / 2, width, height, 1, 1, turretAngle);
        // отрисовка HUD
        if (armor != 0) {
            batch.setColor(0, 0, 0, 1);
            batch.draw(textureHP, position.x - (float)width / 2 - 2, position.y + (float)height / 2 - 8 + 12, width + 4, 6);
            batch.setColor(0, 0, 1.0f, 1);
            batch.draw(textureHP, position.x - (float)width / 2, position.y + (float)height / 2 - 8 + 12, (float) armor / armorMax * width, 6);
            batch.setColor(1, 1, 1, 1);
        }
        if (hp < hpMax) {
            batch.setColor(0, 0, 0, 1);
            batch.draw(textureHP, position.x - (float)width / 2 - 2, position.y + (float)height / 2 - 8 - 2, width + 4, 12);
            batch.setColor(0, 1, 0, 1);
            batch.draw(textureHP, position.x - (float)width / 2, position.y +(float) height / 2 - 8, (float) hp / hpMax * width, 8);
            batch.setColor(1, 1, 1, 1);
        }
    }

    public void update(float dt) {
        fireTimer += dt;
        if (position.x < (float)width / 2) {
            position.x = (float)width / 2;
        }
        if (position.x > Gdx.graphics.getWidth() - (float)width / 2) {
            position.x = Gdx.graphics.getWidth() - (float)width / 2;
        }
        if (position.y < (float)height / 2) {
            position.y = (float)height / 2;
        }
        if (position.y > Gdx.graphics.getHeight() - (float)height / 2) {
            position.y = Gdx.graphics.getHeight() - (float)height / 2;
        }
        circle.setPosition(position);
    }

    public void move(Direction direction, float dt) {
        tmp.set(position);
        tmp.add(speed * direction.getVx() * dt, speed * direction.getVy() * dt);
        if (gameScreen.getMap().isAreaClear(tmp.x, tmp.y, (float)width / 2)) {
            angle = direction.getAngle();
            position.set(tmp);
        }
    }

    public void rotateTurretToPoint(float pointX, float pointY, float dt) {
        float angleTo = Utils.getAngle(position.x, position.y, pointX, pointY);
        turretAngle = Utils.makeRotation(turretAngle, angleTo, 270.0f, dt);
        turretAngle = Utils.angleToFromNegPiToPosPi(turretAngle);
    }


    public void fire() {
        if (fireTimer >= weapon.getFirePeriod()) {
            fireTimer = 0.0f;
            float angleRad = (float) Math.toRadians(turretAngle);
            gameScreen.getBulletEmitter().activate(this, position.x, position.y,
                    weapon.getProjectileSpeed() * (float) Math.cos(angleRad), weapon.getProjectileSpeed() * (float) Math.sin(angleRad),
                    weapon.getDamage(), weapon.getProjectileLifeTime());
        }
    }

    public void takeDamage(int damage) {
        if (armor > 0) {
            armor -= damage;
        } else {
            hp -= damage;
            if (hp <= 0) {
                destroy();
            }
        }
        if (ownerType == TankOwner.PLAYER) {
            takeDamagePlayer += damage;
        }
    }

    public abstract void destroy();

    public abstract void consumePowerUp(Item item);

    public void addArmor(int amount) {
        armor += amount;
        if (armor >= armorMax) {
            armor = armorMax;
        }
    }
}
