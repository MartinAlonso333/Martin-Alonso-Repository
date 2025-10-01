package org.controlador;

import org.modelo.eventos.EventoManager;
import org.modelo.eventos.TipoEvento;

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

    public void volverAlMenu() {
        EventoManager.getInstancia().notificar(TipoEvento.MOSTRAR_MENU);
    }

    public boolean esVictoria() {
        return victoria;
    }
}
