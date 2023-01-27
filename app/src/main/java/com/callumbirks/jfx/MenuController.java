package com.callumbirks.jfx;

import java.io.IOException;

public class MenuController {

    public void play() throws IOException {
        App.setRoot("game.fxml");
    }

    public void quit() {
        System.exit(0);
    }
}
