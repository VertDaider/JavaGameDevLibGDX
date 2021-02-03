package ru.serioussem;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import ru.serioussem.screens.BaseScreen;

public abstract class BaseGame extends Game {
    private static BaseGame game;
    public static LabelStyle labelStyle;

    public BaseGame() {
        game = this;
    }

    public static void setActiveScreen(BaseScreen s) {
        game.setScreen(s);
    }

    public void create() {
        InputMultiplexer im = new InputMultiplexer();
        Gdx.input.setInputProcessor(im);

        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("spaceage.ttf"));
        FreeTypeFontParameter fontParameter = new FreeTypeFontParameter();
        fontParameter.size = 84;
        fontParameter.color = Color.WHITE;
        fontParameter.borderWidth = 2;
        fontParameter.borderColor = Color.BLACK;
        fontParameter.borderStraight = true;
        fontParameter.minFilter = Texture.TextureFilter.Linear;
        fontParameter.magFilter = Texture.TextureFilter.Linear;

        labelStyle = new LabelStyle();
        labelStyle.font = fontGenerator.generateFont(fontParameter);
    }
}