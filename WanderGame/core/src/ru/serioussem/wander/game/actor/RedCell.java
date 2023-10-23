package ru.serioussem.wander.game.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class RedCell extends Cell {
    private final int move;

    public RedCell(float x, float y, int position, int move, Stage s) {
        super(x, y, position, "red", s);
        this.move = move;
    }

    public int getMove() {
        return move;
    }

}
