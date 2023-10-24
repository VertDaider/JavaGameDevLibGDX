package ru.serioussem.wander.game.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Player extends DragAndDropActor {
    private int targetPosition;
    private Cell cell;
    public Player(float x, float y, Stage s, String colorPlayer) {
        super(x, y, s);
        loadTexture("assets/image/"+colorPlayer+"-player.png");
        setBoundaryPolygon(8);
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

    @Override
    public void act(float dt) {
        super.act(dt);
        boundToWorld();
    }

    @Override
    public void onDragStart() {
        System.out.println("drag start");
        if (targetPosition != 0) {
            Cell targetCell = getTargetCell();
            if (targetCell != null) {
                targetCell.setTargetable(true);
                System.out.println(targetCell.getType().toString());
            }
//            setTargetPosition(0);
        }
    }

    @Override
    public void onDrop() {
        if (hasDropTarget()) {
            Cell cell = (Cell) getDropTarget();
            moveToActor(cell);
            setCell(cell);
            cell.setTargetable(false);
        } else {
            //avoid blocking view of pile when incorrect
            moveToStart();
        }
    }

    private Cell getTargetCell() {
        for (BaseActor cellActor: BaseActor.getList(getStage(), Cell.class.getName())) {
            Cell cell = (Cell) cellActor;
            if (cell.getPosition() == targetPosition) {
                return cell;
            }
        }
        return null;
    }
 }
