package ru.serioussem.bubbleshooter.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import ru.serioussem.bubbleshooter.screens.GameScreen;

public class Player extends Bubble {
    ShapeRenderer renderer = new ShapeRenderer();

    public Player(GameScreen gameScreen) {
        super(gameScreen);
//        this.texture = atlas.findRegion("player");
        this.width = 5;
        this.height = 5;
        this.position = new Vector2((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        this.circle = new Circle(position.x, position.y, (float) (width + height) / 2);
        this.color1 = Color.GRAY;
        this.color2 = Color.WHITE;

    }

    public void update(float delta) {
//        position.set(gameScreen.getMousePosition().x, gameScreen.getMousePosition().y);
        super.update(delta);
    }


    private void drawCircle() {
//        renderer.setProjectionMatrix(ScreenManager.getInstance().getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(color1);
        renderer.circle(circle.x, circle.y, circle.radius + 1);
        renderer.setColor(color2);
        renderer.circle(circle.x, circle.y, circle.radius / 2 + 2);
        renderer.end();

    }

    public void render() {
        drawCircle();
    }
}
