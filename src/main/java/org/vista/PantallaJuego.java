package org.vista;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import org.modelo.Juego;
import org.modelo.utilidades.Direccion;

public class PantallaJuego extends Pantalla {

    private final int jugadores;
    private Juego juego;
    private AnimationTimer loop;

    public PantallaJuego(Stage stage, int jugadores) {
        super(stage);
        this.jugadores = jugadores;
    }

    @Override
    public void mostrar() {
        juego = new Juego();
        Pane root = new Pane();
        Scene scene = new Scene(root, 800, 600);

        scene.setOnKeyPressed(e -> {
            KeyCode code = e.getCode();

            // Controles jugador 1
            switch (code) {
                case UP -> juego.moverJugador(0, Direccion.ARRIBA);
                case DOWN -> juego.moverJugador(0, Direccion.ABAJO);
                case LEFT -> juego.moverJugador(0, Direccion.IZQUIERDA);
                case RIGHT -> juego.moverJugador(0, Direccion.DERECHA);
                case ENTER -> juego.dispararJugador(0);
            }

            if (jugadores == 2) {
                // Controles jugador 2
                switch (code) {
                    case W -> juego.moverJugador(1, Direccion.ARRIBA);
                    case S -> juego.moverJugador(1, Direccion.ABAJO);
                    case A -> juego.moverJugador(1, Direccion.IZQUIERDA);
                    case D -> juego.moverJugador(1, Direccion.DERECHA);
                    case SPACE -> juego.dispararJugador(1);
                }
            }
        });

        stage.setScene(scene);
        stage.setTitle("Yet Another Battle City");
        stage.show();

        loop = new AnimationTimer() {
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
}