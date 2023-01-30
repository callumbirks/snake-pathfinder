package com.callumbirks.jfx;

import com.callumbirks.snake.Game;
import com.callumbirks.snake.SnakePart;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.paint.Color;
import javafx.util.Duration;

public class GameController implements Initializable {
    @FXML
    private Canvas canvas;
    private GraphicsContext gc;

    private static final int PIXEL_SIZE = 20;

    private Game game;
    private Timeline timeline;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int width = (int) canvas.getWidth() / PIXEL_SIZE;
        int height = (int) canvas.getHeight() / PIXEL_SIZE;

        gc = canvas.getGraphicsContext2D();

        game = new Game(width, height);

        game.updatePath();

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(20), e -> {
            if(game.over()) pauseTimeline();//toMenu();
            game.step();
            render();
        }));
        canvas.requestFocus();
        timeline.play();
    }

    private void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.LIME);
        for(SnakePart part : game.getSnake().getBody()) {
            gc.fillRect(part.x * PIXEL_SIZE, part.y * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
        }
        gc.setFill(Color.WHITE);
        gc.fillOval(game.getFood().getX() * PIXEL_SIZE, game.getFood().getY() * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
    }


    private void toMenu() {
        try {
            App.setRoot("menu.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pauseTimeline() {
        timeline.pause();
    }
}
