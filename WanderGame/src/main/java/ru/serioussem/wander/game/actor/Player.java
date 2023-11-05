package ru.serioussem.wander.game.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.serioussem.gdx.base.actor.DragAndDropActor;

public class Player extends DragAndDropActor {
    private int targetPosition;
    private int currentPosition;
    boolean isActive;
    private Cell cell;
    private final String color;
    public Player(float x, float y, Stage s, String colorPlayer) {
        super(x, y, s);
        loadTexture("assets/image/"+colorPlayer+"-player.png");
        setSize(32, 54);
        setBoundaryRectangle();
        setDraggable(false);
        setCurrentPosition(0);
        this.color = colorPlayer;
    }

    public String getColorPlayer() {
        return color;
    }

    public int getTargetPosition() {
        return targetPosition;
    }

    public void setTargetPosition(int targetPosition) {
        this.targetPosition = targetPosition;
    }

    public Cell getCell() {
        return cell;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

//    public boolean isActive() {
//        return isActive;
//    }
//
//    public void setActive(boolean active) {
//        isActive = active;
//    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public boolean hasCell() {
        return cell != null;
    }

    public boolean isCorrectTarget() {
        return hasCell() && cell.getPosition() == targetPosition;
    }

    public void clearCell() {
        cell = null;
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        boundToWorld();
    }

//    @Override
//    public void onDragStart() {
//
//        if (hasCell()) {
//            System.out.println("has Cell");
//            Cell targetCell = getCell();
//            targetCell.setTargetable(true);
//            clearCell();
//        }
//    }

    @Override
    public void onDrop() {
        if (hasDropTarget()) {
            Cell cell = (Cell) getDropTarget();
            if (this.targetPosition == cell.getPosition()) {
                moveToActor(cell);
                setDraggable(false);
                setCell(cell);
                setCurrentPosition(this.targetPosition);
            } else {
                moveToStart();
            }
//            setCell(cell);
//            cell.setTargetable(false);
        } else {
            //avoid blocking view of player when incorrect
            moveToStart();
        }
    }
 }
