package ru.serioussem.wander.game.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.serioussem.gdx.base.actor.DropTargetActor;
import ru.serioussem.wander.game.constants.TypeCell;

public class Cell extends DropTargetActor {
    private final int position;
    private final TypeCell type;
    private final int target;  // для желтых и красных

    public Cell(float x, float y, int position, int target, String type, Stage s) {
        super(x, y, s);
        if (position == 1 || position == 50)
            setSize(110, 64);
        else
            setSize(68, 40);
        setBoundaryPolygon(8);
        this.position = position;
        this.type = TypeCell.getByType(type);
        this.target = target;
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
