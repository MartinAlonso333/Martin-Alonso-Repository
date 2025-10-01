package org.controlador;

import org.modelo.eventos.EventoManager;
import org.modelo.eventos.TipoEvento;

public class EstadoMenu implements EstadoJuego {

    @Override
    public void actualizar(double deltaTime) {
        // Sin lógica de actualización
    }

    @Override
    public void manejarInput(String input, boolean presionada) {
        if (!presionada) return;

        switch (input) {
            case "UN_JUGADOR" -> EventoManager.getInstancia().notificar(TipoEvento.MOSTRAR_PARTIDA, 1);
            case "DOS_JUGADORES" -> EventoManager.getInstancia().notificar(TipoEvento.MOSTRAR_PARTIDA, 2);
        }
    }
}
