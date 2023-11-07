package ru.serioussem.bubbleshooter.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import ru.serioussem.bubbleshooter.screens.GameScreen;

public abstract class Bubble {
    GameScreen gameScreen;
    //    BubbleOwner ownerType;
//    Weapon weapon;
    TextureRegion texture;
    TextureRegion textureHP;
    Vector2 position;
    Vector2 tmp;
    Circle circle;
    Color color1;
    Color color2;
    int width;
    int height;

    public Bubble(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public Circle getCircle() {
        return circle;
    }


    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - (float) width / 2, position.y - (float) height / 2,
                (float) width / 2, (float) height / 2, width, height, 1, 1, 0);
    }

    public void update(float delta) {
        if (position.x < (float) width / 2) {
            position.x = (float) width / 2;
        }
        if (position.x > Gdx.graphics.getWidth() - (float) width / 2) {
            position.x = Gdx.graphics.getWidth() - (float) width / 2;
        }
        if (position.y < (float) height / 2) {
            position.y = (float) height / 2;
        }
        if (position.y > Gdx.graphics.getHeight() - (float) height / 2) {
            position.y = Gdx.graphics.getHeight() - (float) height / 2;
        }
        circle.setPosition(position);
    }
}
