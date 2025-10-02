package org.controlador;

public interface EstadoJuego {
    void actualizar(double deltaTime);
    void manejarInput(String input, boolean presionada);
}