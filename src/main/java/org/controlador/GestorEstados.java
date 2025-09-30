package org.controlador;

import org.modelo.input.GestorInput;
import org.vista.pantallas.PantallaJuego;
import org.vista.pantallas.PantallaMenu;
import org.vista.pantallas.PantallaFinPartida;

public class GestorEstados {

    private EstadoJuego estadoActual;

    // Referencias a las pantallas (vistas)
    private PantallaMenu pantallaMenu;
    private PantallaJuego pantallaJuego;
    private PantallaFinPartida pantallaFinPartida;

    // Input
    private GestorInput gestorInput;

    public void setGestorInput(GestorInput gestorInput) {
        this.gestorInput = gestorInput;
    }

    public void actualizar(double deltaTime) {
        // Procesar input antes de actualizar la lógica
        if (gestorInput != null) {
            gestorInput.procesarInput();
        }

        if (estadoActual != null) {
            estadoActual.actualizar(deltaTime);
        }
    }

    public void manejarInput(String input, boolean presionada) {
        if (estadoActual != null) {
            estadoActual.manejarInput(input, presionada);
        }
    }

    public void iniciarPartida(int numJugadores) {
        cambiarANivel(1, numJugadores);
    }

    public void cambiarANivel(int nivel, int numJugadores) {
        estadoActual = new EstadoPartida(nivel, numJugadores);
        if (pantallaJuego != null) pantallaJuego.mostrar();
    }

    public void cambiarAMenu() {
        estadoActual = new EstadoMenu(this);
        if (pantallaMenu != null) pantallaMenu.mostrar();
    }

    public void cambiarAFinPartida(boolean victoria) {
        estadoActual = new EstadoFinPartida(this, victoria);
        if (pantallaFinPartida != null) pantallaFinPartida.mostrar(victoria);
    }

    public EstadoJuego getEstadoActual() {
        return estadoActual;
    }
}
