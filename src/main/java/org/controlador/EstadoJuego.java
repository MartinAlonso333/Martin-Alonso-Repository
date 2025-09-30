package org.controlador;

public interface EstadoJuego {
    void actualizar(double deltaTime);      // lógica de cada frame
    void manejarInput(String input, boolean presionada);        // input abstracto (string o enum)
}