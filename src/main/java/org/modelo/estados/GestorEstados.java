package org.modelo.estados;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.modelo.input.GestorInput;
import org.vista.PantallaJuego;
import org.vista.*;

public class GestorEstados {

    private EstadoJuego estadoActual;
    private final Scene scene;
    private final Stage stage;

    private PantallaJuego pantallaJuego;
    private PantallaMenu pantallaMenu;

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
        mostrarPantallaJuego();  // Mostrar pantalla y registrar listeners primero
        cambiarANivel(1, numJugadores);  // Luego crear EstadoPartida que dispara eventos
    }

    public void cambiarANivel(int nivel, int numJugadores) {
        estadoActual = new EstadoPartida(this, nivel, numJugadores);
    }

    public void cambiarAFinPartida() {
        estadoActual = new EstadoFinPartida(this);
        // Aquí podrías crear y mostrar pantalla fin partida si tienes
    }

    public void cambiarAMenu() {
        estadoActual = new EstadoMenu(this);
        mostrarPantallaMenu();
    }

    private void mostrarPantallaJuego() {
        if (pantallaJuego == null) {
            pantallaJuego = new PantallaJuego(stage, this, (int) scene.getWidth(), (int) scene.getHeight());
        }
        pantallaJuego.mostrar();
    }

    private void mostrarPantallaMenu() {
        if (pantallaMenu == null) {
            pantallaMenu = new PantallaMenu(stage, this::iniciarPartida, stage::close);
        }
        pantallaMenu.mostrar();
    }

    public EstadoJuego getEstadoActual() {
        return estadoActual;
    }

    public Stage getStage() {
        return stage;
    }

    public void detenerMovimientoJugador(int jugadorId) {
        if (estadoActual instanceof EstadoPartida estadoPartida) {
            estadoPartida.detenerMovimientoJugador(jugadorId);
        }
    }
}