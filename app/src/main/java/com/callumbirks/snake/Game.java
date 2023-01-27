package com.callumbirks.snake;

import com.callumbirks.pathfinder.AStar;
import com.callumbirks.pathfinder.Node;

import java.util.List;
import java.util.Random;

public class Game {
    private final Snake snake;
    private final Food food;
    private final int WIDTH;
    private final int HEIGHT;
    private static final Random rand = new Random();
    private final AStar aStar;

    public Game(int width, int height) {
        snake = new Snake(5, 5);
        WIDTH = width;
        HEIGHT = height;
        food = new Food();
        food.setPos(generateFoodPos());
        aStar = new AStar(width, height);
    }

    public void updatePath() {
        aStar.setStart(snake.getX(), snake.getY());
        aStar.setEnd(food.getX(), food.getY());
        for(SnakePart part : snake.getBody(false)) {
            aStar.setWall(part.getX(), part.getY(), true);
        }
        aStar.run();
    }

    public void step() {
        updatePath();
        moveSnake();
    }

    public void moveSnake() {
        List<Node> path = aStar.getPath();
        if(path != null) {
            Direction newDirection = null;
            try {
                newDirection = calculateDirection(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
            path.remove(0);
            snake.updatePartDirections();
            snake.setDirection(newDirection);
            snake.move();
            if(checkFood())
                eat();
        }
    }

    private Direction calculateDirection(List<Node> path) throws Exception {
        if(path.size() <= 1)
            return snake.getDirection();
        if(path.get(1).getY() == snake.getY() - 1)
            return Direction.UP;
        else if(path.get(1).getX() == snake.getX() + 1)
            return Direction.RIGHT;
        else if(path.get(1).getY() == snake.getY() + 1)
            return Direction.DOWN;
        else if(path.get(1).getX() == snake.getX() - 1)
            return Direction.LEFT;
        else
            throw new Exception("Path is not adjacent to snake head");
    }

    public void eat() {
        snake.eat();
        food.setPos(generateFoodPos());
        resetWalls();
    }

    private void resetWalls() {
        for(int i = 0; i < WIDTH; i++) {
            for(int j = 0; j < HEIGHT; j++) {
                aStar.setWall(i, j, false);
            }
        }
    }

    public boolean over() {
        if(!isSnakeInBounds())
            return true;
        for(SnakePart part : snake.getBody(false)) {
            if (part.getX() == snake.getX() && part.getY() == snake.getY())
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
            if(part.getX() == foodX && part.getY() == foodY)
                return true;
        }
        return false;
    }
}
