package ru.serioussem.wander.game.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Player extends BaseActor{
    public Player(float x, float y, Stage s, String colorPlayer) {
        super(x, y, s);
        loadTexture("core/assets/image/"+colorPlayer+"-player.png");
    }
}
