package juego;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import juego.utilidades.Direccion;

public class JuegoApp extends Application {

    private Juego juego;

    @Override
    public void start(Stage stage) {
        juego = new Juego();
        Pane root = new Pane();
        Scene scene = new Scene(root, 800, 600);

        // Asignar controles genéricos
        scene.setOnKeyPressed(e -> {
            KeyCode code = e.getCode();

            // Jugador 1
            switch (code) {
                case UP -> juego.moverJugador(0, Direccion.ARRIBA);
                case DOWN -> juego.moverJugador(0, Direccion.ABAJO);
                case LEFT -> juego.moverJugador(0, Direccion.IZQUIERDA);
                case RIGHT -> juego.moverJugador(0, Direccion.DERECHA);
                case ENTER -> juego.dispararJugador(0);
            }

            // Jugador 2
            switch (code) {
                case W -> juego.moverJugador(1, Direccion.ARRIBA);
                case S -> juego.moverJugador(1, Direccion.ABAJO);
                case A -> juego.moverJugador(1, Direccion.IZQUIERDA);
                case D -> juego.moverJugador(1, Direccion.DERECHA);
                case SPACE -> juego.dispararJugador(1);
            }
        });

        stage.setScene(scene);
        stage.setTitle("Yet Another Battle City");
        stage.show();

        // Loop principal
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
    }

    public static void main(String[] args) {
        launch();
    }
}
