package ru.serioussem.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Rock extends BaseActor {
    private boolean cleared;

    public Rock(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("rock.png");
        setBoundaryPolygon(8);
        cleared = false;
    }

    public boolean isCleared() {
        return cleared;
    }

    public void removeRock() {
        cleared = true;
        clearActions();
        addAction(Actions.fadeOut(0.3f));
        addAction(Actions.after(Actions.removeActor()));
    }
}
