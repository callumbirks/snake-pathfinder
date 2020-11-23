package com.callumbirks.snake;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private final SnakePart head;
    private List<SnakePart> body;

    public Snake(int x, int y, int pixelSize) {
        head = new SnakePart(x, y, Direction.RIGHT);
        body = new ArrayList<>();
        body.add(head);
    }

    public int getX() {
        return head.getX();
    }

    public int getY() {
        return head.getY();
    }

    public SnakePart getHead() {
        return head;
    }

    public int getSize() {
        return body.size();
    }

    public List<SnakePart> getBody() {
        return body;
    }

    public void eat() {
        SnakePart lastPart = body.get(body.size() - 1);
        Direction lastPartDirection = lastPart.getDirection();
        int newX = calculateNewPartX(lastPartDirection, lastPart.getX());
        int newY = calculateNewPartY(lastPartDirection, lastPart.getY());

        body.add(new SnakePart(newX, newY, lastPartDirection));
    }

    public void updatePartDirections() {
        if(!(body.size() > 1))
            return;
        Direction lastDirection = Direction.RIGHT;
        for(SnakePart part : body) {
            Direction prevDirection = part.getDirection();
            if(part != head) {
                part.setDirection(lastDirection);
            }
            lastDirection = prevDirection;
        }
    }

    public void setDirection(Direction direction) {
        head.setDirection(direction);
    }

    public Direction getDirection() {
        return head.getDirection();
    }

    public void move() {
        for(SnakePart part : body) {
            part.move();
        }
    }

    private int calculateNewPartX(Direction direction, int x) {
        return switch(direction) {
            case UP, DOWN -> x;
            case RIGHT -> x -= 1;
            case LEFT -> x += 1;
        };
    }

    private int calculateNewPartY(Direction direction, int y) {
        return switch(direction) {
            case LEFT, RIGHT -> y;
            case UP -> y += 1;
            case DOWN -> y -= 1;
        };
    }
}
