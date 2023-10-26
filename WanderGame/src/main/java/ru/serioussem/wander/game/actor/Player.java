package ru.serioussem.wander.game.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.serioussem.gdx.base.actor.DragAndDropActor;

public class Player extends DragAndDropActor {
    private int targetPosition;
    private Cell cell;
    public Player(float x, float y, Stage s, String colorPlayer) {
        super(x, y, s);
        loadTexture("assets/image/"+colorPlayer+"-player.png");
        setSize(32, 54);
        setBoundaryRectangle();
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

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    private boolean hasCell() {
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

    @Override
    public void onDragStart() {

        if (hasCell()) {
            System.out.println("has Cell");
            Cell targetCell = getCell();
            targetCell.setTargetable(true);
            clearCell();
        }
    }

    @Override
    public void onDrop() {
        if (hasDropTarget()) {
            System.out.println("hasDropTarget");
            Cell cell = (Cell) getDropTarget();
            moveToActor(cell);
            setCell(cell);
//            cell.setTargetable(false);
        } else {
            //avoid blocking view of player when incorrect
            moveToStart();
        }
    }
 }
