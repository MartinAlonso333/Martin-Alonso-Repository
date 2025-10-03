package org.controlador;
import org.modelo.input.GestorInput;

public class GestorEstados {

    private EstadoJuego estadoActual;
    private GestorInput gestorInput;

    public void setGestorInput(GestorInput gestorInput) {
        this.gestorInput = gestorInput;
    }

    public void actualizar(double deltaTime) {
        if (gestorInput != null) gestorInput.procesarInput();
        if (estadoActual != null) estadoActual.actualizar(deltaTime);
    }

    public void manejarInput(String input, boolean presionada) {
        if (estadoActual != null) estadoActual.manejarInput(input, presionada);
    }

    public void cambiarAEstado(EstadoJuego nuevo) {
        estadoActual = nuevo;
    }
}
