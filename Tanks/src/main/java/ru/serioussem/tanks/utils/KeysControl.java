package ru.serioussem.tanks.utils;

import com.badlogic.gdx.Input;

public class KeysControl {
    public enum Targeting {
        MOUSE,
        KEYBOARD
    }

    private final int up;
    private final int down;
    private final int left;
    private final int right;

    private final Targeting targeting;
    private final int pause;

    private final int fire;
    private final int rotateTurretLeft;
    private final int rotateTurretRight;

    public KeysControl(int up, int down, int left, int right, Targeting targeting, int pause, int fire, int rotateTurretLeft, int rotateTurretRight) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.targeting = targeting;
        this.pause = pause;
        this.fire = fire;
        this.rotateTurretLeft = rotateTurretLeft;
        this.rotateTurretRight = rotateTurretRight;
    }

    public int getUp() {
        return up;
    }

    public int getDown() {
        return down;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public Targeting getTargeting() {
        return targeting;
    }

    public int getFire() {
        return fire;
    }

    public int getRotateTurretLeft() {
        return rotateTurretLeft;
    }

    public int getRotateTurretRight() {
        return rotateTurretRight;
    }

    public static KeysControl createStandardControl1() {
        return new KeysControl(
                Input.Keys.W,
                Input.Keys.S,
                Input.Keys.A,
                Input.Keys.D,
                Targeting.MOUSE,
                Input.Keys.SPACE,
                0,
                0,
                0

        );
    }

    public static KeysControl createStandardControl2() {
        return new KeysControl(
                Input.Keys.UP,
                Input.Keys.DOWN,
                Input.Keys.LEFT,
                Input.Keys.RIGHT,
                Targeting.KEYBOARD,
                Input.Keys.ESCAPE,
                Input.Keys.SPACE,
                Input.Keys.V,
                Input.Keys.B

        );
    }
}
