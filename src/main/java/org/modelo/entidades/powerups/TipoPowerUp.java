package org.modelo.entidades.powerups;

import org.modelo.entidades.tanques.TanqueJugador;
import org.modelo.eventos.EventoManager;
import org.modelo.eventos.TipoEvento;

public enum TipoPowerUp {

    CASCO(5) {
        @Override
        public void aplicar(TanqueJugador jugador) {
            jugador.setInvulnerabilidad(true);
            EventoManager.getInstancia().notificar(TipoEvento.CASCO_RECOGIDO,jugador);
        }
        @Override
        public void remover(TanqueJugador jugador) {
            jugador.setInvulnerabilidad(false);
            EventoManager.getInstancia().notificar(TipoEvento.EFECTO_CASCO_TERMINADO,jugador);
        }
    },

    ESTRELLA(7) {
        @Override
        public void aplicar(TanqueJugador jugador) {
            jugador.setDisparoMejorado(true);
        }
        @Override
        public void remover(TanqueJugador jugador) {
            jugador.setDisparoMejorado(false);

        }
    },

    GRANADA(0) {
        @Override
        public void aplicar(TanqueJugador jugador) {
            EventoManager.getInstancia().notificar(TipoEvento.GRANADA_RECOGIDA);
        }
        @Override
        public void remover(TanqueJugador jugador) { /* no hace nada */ }
    };

    private final double duracion;

    TipoPowerUp(double duracion) { this.duracion = duracion; }
    public double getDuracion() { return duracion; }

    public abstract void aplicar(TanqueJugador jugador);
    public abstract void remover(TanqueJugador jugador);
}
