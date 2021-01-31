package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Map {
    public static final int GRASS_SIZE = 40;
    public static final int SIZE_GRASS_X = Gdx.graphics.getWidth() / GRASS_SIZE;
    public static final int SIZE_GRASS_Y = Gdx.graphics.getHeight() / GRASS_SIZE;

    public static final int BLOCK_SIZE = 20;
    public static final int SIZE_BLOCK_X = Gdx.graphics.getWidth() / BLOCK_SIZE;
    public static final int SIZE_BLOCK_Y = Gdx.graphics.getHeight() / BLOCK_SIZE;

    public enum WallType {
        HARD(0, 5, true, false, false),
        SOFT(1, 3, true, false, false),
        INDESTRUCTIBLE(2, 1, false, false, false),
        WATER(3, 1, false, false, true),
        NONE(0, 0, false, true, true);

        int index;
        int maxHp;
        boolean unitPossible;       //проходимость для юнитов
        boolean projectilePossible; //проходимость для пуль
        boolean destructible;

        WallType(int index, int maxHp, boolean destructible, boolean unitPossible, boolean projectilePossible) {
            this.index = index;
            this.maxHp = maxHp;
            this.destructible = destructible;
            this.unitPossible = unitPossible;
            this.projectilePossible = projectilePossible;
        }

        public static WallType getRandom() {
            switch ((int) (Math.random() * 5)) {
                case 1:
                    return HARD;
                case 2:
                    return SOFT;
                case 3:
                    return INDESTRUCTIBLE;
                case 4:
                default:
                    return WATER;
            }
        }
    }

    private class Cell {
        WallType type;
        int hp;

        public Cell(WallType type) {
            this.type = type;
            this.hp = type.maxHp;
        }

        public void damage() {
            if (type.destructible) {
                hp--;
                if (hp <= 0) {
                    type = WallType.NONE;
                }
            }
        }

        public void changeType(WallType type) {
            this.type = type;
            this.hp = type.maxHp;
        }
    }

    private final TextureRegion grassTexture;
    private final TextureRegion[][] wallsTexture;
    private final Cell[][] cells;

    public Map(TextureAtlas atlas) {
        this.wallsTexture = new TextureRegion(atlas.findRegion("obstacles")).split(BLOCK_SIZE, BLOCK_SIZE);
        this.grassTexture = atlas.findRegion("grass40");
        this.cells = new Cell[SIZE_BLOCK_X][SIZE_BLOCK_Y];
        for (int i = 0; i < SIZE_BLOCK_X; i++) {  //заполняем карту квадратами 4х4
            for (int j = 0; j < SIZE_BLOCK_Y; j++) {
                cells[i][j] = new Cell(WallType.NONE);
                int cx = i / 4;
                int cy = j / 4;
                if (cx % 2 == 0 && cy % 2 == 0) {
                    cells[i][j].changeType(WallType.getRandom());
                }
            }
        }
    }

    public void checkWallAndBulletCollision(Bullet bullet) {
        int cx = (int) bullet.getPosition().x / BLOCK_SIZE;
        int cy = (int) bullet.getPosition().y / BLOCK_SIZE;

        if (cx >= 0 && cy >= 0 && cx < SIZE_BLOCK_X && cy < SIZE_BLOCK_Y) {
            if (!cells[cx][cy].type.projectilePossible) {
                cells[cx][cy].damage();
                bullet.deactivate();
            }
        }
    }

    public boolean isAreaClear(float x, float y, float halfSize) {
//        halfSize--;  // для прохождения юнита между двумя блоками
        int leftX = (int) (x - halfSize) / BLOCK_SIZE;
        int rightX = (int) (x + halfSize) / BLOCK_SIZE;

        int bottomY = (int) (y - halfSize) / BLOCK_SIZE;
        int topY = (int) (y + halfSize) / BLOCK_SIZE;

        if (leftX < 0) {
            leftX = 0;
        }
        if (rightX >= SIZE_BLOCK_X) {
            rightX = SIZE_BLOCK_X - 1;
        }
        if (bottomY < 0) {
            bottomY = 0;
        }
        if (topY >= SIZE_BLOCK_Y) {
            topY = SIZE_BLOCK_Y - 1;
        }

        for (int i = leftX; i <= rightX; i++) {
            for (int j = bottomY; j <= topY; j++) {
                if (!cells[i][j].type.unitPossible) {
                    return false;
                }
            }
        }
        return true;
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < SIZE_GRASS_X; i++) {
            for (int j = 0; j < SIZE_GRASS_Y; j++) {
                batch.draw(grassTexture, i * GRASS_SIZE, j * GRASS_SIZE);
            }
        }
        for (int i = 0; i < SIZE_BLOCK_X; i++) {
            for (int j = 0; j < SIZE_BLOCK_Y; j++) {
                if (cells[i][j].type != WallType.NONE) {
                    batch.draw(wallsTexture[cells[i][j].type.index][cells[i][j].hp - 1], i * BLOCK_SIZE, j * BLOCK_SIZE);
                }
            }
        }
    }
}
