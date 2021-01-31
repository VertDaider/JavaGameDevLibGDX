package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.units.Tank;
import com.mygdx.game.utils.TankOwner;

public class BulletEmitter {
    public static final int MAX_BULLETS_COUNT = 100;

    private final TextureRegion bulletTexture;
    private final Bullet[] bullets;
    private int countPlayerShots;
    private final Sound soundShot = Gdx.audio.newSound(Gdx.files.internal("shot.wav"));

    public Bullet[] getBullets() {
        return bullets;
    }

    public int getPlayerShots () {
        return countPlayerShots;
    }

    public BulletEmitter(TextureAtlas atlas) {
        this.bulletTexture = atlas.findRegion("projectile");
        this.bullets = new Bullet[MAX_BULLETS_COUNT];
        for (int i = 0; i < bullets.length; i++) {
            this.bullets[i] = new Bullet();
        }
    }

    public void activate(Tank owner, float x, float y, float vx, float vy, int damage, float maxTime) {
        for (int i = 0; i < bullets.length; i++) {
            if (!bullets[i].isActive()) {
                bullets[i].activate(owner, x, y, vx, vy, damage, maxTime);
                if (bullets[i].getOwner().getOwnerType() == TankOwner.PLAYER) {
                    countPlayerShots++;
                    soundShot.play(0.6f, 0.6f, 0);
                }
                break;
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < bullets.length; i++) {
            if (bullets[i].isActive()) {
                batch.draw(bulletTexture, bullets[i].getPosition().x - 8, bullets[i].getPosition().y - 8);
            }
        }
    }

    public void update(float dt) {
        for (int i = 0; i < bullets.length; i++) {
            if (bullets[i].isActive()) {
                bullets[i].update(dt);
            }
        }
    }
}