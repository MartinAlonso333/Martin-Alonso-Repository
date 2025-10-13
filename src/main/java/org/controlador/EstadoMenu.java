package org.controlador;

import org.modelo.eventos.EventoManager;
import org.modelo.eventos.GestorEventos;
import org.modelo.eventos.TipoEvento;

public class EstadoMenu implements EstadoJuego {
    private GestorEventos em;

    public EstadoMenu(GestorEventos gestorEventos) {
        em = gestorEventos;
    }

    @Override
    public void actualizar(double deltaTime) {
        // No hay lógica de actualización
    }

    @Override
    public void manejarInput(String input, boolean presionada) {
        if (!presionada) return;

        switch (input) {
            case "UN_JUGADOR" -> em.notificar(TipoEvento.MOSTRAR_PARTIDA, 1);
            case "DOS_JUGADORES" -> em.notificar(TipoEvento.MOSTRAR_PARTIDA, 2);
        }
    }
}
