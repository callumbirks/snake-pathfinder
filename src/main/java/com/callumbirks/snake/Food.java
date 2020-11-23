package com.callumbirks.snake;

public class Food {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPos(int[] pos) {
        this.x = pos[0];
        this.y = pos[1];
    }
}
