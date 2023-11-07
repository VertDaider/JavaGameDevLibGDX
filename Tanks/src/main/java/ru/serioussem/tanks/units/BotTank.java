package ru.serioussem.tanks.units;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import ru.serioussem.tanks.screens.GameScreen;
import ru.serioussem.tanks.Item;
import ru.serioussem.tanks.Weapon;
import ru.serioussem.tanks.utils.Direction;
import ru.serioussem.tanks.utils.TankOwner;

import static ru.serioussem.tanks.utils.Direction.*;

public class BotTank extends Tank {
    Direction preferredDirection;
    float aiTimer;
    float aiTimerTo;
    float pursuitRadius;
    boolean active;
    Vector3 lastPosition;

    public boolean isActive() {
        return active;
    }

    public BotTank(GameScreen game, TextureAtlas atlas) {
        super(game);
        this.weapon = new Weapon(atlas);
        this.weapon.setFirePeriod(0.5f);
        this.ownerType = TankOwner.AI;
        this.texture = atlas.findRegion("botTankBase");
        this.textureHP = atlas.findRegion("bar");
        this.position = new Vector2();
        this.lastPosition = new Vector3(0, 0, 0);
        this.speed = 100.0f;
        this.width = texture.getRegionWidth();
        this.height = texture.getRegionHeight();
        this.hpMax = 3;
        this.armorMax = 3;
        this.hp = this.hpMax;
        this.aiTimerTo = 3.0f;
        this.preferredDirection = UP;
        this.pursuitRadius = 300.0f;
        this.circle = new Circle(position.x, position.y, (float) (width + height) / 2);
    }

    public void activate(float x, float y) {
        hpMax = 3;
        hp = hpMax;
        preferredDirection = Direction.values()[MathUtils.random(0, values().length - 1)];
        angle = preferredDirection.getAngle();
        position.set(x, y);
        aiTimer = 0.0f;
        active = true;
    }

    @Override
    public void update(float dt) {
        aiTimer += dt;
        if (aiTimer >= aiTimerTo) {
            aiTimer = 0.0f;
            aiTimerTo = MathUtils.random(3.5f, 6.0f);
            preferredDirection = Direction.values()[MathUtils.random(0, values().length - 1)];
            angle = preferredDirection.getAngle();
        }
        move(preferredDirection, dt);

        PlayerTank preferredTarget = gameScreen.getPlayer();

        //определение дистанции до игрока
        float distance = this.position.dst(preferredTarget.getPosition());
        if (distance < pursuitRadius) {
            rotateTurretToPoint(preferredTarget.getPosition().x, preferredTarget.getPosition().y, dt);
            fire();
        }

        // чтобы не тупил у стенки
        if (Math.abs(position.x - lastPosition.x) < 0.5f && Math.abs(position.y - lastPosition.y) < 0.5f) {
            lastPosition.z += dt;
            if (lastPosition.z > 0.3f) {
                aiTimer += 10.0f;
            }
        } else {
            lastPosition.x = position.x;
            lastPosition.y = position.y;
            lastPosition.z = 0.0f;
        }
        super.update(dt);
    }

    @Override
    public void destroy() {
        gameScreen.getItemsEmitter().generateRandomItem(position.x, position.y, 1, 0.5f);
        active = false;
    }

    @Override
    public void consumePowerUp(Item item) {
        switch (item.getType()) {
            case MEDKIT:
                hp += 1;
                if (hp > hpMax) {
                    hp = hpMax;
                }
                break;
            case SHIELD:
                addArmor(1);
                break;
        }
    }
}
