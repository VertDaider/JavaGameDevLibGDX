package ru.serioussem.wander.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.serioussem.gdx.base.actor.DragAndDropActor;
import ru.serioussem.wander.game.constants.TypeCell;

public class Player extends DragAndDropActor {
    private int targetPosition;
    private int currentPosition;
    private Cell cell;
    private final String color;
    private boolean isSkipNextMove;
    private final TypeCell typeCell;
    private final Sound pop;
    public Player(float x, float y, Stage s, String colorPlayer) {
        super(x, y, s);
        loadTexture("assets/image/"+colorPlayer+"-player.png");
        typeCell = TypeCell.getByType(colorPlayer);
        setSize(32, 54);
        setBoundaryRectangle();
        setDraggable(false);
        setCurrentPosition(0);
        this.color = colorPlayer;
        pop = Gdx.audio.newSound(Gdx.files.internal("assets/sound/pop.wav"));
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

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public boolean hasCell() {
        return cell != null;
    }

    public String getRusColor() {
        return typeCell.getRusName();
    }

    public void clearCell() {
        cell = null;
    }

    public boolean isSkipNextMove() {
        return isSkipNextMove;
    }

    public void setSkipNextMove(boolean skipNextMove) {
        isSkipNextMove = skipNextMove;
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        boundToWorld();
    }

    @Override
    public void onDrop() {
        if (hasDropTarget()) {
            Cell cell = (Cell) getDropTarget();
            if (this.targetPosition == cell.getPosition()) {
                moveToActor(cell);
                setDraggable(false);
                setCell(cell);
                setCurrentPosition(this.targetPosition);
                pop.play(0.2f);
            } else {
                moveToStart();
            }
        } else {
            //avoid blocking view of player when incorrect
            moveToStart();
        }
    }
 }
