package ru.serioussem.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import ru.serioussem.BaseGame;

public class TargetBox extends BaseActor {
    public TargetBox(float x, float y, Stage s, String letter, Color color) {
        super(x, y, s);
        loadTexture("assets/box.png");
        setSize(64, 64);

        Label letterLabel = new Label(letter, BaseGame.labelStyle);
        letterLabel.setSize(64, 64);
        letterLabel.setAlignment(Align.center);
        letterLabel.setColor(color);
        this.addActor(letterLabel);
    }
}
