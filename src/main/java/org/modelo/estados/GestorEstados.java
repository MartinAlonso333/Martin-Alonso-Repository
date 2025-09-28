package org.modelo.estados;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.modelo.input.GestorInput;
import org.vista.PantallaJuego;

public class GestorEstados {

    private EstadoJuego estadoActual;
    private Scene scene;
    private Stage stage;
    private int numJugadores;

    private PantallaJuego pantallaJuego;

    public GestorEstados(Scene scene, Stage stage) {
        this.scene = scene;
        this.stage = stage;
        new GestorInput(scene, this);
    }

    public void actualizar(double deltaTime) {
        if (estadoActual != null) {
            estadoActual.actualizar(deltaTime);
        }
    }

    public void manejarInput(String input) {
        if (estadoActual != null) {
            estadoActual.manejarInput(input);
        }
    }

    public void iniciarPartida(int numJugadores) {
        this.numJugadores = numJugadores;
        cambiarANivel(1, numJugadores);
        mostrarPantallaJuego();
    }

    public void cambiarANivel(int nivel, int numJugadores) {
        estadoActual = new EstadoPartida(this, nivel, numJugadores);
    }

    public void cambiarAFinPartida() {
        estadoActual = new EstadoFinPartida(this);
    }

    public void cambiarAMenu() {
        estadoActual = new EstadoMenu(this, stage);
    }

    private void mostrarPantallaJuego() {
        if (pantallaJuego == null) {
            pantallaJuego = new PantallaJuego(stage, this, (int)scene.getWidth(), (int)scene.getHeight());
        }
        pantallaJuego.mostrar();
    }

    public EstadoJuego getEstadoActual() {
        return estadoActual;
    }

    public Stage getStage() {
        return stage;
    }

    public void detenerMovimientoJugador(int jugadorId) {
        EstadoJuego estadoActual = getEstadoActual();
        if (estadoActual instanceof EstadoPartida estadoPartida) {
            estadoPartida.detenerMovimientoJugador(jugadorId);
        }
    }
}