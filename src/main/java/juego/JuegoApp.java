package main.java.juego;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

public class JuegoApp extends Application {
    private Juego juego;

    @Override
    public void start(Stage stage) {
        juego = new Juego();
        AnimationTimer loop = new AnimationTimer() {
            private long ultimoFrame = 0;

            @Override
            public void handle(long ahora) {
                if (ultimoFrame == 0) {
                    ultimoFrame = ahora;
                    return;
                }
                double deltaTime = (ahora - ultimoFrame) / 1_000_000_000.0;
                ultimoFrame = ahora;
                juego.actualizar(deltaTime);
            }
        };
        loop.start();

        stage.setTitle("Yet Another Battle City");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
