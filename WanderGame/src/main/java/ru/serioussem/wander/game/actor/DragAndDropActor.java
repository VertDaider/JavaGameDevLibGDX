package ru.serioussem.wander.game.actor;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class DragAndDropActor extends BaseActor {
    private final DragAndDropActor self;
    private DropTargetActor dropTarget;
    private boolean draggable;

    private float grabOffsetX;
    private float grabOffsetY;

    private float startPositionX;
    private float startPositionY;

    public DragAndDropActor(float x, float y, Stage s) {
        super(x, y, s);

        self = this;
        draggable = true;

        addListener(
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float offsetX, float offsetY, int pointer, int button) {
                        if (!self.isDraggable()) {
                            return false;
                        }
                        self.grabOffsetX = offsetX;
                        self.grabOffsetY = offsetY;
                        self.startPositionX = self.getX();
                        self.startPositionY = self.getY();
                        self.toFront();
                        self.addAction(Actions.scaleTo(1.1f, 1.1f, 0.25f));
                        self.onDragStart();
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
                        self.setDropTarget(null);
                        //keep track of distance to closest object
                        float closestDistance = Float.MAX_VALUE;

                        for (BaseActor actor : getList(self.getStage(), DropTargetActor.class.getName())) {
                            DropTargetActor target = (DropTargetActor) actor;
                            if (target.isTargetable() && self.overlaps(target)) {
                                float currentDistance =
                                        Vector2.dst(self.getX(), self.getY(), target.getX(), target.getY());

                                //check if this target is even closer
                                if (currentDistance < closestDistance) {
                                    self.setDropTarget(target);
                                    closestDistance = currentDistance;
                                }
                            }
                        }

                        self.addAction(Actions.scaleTo(1.0f, 1.0f, 0.25f));
                        self.onDrop();
                    }
                }
        );
    }

    private void setDropTarget(DropTargetActor dt) {
        dropTarget = dt;
    }

    public boolean hasDropTarget() {
        return (dropTarget != null);
    }

    public DropTargetActor getDropTarget() {
        return dropTarget;
    }

    public boolean isDraggable() {
        return draggable;
    }

    public void setDraggable(boolean d) {
        draggable = d;
    }

    public void onDragStart() {
    }

    public void onDrop() {
    }

    /**
     * Slide this actor to the center of another actor.
     */
    public void moveToActor(BaseActor other) {
        float x = other.getX() + (other.getWidth() - this.getWidth()) / 2;
        float y = other.getY() + (other.getHeight() - this.getHeight()) / 2;
        addAction(Actions.moveTo(x, y, 0.50f, Interpolation.pow3));
    }

    /**
     * Slide this actor back to its original position before it was dragged.
     */
    public void moveToStart() {
        addAction(Actions.moveTo(startPositionX, startPositionY, 0.50f, Interpolation.pow3));
    }

    @Override
    public void act(float dt) {
        super.act(dt);
    }
}