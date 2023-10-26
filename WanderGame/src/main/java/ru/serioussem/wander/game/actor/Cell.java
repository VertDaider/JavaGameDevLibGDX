package ru.serioussem.wander.game.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.serioussem.gdx.base.actor.DropTargetActor;
import ru.serioussem.wander.game.constants.TypeCell;

public class Cell extends DropTargetActor {
    private final int position;
    private final TypeCell type;
    private final int target;
    private final int move;

    public Cell(float x, float y, int position, int target, int move,  String type, Stage s) {
        super(x, y, s);
        setBoundaryPolygon(8);
        setSize(70, 27);
        this.position = position;
        this.type = TypeCell.getByType(type);
        this.target = target;
        this.move = move;
    }

    public int getPosition() {
        return position;
    }

    public TypeCell getType() {
        return type;
    }

    public int getTarget() {
        return target;
    }

}
