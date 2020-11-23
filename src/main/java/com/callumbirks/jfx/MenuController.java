package com.callumbirks.jfx;

import javafx.event.ActionEvent;

import java.io.IOException;

public class MenuController {

    public void play() throws IOException {
        App.setRoot("game.fxml");
    }

    public void quit() {
        System.exit(0);
    }
}
