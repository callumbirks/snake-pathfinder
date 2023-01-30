package com.callumbirks.snake;

import com.callumbirks.pathfinder.AStar;
import com.callumbirks.pathfinder.Node;

import java.util.Random;

public class Game {
    private final Snake snake;
    private final Food food;
    private final int WIDTH;
    private final int HEIGHT;
    private static final Random rand = new Random();
    private final AStar aStar;
    // Public target for GameController::render()
    public int[] target = new int[] { 0, 0 };

    public Game(int width, int height) {
        snake = new Snake(5, 5);
        WIDTH = width;
        HEIGHT = height;
        food = new Food();
        food.setPos(generateFoodPos());
        aStar = new AStar(width, height);
    }

    public void step() {
        updatePath();
        moveSnake();
        if(checkFood())
            eat();
    }

    public void updatePath() {
        aStar.resetWalls();
        aStar.setStart(snake.getX(), snake.getY());
        for(SnakePart part : snake.getBody(false)) {
            aStar.setWall(part.x, part.y, true);
        }
        // Try and pathfind food, otherwise pathfind random location
        // We can pathfind food again when a path is available.
        target[0] = food.getX();
        target[1] = food.getY();
        // max attempts to stop infinite loop
        int numAttempts = 0;
        while(numAttempts++ < 20 && !pathfindTarget(target[0], target[1])) {
            target = generateFoodPos();
        }
    }

    private boolean pathfindTarget(int x, int y) {
        aStar.setEnd(x, y);
        return aStar.run();
    }

    public void moveSnake() {
        Direction newDirection = snake.getDirection();
        if(aStar.getPath() != null && aStar.getPath().size() > 1) {
            Node next = aStar.getPath().get(1);
            try {
                newDirection = calcDirection(next);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        snake.move(newDirection);
    }

    private Direction calcDirection(Node next) throws Exception {
        // Next node in path is above Snake
        if(next.getY() == snake.getY() - 1)
            return Direction.UP;
        // ... to the right
        else if(next.getX() == snake.getX() + 1)
            return Direction.RIGHT;
        // ... below
        else if(next.getY() == snake.getY() + 1)
            return Direction.DOWN;
        // ... to the left
        else if(next.getX() == snake.getX() - 1)
            return Direction.LEFT;
        else
            throw new Exception("Path is not adjacent to snake head");
    }

    public void eat() {
        snake.grow();
        food.setPos(generateFoodPos());
    }

    public boolean over() {
        if(aStar.getPath() == null || aStar.getPath().size() == 0)
            return true;
        if(!isSnakeInBounds())
            return true;
        for(SnakePart part : snake.getBody(false)) {
            if (part.x == snake.getX() && part.y == snake.getY())
                return true;
        }
        return aStar.getPath() == null;
    }

    public Snake getSnake() {
        return snake;
    }

    public Food getFood() {
        return food;
    }

    public boolean checkFood() {
        return (snake.getX() == food.getX() && snake.getY() == food.getY());
    }

    public static boolean inBounds(int w, int h, int x, int y) {
        return !(x < 0 ||
                y < 0 ||
                x > w - 1 ||
                y > h - 1);
    }
    private boolean isSnakeInBounds() {
        return inBounds(WIDTH, HEIGHT, snake.getX(), snake.getY());
    }

    private int[] generateFoodPos() {
        int foodX;
        int foodY;
        long positionsTried = 0;
        do {
            foodX = rand.nextInt(WIDTH);
            foodY = rand.nextInt(HEIGHT);
        } while(insideSnake(foodX, foodY) && positionsTried++ < (long) WIDTH * HEIGHT);
        return new int[] { foodX, foodY };
    }

    private boolean insideSnake(int foodX, int foodY) {
        for(SnakePart part : snake.getBody()) {
            if(part.x == foodX && part.y == foodY)
                return true;
        }
        return false;
    }
}
