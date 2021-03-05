package ru.serioussem.actors;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class DragAndDropActor extends BaseActor {
    private DragAndDropActor self;

    private float grabOffsetX;
    private float grabOffsetY;

    public DragAndDropActor(float x, float y, Stage s) {
        super(x, y, s);

        self = this;

        addListener(
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float offsetX, float offsetY, int pointer, int button) {
                        self.grabOffsetX = offsetX;
                        self.grabOffsetY = offsetY;
                        self.toFront();
                        return true;
                    }

                    @Override
                    public void touchDragged(InputEvent event, float offsetX, float offsetY, int pointer) {
                        float deltaX = offsetX - self.grabOffsetX;
                        float deltaY = offsetY - self.grabOffsetY;

                        self.moveBy(deltaX, deltaY);
                    }

                    @Override
                    public void touchUp(InputEvent event, float offsetX, float offsetY, int pointer, int button) {
                        //will add code later
                    }
                }
        );
    }

    @Override
    public void act(float dt) {
        super.act(dt);
    }
}
