package ru.serioussem.mazerunman;

import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.serioussem.mazerunman.actor.Room;

public class Maze {
    private Room[][] roomGrid;

    private final int roomCountX = 24;
    private final int roomCountY = 20;
    private final int roomWidth = 64;
    private final int roomHeight = 64;

    public Maze(Stage s) {
        roomGrid = new Room[roomCountX][roomCountY];

        for (int gridY = 0; gridY < roomCountY; gridY++) {
            for (int gridX = 0; gridX < roomCountX; gridX++) {
                float pixelX = gridX * roomWidth;
                float pixelY = gridY * roomHeight;
                Room room = new Room(pixelX, pixelY, s);
                roomGrid[gridX][gridY] = room;
            }
        }

        // neighbor relations
        for (int gridY = 0; gridY < roomCountY; gridY++) {
            for (int gridX = 0; gridX < roomCountX; gridX++) {
                Room room = roomGrid[gridX][gridY];
                if (gridY > 0)
                    room.setNeighbor(Room.SOUTH, roomGrid[gridX][gridY - 1]);
                if (gridY < roomCountY - 1)
                    room.setNeighbor(Room.NORTH, roomGrid[gridX][gridY + 1]);
                if (gridX > 0)
                    room.setNeighbor(Room.WEST, roomGrid[gridX - 1][gridY]);
                if (gridX < roomCountX - 1)
                    room.setNeighbor(Room.EAST, roomGrid[gridX + 1][gridY]);
            }
        }
    }

    public Room getRoom(int gridX, int gridY) {
        return roomGrid[gridX][gridY];
    }

    public int getRoomWidth() {
        return roomCountX * roomWidth;
    }

    public int getRoomHeight() {
        return (roomCountY * roomHeight) + 60;
    }
}
