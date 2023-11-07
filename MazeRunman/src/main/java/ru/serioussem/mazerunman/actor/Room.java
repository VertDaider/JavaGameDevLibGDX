package ru.serioussem.mazerunman.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.serioussem.gdx.base.actor.BaseActor;


public class Room extends BaseActor {
    public static final int NORTH = 0;
    public static final int SOUTH = 1;
    public static final int EAST = 2;
    public static final int WEST = 3;
    public static int[] directionArray = {NORTH, SOUTH, EAST, WEST};
    private Wall[] wallArray;
    private Room[] neighborArray;

    public Room(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("assets/dirt.png");

        float w = getWidth();
        float h = getHeight();

        // t = wall thickness in pixels
        float t = 6;

        wallArray = new Wall[4];
        wallArray[SOUTH] = new Wall(x, y, w, t, s);
        wallArray[WEST] = new Wall(x, y, t, h, s);
        wallArray[NORTH] = new Wall(x, y + h - t, w, t, s);
        wallArray[EAST] = new Wall(x + w - t, y, t, h, s);

        neighborArray = new Room[4]; // contents of this array will be initialized by Maze class

    }

    public void setNeighbor(int direction, Room neighbor) {
        neighborArray[direction] = neighbor;
    }

    public boolean hasNeighbor(int direction) {
        return neighborArray[direction] != null;
    }

    public Room getNeighbor(int direction) {
        return neighborArray[direction];
    }

    // check if wall in this direction still exists (has not been removed from stage)
    public boolean hasWall(int direction) {
        return wallArray[direction].getStage() != null;
    }

    public void removeWalls(int direction) {
        removeWallsBetween(neighborArray[direction]);
    }

    public void removeWallsBetween(Room other) {
        if (other == neighborArray[NORTH]) {
            this.wallArray[NORTH].remove();
            other.wallArray[SOUTH].remove();
        } else if (other == neighborArray[SOUTH]) {
            this.wallArray[SOUTH].remove();
            other.wallArray[NORTH].remove();
        } else  if (other == neighborArray[EAST]) {
            this.wallArray[EAST].remove();
            other.wallArray[WEST].remove();
        } else {
            this.wallArray[WEST].remove();
            other.wallArray[EAST].remove();
        }
    }
}
