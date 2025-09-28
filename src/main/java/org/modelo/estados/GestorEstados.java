package org.modelo.estados;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class GestorEstados {

    private EstadoJuego estadoActual;
    private Scene scene;
    private Stage stage; // 🔹 lo agregamos
    private int numJugadores;

    public GestorEstados(Scene scene, Stage stage) {
        this.scene = scene;
        this.stage = stage;
    }
    // Se llama al loop principal
    public void actualizar(double deltaTime) {
        if (estadoActual != null) {
            estadoActual.actualizar(deltaTime);
        }
    }

    // Se llama desde GestorInput
    public void manejarInput(String input) {
        if (estadoActual != null) {
            estadoActual.manejarInput(input);
        }
    }

    // --- Funciones para cambiar de estado ---
    public void iniciarPartida(int numJugadores) {
        this.numJugadores = numJugadores;
        cambiarANivel(1, numJugadores);
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
}
