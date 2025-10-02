package org.controlador;

public class EstadoFinPartida implements EstadoJuego {

    private final boolean victoria;

    public EstadoFinPartida(boolean victoria) {
        this.victoria = victoria;
    }

    @Override
    public void actualizar(double deltaTime) {
        // No hay actualización necesaria
    }

    @Override
    public void manejarInput(String input, boolean presionada) {
        // Sin inputs directos
    }
}
