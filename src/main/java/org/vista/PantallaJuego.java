package org.vista;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import org.modelo.estados.EstadoJuego;
import org.modelo.estados.EstadoPartida;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.modelo.estados.GestorEstados;


public class PantallaJuego extends Pantalla {

    private final GestorEstados gestorEstados;
    private final StackPane root;
    private final Canvas canvas;
    private final GraphicsContext gc;
    private AnimationTimer timer;
    private long ultimoTiempoNano = 0;

    public PantallaJuego(Stage stage, GestorEstados gestorEstados, int ancho, int alto) {
        super(stage);
        this.gestorEstados = gestorEstados;

        root = new StackPane();
        canvas = new Canvas(ancho, alto);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

    }

    @Override
    public void mostrar() {
        // Cambiar root de la escena actual
        stage.getScene().setRoot(root);
        stage.setTitle("Juego - Nivel " + obtenerNivelActual());

        // Pedir foco para recibir input
        stage.getScene().setRoot(root);
        stage.getScene().getRoot().requestFocus();

        // Iniciar loop de actualización y renderizado
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (ultimoTiempoNano == 0) {
                    ultimoTiempoNano = now;
                    return;
                }
                double deltaTime = (now - ultimoTiempoNano) / 1_000_000_000.0;
                ultimoTiempoNano = now;

                actualizarYRenderizar(deltaTime);
            }
        };
        timer.start();
    }

    private void actualizarYRenderizar(double deltaTime) {
        EstadoJuego estado = gestorEstados.getEstadoActual();

        if (estado == null) {
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            return;
        }

        // Actualizar estado
        estado.actualizar(deltaTime);

        // Limpiar pantalla
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Dibujar juego si es EstadoPartida
        if (estado instanceof EstadoPartida ep) {
            ep.getJuegoVista().dibujar(gc);
        }
    }


    private int obtenerNivelActual() {
        EstadoJuego estado = gestorEstados.getEstadoActual();
        if (estado instanceof EstadoPartida ep) {
            return ep.nivelActual;
        }
        return 0;
    }

    public void detener() {
        if (timer != null) {
            timer.stop();
        }
    }
}