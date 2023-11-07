package ru.serioussem.missinghomework.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.serioussem.gdx.base.actor.BaseActor;

public class Background extends BaseActor {
    public Animation hallway;
    public Animation classroom;
    public Animation scienceLab;
    public Animation library;

    public Background(float x, float y, Stage s) {
        super(x, y, s);
        hallway = loadTexture("assets/bg-hallway.jpg");
        classroom = loadTexture("assets/bg-classroom.jpg");
        scienceLab = loadTexture("assets/bg-science-lab.jpg");
        library = loadTexture("assets/bg-library.jpg");
        setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
}
