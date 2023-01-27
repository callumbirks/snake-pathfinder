package com.callumbirks.snake;

public class SnakePart {
    private int x;
    private int y;
    private Direction direction;

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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
