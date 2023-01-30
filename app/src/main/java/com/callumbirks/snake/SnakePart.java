package com.callumbirks.snake;

public class SnakePart {
    public int x;
    public int y;
    public Direction direction;

    public SnakePart(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void move() {
        switch(direction) {
            case UP -> y -= 1;
            case RIGHT -> x += 1;
            case DOWN -> y += 1;
            case LEFT -> x -= 1;
        }
    }
}
