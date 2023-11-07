package ru.serioussem.jigsawpuzzle.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.serioussem.gdx.base.actor.DropTargetActor;

public class PuzzleArea extends DropTargetActor {
    private int row;
    private int col;

    public PuzzleArea(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("assets/border.jpg");
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}