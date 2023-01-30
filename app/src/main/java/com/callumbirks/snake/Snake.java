package com.callumbirks.snake;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private final SnakePart head;
    private final List<SnakePart> body;

    public Snake(int x, int y) {
        head = new SnakePart(x, y, Direction.RIGHT);
        body = new ArrayList<>();
        body.add(head);
    }

    public int getX() {
        return head.x;
    }

    public int getY() {
        return head.y;
    }

    public List<SnakePart> getBody(boolean includeHead) {
        return includeHead ? body : body.subList(1, body.size());
    }

    public List<SnakePart> getBody() { return getBody(true); }

    public void grow() {
        SnakePart last = body.get(body.size() - 1);
        int newX = calculateNewPartX(last.x, last.direction);
        int newY = calculateNewPartY(last.y, last.direction);

        body.add(new SnakePart(newX, newY, last.direction));
    }

    private void updatePartDirections() {
        if(!(body.size() > 1))
            return;
        Direction lastDirection = Direction.RIGHT;
        for(SnakePart part : body) {
            Direction prevDirection = part.direction;
            if(part != head) {
                part.direction = lastDirection;
            }
            lastDirection = prevDirection;
        }
    }

    public Direction getDirection() {
        return head.direction;
    }

    public void move(Direction direction) {
        updatePartDirections();
        head.direction = direction;
        for(SnakePart part : body) {
            part.move();
        }
    }

    private int calculateNewPartX(int x, Direction direction) {
        return switch(direction) {
            case UP, DOWN -> x;
            case RIGHT -> x - 1;
            case LEFT -> x + 1;
        };
    }

    private int calculateNewPartY(int y, Direction direction) {
        return switch(direction) {
            case LEFT, RIGHT -> y;
            case UP -> y + 1;
            case DOWN -> y - 1;
        };
    }
}
