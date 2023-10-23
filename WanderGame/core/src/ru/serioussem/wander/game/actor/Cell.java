package ru.serioussem.wander.game.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.serioussem.wander.game.constants.TypeCell;

public class Cell extends BaseActor {
    private final int position;
    private final TypeCell type;
    public Cell(float x, float y, int position, String type,  Stage s) {
        super(x, y, s);
        setBoundaryPolygon(8);
        this.position = position;
        this.type = TypeCell.getByType(type);
    }

    public int getPosition() {
        return position;
    }

    public TypeCell getType() {
        return type;
    }

}
