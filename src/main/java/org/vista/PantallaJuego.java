package org.vista;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import org.modelo.Juego;
import org.modelo.entidades.Ente;
import org.modelo.utilidades.Direccion;

import java.util.List;
import java.util.Map;

public class PantallaJuego extends Pantalla {

    private final int jugadores;
    private final Juego juego;
    private final JuegoVista vista; // la fachada de lo gráfico
    private AnimationTimer loop;

    public PantallaJuego(Stage stage, int jugadores, Juego juego) {
        super(stage);
        this.jugadores = jugadores;
        this.juego = juego;
        this.vista = new JuegoVista();

        // Aquí podés inicializar las vistas de los entes del juego
        for (Ente ente : juego.getEntes()) {
            Map<Direccion, List<String>> rutas = FabricaSprites.obtenerRutas(ente);
            // esa clase helper puede devolverte las rutas segun tipo de ente
            vista.agregarEnteVista(new EnteVista(ente, rutas));
        }
    }

    @Override
    public void mostrar() {
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Pane root = new Pane(canvas);
        Scene scene = new Scene(root);

        configurarControles(scene);

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

                // Lógica
                juego.actualizar(deltaTime);

                // Limpio pantalla y dibujo
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                vista.actualizar(deltaTime);
                vista.dibujar(gc);
            }
        };
        loop.start();
    }

    private void configurarControles(Scene scene) {
        scene.setOnKeyPressed(e -> {
            KeyCode code = e.getCode();

            switch (code) {
                case UP -> juego.moverJugador(0, Direccion.ARRIBA);
                case DOWN -> juego.moverJugador(0, Direccion.ABAJO);
                case LEFT -> juego.moverJugador(0, Direccion.IZQUIERDA);
                case RIGHT -> juego.moverJugador(0, Direccion.DERECHA);
                case ENTER -> juego.dispararJugador(0);
            }

            if (jugadores == 2) {
                switch (code) {
                    case W -> juego.moverJugador(1, Direccion.ARRIBA);
                    case S -> juego.moverJugador(1, Direccion.ABAJO);
                    case A -> juego.moverJugador(1, Direccion.IZQUIERDA);
                    case D -> juego.moverJugador(1, Direccion.DERECHA);
                    case SPACE -> juego.dispararJugador(1);
                }
            }
        });
    }
}