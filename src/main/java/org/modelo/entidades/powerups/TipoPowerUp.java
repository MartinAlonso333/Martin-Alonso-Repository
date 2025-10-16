package org.modelo.entidades.powerups;

import org.modelo.entidades.tanques.TanqueJugador;
import org.modelo.eventos.EventoManager;
import org.modelo.eventos.GestorEventos;
import org.modelo.eventos.TipoEvento;

public enum TipoPowerUp {
    CASCO("PowerUp-Helmet", 5) {
        @Override
        public void aplicar(TanqueJugador jugador, GestorEventos em) {
            jugador.setInvulnerabilidad(true);
            em.notificar(TipoEvento.CASCO_RECOGIDO, jugador);
        }
        @Override
        public void remover(TanqueJugador jugador, GestorEventos em) {
            jugador.setInvulnerabilidad(false);
            em.notificar(TipoEvento.EFECTO_CASCO_TERMINADO, jugador);
        }
    },
    ESTRELLA("PowerUp-Star", 7) {
        @Override
        public void aplicar(TanqueJugador jugador, GestorEventos em) {
            jugador.setDisparoMejorado(true);
        }
        @Override
        public void remover(TanqueJugador jugador, GestorEventos em) {
            jugador.setDisparoMejorado(false);
        }
    },
    GRANADA("PowerUp-Grenade", 0) {
        @Override
        public void aplicar(TanqueJugador jugador, GestorEventos em) {
            em.notificar(TipoEvento.GRANADA_RECOGIDA, null);
        }
        @Override
        public void remover(TanqueJugador jugador, GestorEventos em) { /* no hace nada */ }
    };
    private final String claveSprite;
    private final double duracion;
    TipoPowerUp(String claveSprite, double duracion) {
        this.claveSprite = claveSprite;
        this.duracion = duracion;
    }
    public String getClaveSprite() {
        return claveSprite;
    }

    public double getDuracion() { return duracion; }

    public abstract void aplicar(TanqueJugador jugador, GestorEventos em);
    public abstract void remover(TanqueJugador jugador, GestorEventos em);
}
